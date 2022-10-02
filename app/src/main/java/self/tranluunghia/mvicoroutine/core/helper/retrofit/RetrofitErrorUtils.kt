package com.f8fit.f8.core.helper.retrofit

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit

object RetrofitErrorUtils {
    fun <T> getErrorBodyAs(
        retrofit: Retrofit,
        type: Class<T>,
        response: Response<*>?
    ): T? {
        if (response?.errorBody() == null) {
            return null
        }
        return try {
            val converter: Converter<ResponseBody?, T> =
                retrofit.responseBodyConverter(type, arrayOfNulls(0))
            converter.convert(response.errorBody()!!)
        } catch (ex: Exception) {
            null
        }
    }
}