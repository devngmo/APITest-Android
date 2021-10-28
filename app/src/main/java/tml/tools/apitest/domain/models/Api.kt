package tml.tools.apitest.domain.models

import com.google.gson.Gson
import tml.common.Defs
import tml.libs.cku.data.HashMapUtils
import tml.libs.cku.textutils.UrlUtils
import tml.tools.apitest.modules.apieditor.ApiEditViewModel
import java.util.*
import kotlin.collections.HashMap

class Api(val id: String, var name:String, var method: String, var url: String,
          var params: HashMap<String, String>? = null,
          var headers: HashMap<String, String>? = null,
          var form: HashMap<String, String>? = null,
          var body: String? = null) {
    fun setHeaders(kvp: ArrayList<KeyValuePair>) {
        if (headers == null) headers = hashMapOf<String, String>()
        for (item in kvp)
            headers!![item.key] = item.value
    }

    val contentType: String
    get() {
        if (headers == null) return ""
        if (headers!!.containsKey("Content-Type"))
            return headers!!["Content-Type"]!!
        return ""
    }

    fun setParams(kvp: ArrayList<KeyValuePair>) {
        if (params == null) params = hashMapOf<String, String>()
        for (item in kvp)
            params!![item.key] = item.value
    }

    fun setForm(kvp: ArrayList<KeyValuePair>) {
        if (form == null)  form = hashMapOf<String, String>()
        for (item in kvp)
            form!![item.key] = item.value
    }

    fun clone(): Api {
        val copyParams = HashMapUtils.cloneNonNullableMapString(params)
        val copyHeaders = HashMapUtils.cloneNonNullableMapString(headers)
        val copyForm = HashMapUtils.cloneNonNullableMapString(form)
        return Api(UUID.randomUUID().toString(), name, method, url, copyParams, copyHeaders, copyForm, body)
    }

    companion object {
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
        const val METHOD_PUT = "PUT"
        const val METHOD_DELETE = "DELETE"
        fun create(name:String, method: String, url: String,
                   params: HashMap<String, String>? = null,
                   headers: HashMap<String, String>? = null,
                   form: HashMap<String, String>? = null,
                   body: String? = null) :Api {
            return Api(UUID.randomUUID().toString(), name, method, url, params, headers, form, body)
        }

        fun create(m: ApiEditViewModel):Api {
            var url = UrlUtils.combine(m.domain, m.function)
            var headers = hashMapOf<String, String>()
            var params = hashMapOf<String, String>()
            var form = hashMapOf<String, String>()
            var body = ""
            return Api(UUID.randomUUID().toString(), m.name, Defs.httpMethodList[m.methodIndex], url, params, headers, form, body)
        }
    }

    val headersKVP: ArrayList<KeyValuePair>
        get() {
            val ls = arrayListOf<KeyValuePair>()
            if (headers == null) return ls
            for (key in headers!!.keys)
                ls.add(KeyValuePair(key, "" + headers!![key]))
            return ls
        }
    val formKVP: ArrayList<KeyValuePair>
        get() {
            val ls = arrayListOf<KeyValuePair>()
            if (form == null) return ls
            for (key in form!!.keys)
                ls.add(KeyValuePair(key, "" + form!![key]))
            return ls
        }
    val paramsKVP: ArrayList<KeyValuePair>
        get() {
            val ls = arrayListOf<KeyValuePair>()
            if (params == null) return ls
            for (key in params!!.keys)
                ls.add(KeyValuePair(key, "" + params!![key]))
            return ls
        }
    val DomainName: String
        get() {
            if (url.isEmpty()) return ""
            return UrlUtils.getDomainName(url)
        }

    val Function: String
        get() {
            if (url.isEmpty()) return ""
            return url.substring(DomainName.length + 1)
        }
}