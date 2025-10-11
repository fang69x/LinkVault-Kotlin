package com.fang.linkvault.data.dto

import com.google.gson.Gson

data class ApiError(
    val message:String
)
object ApiErrorParser {
    fun parseError(errorBody: String?): String {
        if (errorBody.isNullOrEmpty()) return "Unknown server error"
        return try {
            Gson().fromJson(errorBody, ApiError::class.java).message
        } catch (e: Exception) {
            errorBody
        }
    }
}
