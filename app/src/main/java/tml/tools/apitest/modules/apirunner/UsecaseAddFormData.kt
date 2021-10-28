package tml.tools.apitest.modules.apirunner

import android.util.Log
import com.google.gson.Gson
import tml.tools.apitest.domain.models.KeyValuePair

class UsecaseAddFormData(val host: ApiRunnerFragment) {
    fun start() {
        createNewFormData {
            updateListViewAdapter {
                showDialogEdit()
            }
        }
    }

    private fun showDialogEdit() {
        ApiRunnerDialogs.editKVP(host, host.requiredRunVM.lastFormData) { kvp ->
            host.requiredRunVM.lastFormData = kvp
            host.requireFormDataListViewAdapter.update(host.requiredRunVM.form)
            host.BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }

    private fun updateListViewAdapter(function: () -> Unit) {
        host.requireFormDataListViewAdapter.update(host.requiredRunVM.form)
    }

    private fun createNewFormData(function: () -> Unit) {
        val kvp = KeyValuePair("key", "value")
        host.requiredRunVM.form.add(kvp)
    }
}