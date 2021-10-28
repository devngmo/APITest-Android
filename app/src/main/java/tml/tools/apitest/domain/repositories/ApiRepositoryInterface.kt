package tml.tools.apitest.domain.repositoriess

import tml.tools.apitest.domain.models.Api

interface ApiRepositoryInterface {
    fun getAllApi():ArrayList<Api>
    fun save(api: Api)
    fun getApiByID(id:String):Api?
}