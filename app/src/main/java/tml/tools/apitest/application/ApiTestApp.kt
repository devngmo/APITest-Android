package tml.tools.apitest.application

import android.app.Application
import tml.libs.cku.io.EventHub
import tml.tools.apitest.modules.apibrowser.ApiManager

class ApiTestApp : Application() {
    companion object {
        const val EVTCHANNEL_API_MANAGER = "api.manager"
        const val EVTID_APILIST_LOADED = "api.list.loaded"
    }
    override fun onCreate() {
        super.onCreate()
    }
}