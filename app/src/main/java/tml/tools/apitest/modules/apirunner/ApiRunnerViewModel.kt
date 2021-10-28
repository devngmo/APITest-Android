package tml.tools.apitest.modules.apirunner

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import tml.common.Defs
import tml.libs.cku.textutils.UltimateJsonParser
import tml.tools.apitest.BR
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.domain.models.KeyValuePair

class ApiRunnerViewModel(val srcApi: Api) : BaseObservable() {
    var lastHeader: KeyValuePair
    get() {
        return headers[headers.size-1]
    }
    set(value) {
        headers.add(value)
    }

    var lastFormData: KeyValuePair
        get() {
            return form[form.size-1]
        }
        set(value) {
            form.add(value)
        }

    var lastParam: KeyValuePair
        get() {
            return params[params.size-1]
        }
        set(value) {
            params.add(value)
        }

    @get:Bindable
    var name = srcApi.name
    set (value) {
        field = value
        notifyPropertyChanged(BR.name)
    }

    @get:Bindable
    var domain = srcApi.DomainName
        set (value) {
            field = value
            notifyPropertyChanged(BR.domain)
        }

    @get:Bindable
    var function = srcApi.Function
        set (value) {
            field = value
            notifyPropertyChanged(BR.function)
        }

    @get:Bindable
    var method = srcApi.method
        set (value) {
            field = value
            notifyPropertyChanged(BR.method)
        }

    @get:Bindable
    var bodyText = "" + srcApi.body
        set (value) {
            field = value
            notifyPropertyChanged(BR.bodyText)
        }

    @get:Bindable
    var params = srcApi.paramsKVP
        set (value) {
            field = value
            notifyPropertyChanged(BR.params)
        }

    @get:Bindable
    var headers = srcApi.headersKVP
        set (value) {
            field = value
            notifyPropertyChanged(BR.headers)
        }

    @get:Bindable
    var form = srcApi.formKVP
        set (value) {
            field = value
            notifyPropertyChanged(BR.form)
        }

    @get:Bindable
    var url = srcApi.url
        set (value) {
            field = value
            notifyPropertyChanged(BR.url)
        }

    @get:Bindable
    var bodyTypeNameList = Defs.bodyTypeNameList

    @get:Bindable
    var bodyTypeIndex = 0
        set (value) {
            field = value
            notifyPropertyChanged(BR.bodyTypeIndex)
        }

    internal fun create():Api {
        return createWithID(srcApi.id)
    }

    internal fun createWithID(id: String): Api {
        val api = Api(id, name, method, url, hashMapOf(), hashMapOf(), hashMapOf(), bodyText)
        api.setHeaders(headers)
        api.setParams(params)
        api.setForm(form)
        d("create api: ${Gson().toJson(api)}")
        return api
    }

    fun d(msg:String) {
        Log.d("-DBG-", "${this.javaClass.simpleName}: $msg")
    }

    var selectedListTabIndex: Int = 0

    val btnAddLabel:String
    get() {
        return when (selectedListTabIndex) {
            0 -> "Add Header"
            1 -> "Add Param"
            2 -> "Add Form Data"
            else -> "Add"
        }
    }

    @get:Bindable
    var runErrorMsg = ""
        set (value) {
            field = value
            notifyPropertyChanged(BR.runErrorMsg)
            notifyPropertyChanged(BR.runResultDisplayString)
        }

    @get:Bindable
    var runResultString = ""
        set (value) {
            field = value
            notifyPropertyChanged(BR.runResultString)
            notifyPropertyChanged(BR.runResultDisplayString)
        }

    @get:Bindable
    val runResultDisplayString : String
        get() {
            if (runErrorMsg.isNullOrEmpty()) {
                return getSuccessResultDisplay()
            }
            return "ERROR: $runErrorMsg"
        }

    @get:Bindable
    var resultMode = 0
        set (value) {
            field = value
            notifyPropertyChanged(BR.resultMode)
            notifyPropertyChanged(BR.runResultDisplayString)
        }
    private fun getSuccessResultDisplay():String {
        if (resultMode == 0) // RAW
            return runResultString
        else if (resultMode == 1) // PRETTIER
        {
            return UltimateJsonParser.tryPrettier(runResultString, runResultString)
        }
        else {
            return "All tests passed!"
        }
    }
}