package com.san.ipassmanager.model


import com.squareup.moshi.Json

data class ErrorBody(
    @Json(name = "status")
    var status: Int? = 0,
    @Json(name = "msg")
    var msg: String? = ""
)