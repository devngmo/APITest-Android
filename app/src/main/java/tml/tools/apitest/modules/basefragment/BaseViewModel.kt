package tml.tools.apitest.modules.basefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tml.common.PopupMessage

class ConfirmTask(val title:String, val msg:String, val onResult:(confirmed: Boolean) -> Unit) {

}

open class BaseViewModel : ViewModel() {
    val confirmTaskLD = MutableLiveData<ConfirmTask>()
    val popupMsg = MutableLiveData<PopupMessage>()
    var shouldAskUserConfirmBeforeBack: Boolean
        get() = shouldAskUserConfirmBeforeBackLD.value!!
        set(value) {
            shouldAskUserConfirmBeforeBackLD.value = value
        }
    private val shouldAskUserConfirmBeforeBackLD = MutableLiveData(false)
}