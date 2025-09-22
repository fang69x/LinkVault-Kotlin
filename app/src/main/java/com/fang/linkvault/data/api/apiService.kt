package com.fang.linkvault.data.api

import android.content.Context
import androidx.compose.ui.autofill.ContentType
import okhttp3.Interceptor
import okhttp3.Response

class ApiException(message:String, val statusCode:Int?=null): Exception(message)

object TokenStorage{
    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY="auth_token"

    fun saveToken(context: Context, token:String){
        val prefs= context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        prefs.edit().putString(TOKEN_KEY,token).apply()
    }
    fun getToken(context: Context):String?{
        val prefs=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        return prefs.getString(TOKEN_KEY,null)
    }
    fun clearToken(context: Context){
        val prefs=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}
