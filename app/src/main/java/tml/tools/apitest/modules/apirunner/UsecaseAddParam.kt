package tml.tools.apitest.modules.apirunner

import android.util.Log
import com.google.gson.Gson
import tml.tools.apitest.domain.models.KeyValuePair

class UsecaseAddParam(val host: ApiRunnerFragment) {
    fun start() {
        createNewParam {
            updateListViewAdapter {
                showDialogEdit()
            }
        }
    }

    private fun showDialogEdit() {
        ApiRunnerDialogs.editKVP(host, host.requiredRunVM.lastParam) { kvp ->
            host.requiredRunVM.lastParam = kvp
            host.requireParamsListViewAdapter.update(host.requiredRunVM.params)
            host.BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }

    private fun updateListViewAdapter(function: () -> Unit) {
        host.requireParamsListViewAdapter.update(host.requiredRunVM.params)
    }

    private fun createNewParam(function: () -> Unit) {
        val kvp = KeyValuePair("key", "value")
        host.requiredRunVM.params.add(kvp)
    }
}