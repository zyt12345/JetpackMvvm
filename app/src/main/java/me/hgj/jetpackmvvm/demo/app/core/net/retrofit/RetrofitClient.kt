package me.hgj.jetpackmvvm.demo.app.core.net.retrofit

import me.hgj.jetpackmvvm.core.net.interception.LogInterceptor
import me.hgj.jetpackmvvm.demo.app.core.net.NetUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 作者　：hegaojian
 * 时间　：2025/11/18
 * 描述　：简单Retrofit引入示例,演示一下使用，还有解包脱壳
 */
object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(LogInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(NetUrl.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(UnwrapConverterFactory()) //对返回数据解包脱壳
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}