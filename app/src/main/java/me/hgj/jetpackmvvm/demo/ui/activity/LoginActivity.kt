package me.hgj.jetpackmvvm.demo.ui.activity

import android.os.Bundle
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import me.hgj.jetpackmvvm.core.data.obs
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.core.base.BaseActivity
import me.hgj.jetpackmvvm.demo.app.core.net.NetUrl
import me.hgj.jetpackmvvm.demo.app.core.util.UserManager
import me.hgj.jetpackmvvm.demo.data.vm.UserViewModel
import me.hgj.jetpackmvvm.demo.databinding.ActivityLoginBinding
import me.hgj.jetpackmvvm.ext.util.clickNoRepeat
import me.hgj.jetpackmvvm.ext.util.getColorExt
import me.hgj.jetpackmvvm.ext.util.intent.openActivity
import me.hgj.jetpackmvvm.ext.util.setOnclick
import me.hgj.jetpackmvvm.ext.view.afterTextChange
import me.hgj.jetpackmvvm.ext.view.showDialogMessage
import me.hgj.jetpackmvvm.ext.view.showPwd
import me.hgj.jetpackmvvm.ext.view.textString
import me.hgj.jetpackmvvm.ext.view.visibleOrInvisible

/**
 * 作者　：hegaojian
 * 时间　：2025/10/10
 * 说明　：
 */
class LoginActivity : BaseActivity<UserViewModel, ActivityLoginBinding>() {

    override val showTitle = true

    override val title = "登录"

    override fun initView(savedInstanceState: Bundle?) {
        mBind.userName.afterTextChange {
            mBind.clearImg.visibleOrInvisible(it.isNotEmpty())
        }
        mBind.password.afterTextChange {
            mBind.pwdVisible.visibleOrInvisible(it.isNotEmpty())
        }
        mBind.pwdVisible.setOnCheckedChangeListener { checkbox, checked ->
            mBind.password.showPwd(checked)
        }
        mBind.agreementTv.movementMethod = ClickableMovementMethod.getInstance()
        mBind.agreementTv.text = mBind.agreementTv.textString().replaceSpan("《服务协议》"){
            HighlightSpan(getColorExt(R.color.colorPrimary)) {
                WebActivity.start(title = "服务协议", url = NetUrl.Agreement.TERMS_OF_SERVICE)
            }
        }.replaceSpan("《隐私政策》"){
            HighlightSpan(getColorExt(R.color.colorPrimary)) {
                WebActivity.start(title = "隐私政策", url = NetUrl.Agreement.PRIVACY_POLICY)
            }
        }
    }

    override fun onBindViewClick() {
        mBind.clearImg.clickNoRepeat {
            //清除用户名
            mBind.userName.setText("")
        }
        setOnclick(mBind.agreementTv,mBind.agreement){
            //协议
            mBind.agreement.isSelected = !mBind.agreement.isSelected
        }
        mBind.loginSub.clickNoRepeat {
            //登录
            val userName = mBind.userName.textString()
            val pwd = mBind.password.textString()
            when {
                userName.isEmpty() -> showDialogMessage("请填写账号")
                pwd.isEmpty() -> showDialogMessage("请填写密码")
                !mBind.agreement.isSelected -> showDialogMessage("请阅读并勾选协议")
                else -> {
                    mViewModel.loginFlow(userName, pwd).obs(this)  {
                        onSuccess {
                            //请求成功
                            UserManager.saveUser(it)
                            finish()
                        }
                        onError {
                            //请求失败 onError 可以不写的，不写就走默认处理
                            showDialogMessage(it.msg)
                        }
                    }
                }
            }
        }
        mBind.toRegister.clickNoRepeat {
            //去注册
            openActivity<RegisterActivity>()
        }
    }
}