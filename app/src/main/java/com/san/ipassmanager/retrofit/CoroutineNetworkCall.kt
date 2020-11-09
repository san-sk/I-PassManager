package com.san.ipassmanager.retrofit


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.san.ipassmanager.model.ErrorBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

open class CoroutineNetworkCall<T> {

    var result: MutableLiveData<Resource<T>> = MutableLiveData()

    fun makeCall(call: suspend () -> T): LiveData<Resource<T>> {
        CoroutineScope(Dispatchers.IO).launch {
            result.postValue(Resource.loading(null))
            try {
                result.postValue(Resource.success(call()))

            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        result.postValue(
                            Resource.error(
                                try {
                                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

                                    val jsonAdapter: JsonAdapter<ErrorBody> =
                                        moshi.adapter(ErrorBody::class.java);

                                    jsonAdapter.fromJson(e.response()?.errorBody()?.string())
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    ErrorBody(500, "Unknown JSON error occurred")
                                }
                            )
                        )
                    }
                    else -> {
                        e.printStackTrace()
                        result.postValue(
                            Resource.error(
                                ErrorBody(
                                    500,
                                    "Something went wrong! \n Please check your internet connection."
                                )
                            )
                        )
                    }
                }
            }
        }
        return result
    }

}
