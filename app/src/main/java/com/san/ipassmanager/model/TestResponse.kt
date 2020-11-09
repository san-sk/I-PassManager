package com.san.ipassmanager.model


import com.squareup.moshi.Json

data class TestResponse(
    @Json(name = "userId")
    var userId: Int? = 0,
    @Json(name = "id")
    var id: Int? = 0,
    @Json(name = "title")
    var title: String? = "",
    @Json(name = "completed")
    var completed: Boolean? = false
)