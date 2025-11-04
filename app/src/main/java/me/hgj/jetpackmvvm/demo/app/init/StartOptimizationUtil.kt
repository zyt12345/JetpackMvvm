package me.hgj.jetpackmvvm.demo.app.init

import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import me.hgj.jetpackmvvm.core.init.InitTaskManager

/**
 * 作者　：hegaojian
 * 时间　：2025/11/4
 * 说明　：根据 https://mp.weixin.qq.com/s/38jiajcoaqn8uVnSy68RuQ 文章方案编写的启动优化，结合本框架InitTaskManager实现
 * 可以在启动的同时 加载首页，给用户视觉上带来极快的启动速度体验。
 * 注意：此方案只适合启动页设计为比较简单的项目 且 android12以上的设备
 * 使用方法：
 *
 * ① 定义启动页主题与配置
 *```
 * <style name="Splash" parent="Theme.SplashScreen">
 *     <!-- 背景色 -->
 *     <item name="windowSplashScreenBackground">@color/colorPrimary</item>
 *     <!-- 中心图标（支持静态或动画矢量图） -->
 *     <item name="windowSplashScreenAnimatedIcon">@drawable/cxk</item>
 *     <!-- 启动后切换的主主题 -->
 *     <item name="postSplashScreenTheme">@style/AppTheme</item>
 *     </style>
 * ```
 * ② 配置主题
 * ```
 * <activity
 *     android:name=".MainActivity"
 *     android:exported="true"
 *     android:theme="@style/Splash">
 * ```
 * ③ 首页中代码注入
 * ```
 * class MainActivity : BaseActivity<BaseViewModel, ActivityMainBinding>() {
 *      override fun onCreate(savedInstanceState: Bundle?) {
 *          //安装启动屏幕
 *          StartOptimizationUtil.installSplashScreen(this)
 *          super.onCreate(savedInstanceState)
 *          //监听启动完成后显示首页
 *          StartOptimizationUtil.startCompleteListener(this)
 *      }
 * }
 * ```
 *
 */
object StartOptimizationUtil {

    /**
     * 安装启动屏幕
     */
    fun installSplashScreen(activity: AppCompatActivity){
        val splashScreen: SplashScreen = activity.installSplashScreen()
        //给启动页面做渐变消失效果过渡到主页
        splashScreen.setOnExitAnimationListener { splashView ->
            splashView.view.animate()
                .alpha(0f)
                .setDuration(500)
                .withEndAction { splashView.remove() }
                .start()
        }
    }

    /**
     * 监听启动是否完成
     */
    fun startCompleteListener(activity: AppCompatActivity){
        val content: View = activity.findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (InitTaskManager.isInitialized) {
                    // 启动任务初始化完毕 → 移除监听，允许绘制首页页面
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    // 未完成初始化 → 还不能绘制首页页面
                    false
                }
            }
        })
    }



}