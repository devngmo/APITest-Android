package tml.tools.apitest.infrastructure

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.domain.repositoriess.ApiRepositoryInterface

class ApiRepositorySP(val context: Context) : ApiRepositoryInterface {

    val sp : SharedPreferences
        get() =  context.getSharedPreferences("API", Activity.MODE_PRIVATE)

    override fun getAllApi() :ArrayList<Api> {
        val ls = ArrayList<Api>()
        for(item in sp.all) {
            item.value?.let {
                ls.add(Gson().fromJson(it as String, Api::class.java))
            }
        }
        return ls
    }
    override fun save(api: Api) {
        sp.edit().putString(api.id, Gson().toJson(api)).apply()
    }

    override fun getApiByID(id:String):Api? {
        val jsonStr = sp.getString(id, null) ?: return null
        return Gson().fromJson(jsonStr, Api::class.java)
    }
}