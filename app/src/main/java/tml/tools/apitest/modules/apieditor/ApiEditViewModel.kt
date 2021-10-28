package tml.tools.apitest.modules.apieditor

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import tml.tools.apitest.BR

class ApiEditViewModel : BaseObservable() {
    @get:Bindable
    var name = ""
    set (value) {
        field = value
        notifyPropertyChanged(BR.api)
    }

    @get:Bindable
    var domain = ""
        set (value) {
            field = value
            notifyPropertyChanged(BR.api)
        }

    @get:Bindable
    var function = ""
        set (value) {
            field = value
            notifyPropertyChanged(BR.api)
        }

    @get:Bindable
    var methodIndex : Int = 0
        set (value) {
            field = value
            notifyPropertyChanged(BR.api)
        }
}