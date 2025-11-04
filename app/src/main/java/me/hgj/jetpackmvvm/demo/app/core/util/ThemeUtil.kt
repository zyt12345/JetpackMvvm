package me.hgj.jetpackmvvm.demo.app.core.util

import androidx.appcompat.app.AppCompatDelegate
import me.hgj.jetpackmvvm.demo.data.model.CacheConfig

/**
 * 作者　：hegaojian
 * 时间　：2025/11/4
 * 说明　：
 */
object ThemeUtil {
    fun changeTheme(){
        when (CacheConfig.themeModel) {
            0 -> {
                //跟随系统
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            1 -> {
                //浅色
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            2 -> {
                //深色
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}