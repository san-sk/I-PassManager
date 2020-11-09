package com.san.ipassmanager.retrofit

import com.san.ipassmanager.model.ErrorBody


class Resource<T> private constructor(val status: Status, val data: T?, val errorBody: ErrorBody?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }
        fun <T> error(errorBody: ErrorBody?): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                errorBody
            )
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}