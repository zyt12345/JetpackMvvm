package me.hgj.jetpackmvvm.demo.app.core.net.retrofit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.hgj.jetpackmvvm.core.net.AppException
import me.hgj.jetpackmvvm.demo.app.core.net.NetUrl
import me.hgj.jetpackmvvm.demo.data.model.entity.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 作者　：hegaojian
 * 时间　：2025/11/18
 * 说明　：解包脱壳
 */
class UnwrapResponseBodyConverter<T>(
    private val gson: Gson,
    private val type: Type
) : Converter<ResponseBody, T> {
    override fun convert(body: ResponseBody): T {
        val json = body.string()
        // 解析 ApiResponse
        val apiResponseType = TypeToken.getParameterized(ApiResponse::class.java, type).type
        val apiResponse: ApiResponse<T> = gson.fromJson(json, apiResponseType)

        // 如果服务端返回的code不是成功，那么直接抛出异常
        if (apiResponse.errorCode != NetUrl.SUCCESS_CODE) {
            throw AppException(apiResponse.errorCode.toString(),apiResponse.errorMsg)
        }
        val data = apiResponse.data
        //如果解析到的data 是个空的 ，那么给默认值
        if (data == null) {
            // 生成 泛型T类型的默认值
            return createDefaultValue(type) as T
        }
        return data
    }
    
    private fun createDefaultValue(type: Type): Any {
        return when (type) {
            is Class<*> -> when {
                // Int / Integer
                type == Int::class.java || type == Integer::class.java -> 0
                // Long / Long
                type == Long::class.java || type == java.lang.Long::class.java -> 0L
                // Boolean / Boolean
                type == Boolean::class.java || type == java.lang.Boolean::class.java -> false
                // Double / Double
                type == Double::class.java || type == java.lang.Double::class.java -> 0.0
                // Float / Float
                type == Float::class.java || type == java.lang.Float::class.java -> 0f
                // Short / Byte 等
                type == Short::class.java || type == java.lang.Short::class.java -> 0.toShort()
                type == Byte::class.java || type == java.lang.Byte::class.java -> 0.toByte()
                // String
                type == String::class.java -> ""
                List::class.java.isAssignableFrom(type) -> emptyList<Any>()
                Set::class.java.isAssignableFrom(type) -> emptySet<Any>()
                Map::class.java.isAssignableFrom(type) -> emptyMap<Any, Any>()
                else -> gson.fromJson("{}", type)
            }

            is ParameterizedType -> {
                val rawType = type.rawType
                if (rawType == List::class.java) return emptyList<Any>()
                if (rawType == Set::class.java) return emptySet<Any>()
                if (rawType == Map::class.java) return emptyMap<Any, Any>()
                return gson.fromJson("{}", type)
            }

            else -> ""
        }
    }

}


