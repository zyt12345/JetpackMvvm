package me.hgj.jetpackmvvm.demo.app.init

import android.app.Application
import me.hgj.jetpackmvvm.core.init.BaseInitTask
import me.hgj.jetpackmvvm.demo.app.core.net.RxHttpInit

/**
 * 作者　：hegaojian
 * 时间　：2025/9/29
 * 说明　：
 */
class NetTask : BaseInitTask() {
    override val name = "NetTask"
    override val runOnMainThread = true //主线程初始化
    override val isBlocking = true //阻塞线程

    override suspend fun init(app: Application) {
        // rxHttp 网络框架 初始化
        RxHttpInit.init()
    }
}