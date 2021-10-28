package tml.tools.apitest.modules.apirunner

import android.util.Log
import com.google.gson.Gson
import tml.tools.apitest.domain.models.KeyValuePair

class UsecaseAddHeader(val host: ApiRunnerFragment) {
    fun start() {
        createNewHeader {
            updateListViewAdapter {
                showDialogEdit()
            }
        }
    }

    private fun showDialogEdit() {
        ApiRunnerDialogs.editKVP(host, host.requiredRunVM.lastHeader) { kvp ->
            host.requiredRunVM.lastHeader = kvp
            host.requireHeaderListViewAdapter.update(host.requiredRunVM.headers)
            host.BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }

    private fun updateListViewAdapter(function: () -> Unit) {
        host.requireHeaderListViewAdapter.update(host.requiredRunVM.headers)
    }

    private fun createNewHeader(function: () -> Unit) {
        val kvp = KeyValuePair("", "")
        if (host.requiredRunVM.headers.size == 0) {
            kvp.key = "Content-Type"
            kvp.value = "application/json"
        }
        host.requiredRunVM.headers.add(kvp)
    }
}