package tml.tools.apitest.modules.apirunner

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import tml.common.Defs
import tml.tools.apitest.R
import tml.tools.apitest.domain.models.KeyValuePair

class ApiRunnerDialogs {
    companion object {
        fun editKVP(host:ApiRunnerFragment, kvp:KeyValuePair, onSave:(kvp: KeyValuePair) -> Unit) {
            MaterialDialog(host.requireContext()).show {
                title(text = "Edit Header Value")
                customView(R.layout.kvp_input).apply {
                    findViewById<TextInputEditText>(R.id.kvp_key).setText( kvp.key )
                    findViewById<TextInputEditText>(R.id.kvp_value).setText( kvp.value )
                }
                positiveButton(R.string.save) { dlg ->
                    onSave(KeyValuePair(
                        dlg.getCustomView().findViewById<TextInputEditText>(R.id.kvp_key).text.toString(),
                        dlg.getCustomView().findViewById<TextInputEditText>(R.id.kvp_value).text.toString()
                    ))
                }
                negativeButton(R.string.cancel)
            }
        }

        fun showHttpMethodPicker(host:ApiRunnerFragment, onSelected:(index:Int) -> Unit) {
            MaterialDialog(host.requireContext()).show {
                listItems( items= Defs.httpMethodList) { _, index, _ ->
                    onSelected(index)
                }
                positiveButton(text = "OK")
                negativeButton(text = "Cancel")
            }
        }
    }
}