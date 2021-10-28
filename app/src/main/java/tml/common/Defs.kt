package tml.common

import tml.tools.apitest.domain.models.Api

class Defs {
    companion object {
        const val SPM_SHOULD_RELOAD_API_LIST = "should.reload.api.list"
        val EVTC_MAIN_ACTIVITY = "evt.channel.main.activity"
        val EVT_HIDE_FAB = "evt.hide.fab"
        val EVT_SHOW_FAB = "evt.show.fab"

        val httpMethodList = listOf(Api.METHOD_GET, Api.METHOD_POST, Api.METHOD_PUT, Api.METHOD_DELETE)
        val bodyTypeNameList = listOf("none", "Form Data", "x-www-form-urlencoded", "binary", "application/json", "text/plain", "application/javascript", "text/html", "application/xml")
    }
}