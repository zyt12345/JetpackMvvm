package me.hgj.jetpackmvvm.demo.data.model

import me.hgj.jetpackmvvm.ext.util.Cache

/**
 * 作者　：hegaojian
 * 时间　：2025/10/10
 * 说明　：本地数据缓存管理
 */
object CacheConfig {

    /** 是否同意了隐私政策 */
    var isAgree by Cache(false)

    /** 是否要获取置顶文章 这里通过 Cache 自动绑定本地缓存了 */
    var showTop by Cache(true)

    /** 主题颜色，0 跟随系统 1 白天 2 黑夜 */
    var themeModel by Cache(0)


    /** 搜索历史数据 本地缓存中拿 */
    var historyCacheData by Cache<Set<String>>(setOf())
}