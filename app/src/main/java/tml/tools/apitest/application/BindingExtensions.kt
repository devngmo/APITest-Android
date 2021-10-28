package tml.tools.apitest.application

import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import tml.tools.apitest.domain.models.Api

@BindingAdapter("apiList")
fun RecyclerView.apiList(apiList: List<Api>?) {
    val nameList = ArrayList<String>()
}


//@BindingAdapter("httpMethodList")
//fun AppCompatSpinner.httpMethodList(httpMethodList: List<String>) {
//    adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, httpMethodList)
//}


@BindingAdapter("nameList")
fun AppCompatSpinner.nameList(nameList: List<String>) {
    adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, nameList)
}