package tml.tools.apitest.modules.apirunner

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import tml.cuajet.sys.SysUtils
import tml.tools.apitest.domain.models.KeyValuePair
import tml.tools.apitest.modules.apibrowser.ApiManager
import java.util.*

class ApiRunSerializer(val host: ApiRunnerFragment) {
    internal fun doSave(showToast: Boolean = true) {
        ApiManager.ins.apiRepo.save(host.requiredRunVM.create())
        host.BaseVM.shouldAskUserConfirmBeforeBack = false
        if (showToast) {
            SysUtils.playNotificationSound(host.requireContext())
            Toast.makeText(host.requireContext(), "Saved", Toast.LENGTH_LONG).show()
        }
    }

    fun saveAs() {
        val clonedApi = host.requiredRunVM.createWithID(UUID.randomUUID().toString())
        clonedApi.name += " copy"
        Log.d("-DBG-", "clone api as ${clonedApi.name}")
        ApiManager.ins.apiRepo.save(clonedApi)
        host.binding.runVM = ApiRunnerViewModel(clonedApi)
        host.BaseVM.shouldAskUserConfirmBeforeBack = false
    }
}