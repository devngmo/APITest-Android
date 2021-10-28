package tml.tools.apitest.modules.apibrowser

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tml.common.PopupMessage
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.modules.basefragment.BaseViewModel

class ApiBrowserViewModel : BaseViewModel() {
    val filteredApiListLD = MutableLiveData<ArrayList<Api>>()

    val filteredApiList: ArrayList<Api>
    get() {
        if (filteredApiListLD.value != null) return filteredApiListLD.value!!
        return arrayListOf()
    }

    fun filter(context:Context) {
        Log.d("-DBG-", "filter")
        ApiManager.ins.load(context) { success, items ->
            if (success)
                filteredApiListLD.value = items
            else
                popupMsg.value = PopupMessage.Error("Error","Can not load API List")
        }
    }
}