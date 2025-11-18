package me.hgj.jetpackmvvm.demo.app.core.net.retrofit

import me.hgj.jetpackmvvm.demo.data.model.entity.ApiResponse
import me.hgj.jetpackmvvm.ext.util.gson
import okhttp3.ResponseBody
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 作者　：hegaojian
 * 时间　：2025/11/18
 * 说明　：
 */
class UnwrapConverterFactory() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return UnwrapResponseBodyConverter<Any>(gson, type)
    }
}

