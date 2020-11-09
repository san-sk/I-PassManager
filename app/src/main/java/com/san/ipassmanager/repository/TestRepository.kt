package com.san.ipassmanager.repository

import com.san.ipassmanager.model.TestResponse
import com.san.ipassmanager.retrofit.Api
import com.san.ipassmanager.retrofit.CoroutineNetworkCall
import javax.inject.Inject


class TestRepository @Inject constructor(private val api: Api) {


    fun fetchTestResponse() = CoroutineNetworkCall<TestResponse>().makeCall {
        api.testCall()
    }

    fun fetchTestResponse2() = CoroutineNetworkCall<List<String>>().makeCall {
        //api.testCall2("https://gorest.co.in/public-api/users/12450000")
        api.testCall2("http://192.168.29.98/projects/dc-plus/index.php/DoctorPlus/drug_type")
    }

}