package tml.tools.apitest.modules.apibrowser

import android.content.Context
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.infrastructure.ApiRepositorySP

class ApiManager {
    companion object {
        val ins = ApiManager()
    }
    lateinit var apiRepo : ApiRepositorySP
    fun load(context: Context, onFinished: (success: Boolean, items: ArrayList<Api>) -> Unit) {
        apiRepo = ApiRepositorySP(context)
        onFinished(true, apiRepo.getAllApi())
    }
}