package tml.tools.apitest.modules.apirunner

import tml.cuajet.LogD
import tml.cuajet.apis.RestApiUtils
import tml.tools.apitest.domain.models.Api

class UsecaseRunApi(val host: ApiRunnerFragment) : LogD() {
    fun start() {
        val api = host.requiredRunVM.create()
        if (api.method == Api.METHOD_POST) {
            d("POST: ${api.url}...")
            RestApiUtils.postGetString(host.requireContext(), api.url, api.contentType, api.headers, api.params, api.body,
                { result -> onRunSuccess(result) },
                { err -> onRunError(err) }
            )
        }
        if (api.method == Api.METHOD_GET) {
            d("GET: ${api.url}...")
            RestApiUtils.getString(host.requireContext(), api.url, api.contentType, api.headers, api.params, api.body,
                { result -> onRunSuccess(result) },
                { err -> onRunError(err) }
            )
        }
    }

    private fun onRunError(err: String) {
        d("error $err")
        host.requiredRunVM.runErrorMsg = err
        host.requiredRunVM.runResultString = ""
    }

    private fun onRunSuccess(result: String) {
        d("success: $result")
        host.requiredRunVM.runErrorMsg = ""
        host.requiredRunVM.runResultString = result
    }
}