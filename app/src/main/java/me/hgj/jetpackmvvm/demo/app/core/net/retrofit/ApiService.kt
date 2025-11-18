package me.hgj.jetpackmvvm.demo.app.core.net.retrofit

import me.hgj.jetpackmvvm.demo.data.model.entity.ArticleResponse
import retrofit2.http.GET

/**
 * 作者　：hegaojian
 * 时间　：2025/11/18
 * 说明　：
 */
interface ApiService {

    // 获取置顶文章
    @GET("article/top/json")
    suspend fun getTop(): List<ArticleResponse>
}