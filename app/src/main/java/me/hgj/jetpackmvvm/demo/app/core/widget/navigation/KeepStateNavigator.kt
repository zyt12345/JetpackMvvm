package me.hgj.jetpackmvvm.demo.app.core.widget.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import java.util.ArrayDeque

/**
 * 修正版 KeepStateNavigator
 * 支持夜间模式切换（Activity 重建）后状态恢复
 * 修复 Fragment 透明层和返回异常问题
 */
@Navigator.Name("fragment")
class KeepStateNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Navigator<KeepStateNavigator.Destination>() {

    private val backStack = ArrayDeque<String>()
    private val animationMap = mutableMapOf<String, AnimInfo>()
    private val TAG = "KeepStateNavigator"

    override fun createDestination(): Destination = Destination(this)

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)

        // ---------- 处理动画 ----------
        var animInfo = AnimInfo()
        if (navOptions != null) {
            animInfo = AnimInfo(
                enterAnim = navOptions.enterAnim.takeIf { it != -1 } ?: 0,
                exitAnim = navOptions.exitAnim.takeIf { it != -1 } ?: 0,
                popEnterAnim = navOptions.popEnterAnim.takeIf { it != -1 } ?: 0,
                popExitAnim = navOptions.popExitAnim.takeIf { it != -1 } ?: 0
            )
            transaction.setCustomAnimations(
                animInfo.enterAnim,
                animInfo.exitAnim,
                animInfo.popEnterAnim,
                animInfo.popExitAnim
            )
        }
        animationMap[tag] = animInfo

        // ---------- 修复主题切换后透明层残留 ----------
        if (backStack.isEmpty() && fragmentManager.fragments.isNotEmpty()) {
            fragmentManager.beginTransaction().apply {
                fragmentManager.fragments.forEach { remove(it) }
            }.commitNowAllowingStateLoss()
            animationMap.clear()
        }

        // ---------- 隐藏当前 fragment ----------
        backStack.lastOrNull()?.let { currentTag ->
            fragmentManager.findFragmentByTag(currentTag)?.let { currentFragment ->
                transaction.hide(currentFragment)
                transaction.setMaxLifecycle(currentFragment, Lifecycle.State.STARTED)
            }
        }

        // ---------- 创建 / 复用 fragment ----------
        var fragment = fragmentManager.findFragmentByTag(tag)
        val shouldRecreate = fragment != null && navOptions?.popUpToId == destination.id
        if (shouldRecreate) {
            fragmentManager.beginTransaction().remove(fragment!!).commitNowAllowingStateLoss()
            fragment = null
            backStack.remove(tag)
        }

        if (fragment == null) {
            val className = destination.className.ifBlank {
                throw IllegalStateException("KeepStateNavigator: destination.className is empty for id=${destination.id}")
            }
            fragment = fragmentManager.fragmentFactory.instantiate(context.classLoader, className)
            fragment.arguments = args
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.show(fragment)
        }

        // ---------- 设置主 fragment ----------
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
        transaction.commitNowAllowingStateLoss()

        if (backStack.isEmpty() || backStack.last() != tag) {
            backStack.add(tag)
        }

        Log.d(TAG, "navigate → $backStack")
        return destination
    }

    override fun popBackStack(): Boolean {
        if (backStack.size <= 1) {
            Log.d(TAG, "popBackStack → root reached")
            return false
        }

        val currentTag = backStack.removeLast()
        val previousTag = backStack.last()

        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)

        // 从缓存中取动画配置
        animationMap[currentTag]?.let { anim ->
            if (anim.popEnterAnim != 0 || anim.popExitAnim != 0) {
                transaction.setCustomAnimations(anim.popEnterAnim, anim.popExitAnim)
            }
        }

        val currentFragment = fragmentManager.findFragmentByTag(currentTag)
        val previousFragment = fragmentManager.findFragmentByTag(previousTag)

        currentFragment?.let { transaction.remove(it) }
        previousFragment?.let {
            transaction.show(it)
            transaction.setMaxLifecycle(it, Lifecycle.State.RESUMED)
            transaction.setPrimaryNavigationFragment(it)
        }

        transaction.commitNowAllowingStateLoss()
        Log.d(TAG, "popBackStack → $backStack")
        return true
    }

    // ---------- 保存 / 恢复状态 ----------
    override fun onSaveState(): Bundle {
        return Bundle().apply {
            putStringArrayList("backStack", ArrayList(backStack))
        }
    }

    override fun onRestoreState(savedState: Bundle) {
        val restored = savedState.getStringArrayList("backStack")
        backStack.clear()
        restored?.let { backStack.addAll(it) }
        Log.d(TAG, "onRestoreState → $backStack")
    }

    @NavDestination.ClassType(Fragment::class)
    class Destination(navigator: Navigator<out Destination>) : NavDestination(navigator) {
        var className: String = ""

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            val name = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "name")
            if (!name.isNullOrEmpty()) {
                className = if (name.startsWith(".")) context.packageName + name else name
            }
        }
    }

    data class AnimInfo(
        val enterAnim: Int = 0,
        val exitAnim: Int = 0,
        val popEnterAnim: Int = 0,
        val popExitAnim: Int = 0
    )
}
