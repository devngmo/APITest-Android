package tml.tools.apitest.modules.apirunner

import android.util.Log
import com.google.gson.Gson
import tml.tools.apitest.domain.models.KeyValuePair

class UsecaseEditHeader(val host: ApiRunnerFragment, val index:Int) {
    fun start() {
        showDialogEdit()
    }

    private fun showDialogEdit() {
        ApiRunnerDialogs.editKVP(host, host.requiredRunVM.headers[index]) { kvp ->
            val old = host.requiredRunVM.headers[index]
            if (kvp.key.isNullOrEmpty()) {
                host.requiredRunVM.headers.remove(old)
            }
            else {
                host.requiredRunVM.headers[index] = kvp
            }
            host.requireHeaderListViewAdapter.update(host.requiredRunVM.headers)
            host.BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }
}