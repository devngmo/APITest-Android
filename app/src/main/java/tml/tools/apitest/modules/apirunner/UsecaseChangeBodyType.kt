package tml.tools.apitest.modules.apirunner

import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.google.gson.Gson
import tml.common.Defs
import tml.tools.apitest.domain.models.KeyValuePair

class UsecaseChangeBodyType(val host: ApiRunnerFragment) {
    fun start() {
        ApiRunnerDialogs.showHttpMethodPicker(host) {
            host.requiredRunVM.method = Defs.httpMethodList[it]
            host.BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }
}