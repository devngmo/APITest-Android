package tml.common

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

class Dlg {
    companion object {
        fun showConfirmPopup(context: Context, popupMessage: PopupMessage, onConfirmResult:(confirmed: Boolean) -> Unit) {
            MaterialDialog(context).show {
                title(text = popupMessage.title)
                message(text = popupMessage.msg)
                positiveButton(text = "OK") {
                    onConfirmResult(true)
                }
                negativeButton(text = "Cancel") {
                    onConfirmResult(false)
                }
            }
        }

        fun showConfirmPopup(context: Context, title: String, msg:String, onConfirmResult:(confirmed: Boolean) -> Unit) {
            MaterialDialog(context).show {
                title(text = title)
                message(text = msg)
                positiveButton(text = "OK") {
                    onConfirmResult(true)
                }
                negativeButton(text = "Cancel") {
                    onConfirmResult(false)
                }
            }
        }
    }
}