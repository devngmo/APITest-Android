package tml.tools.apitest.modules.jsoneditor

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import tml.common.Defs
import tml.tools.apitest.BR
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.domain.models.KeyValuePair

class JsonNode(var key:String, var value:Any?=null) {
    private var _parent : JsonNode? = null
    val childs = arrayListOf<JsonNode>()

    fun attach(parent: JsonNode) {
        _parent = parent
        parent.childs.add(this)
    }

    fun detach() {
        _parent?.let {
            it.childs.remove(this)
            _parent = null
        }
    }
}

class JsonEditorViewModel(val initValue: String) : BaseObservable() {
    @get:Bindable
    var jsonStr = initValue
    set (value) {
        field = value
        notifyPropertyChanged(BR.jsonStr)
    }

    @get:Bindable
    var treeRoot = JsonNode("body")
        set (value) {
            field = value
            notifyPropertyChanged(BR.treeRoot)
        }
}