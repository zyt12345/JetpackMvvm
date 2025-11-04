package me.hgj.jetpackmvvm.demo.app.core.util

import android.content.Context
import android.view.LayoutInflater
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.core.net.NetUrl
import me.hgj.jetpackmvvm.demo.data.model.CacheConfig
import me.hgj.jetpackmvvm.demo.databinding.DialogAgreementBinding
import me.hgj.jetpackmvvm.demo.ui.activity.WebActivity
import me.hgj.jetpackmvvm.ext.util.clickNoRepeat
import me.hgj.jetpackmvvm.ext.util.currentActivity
import me.hgj.jetpackmvvm.ext.util.getColorExt
import me.hgj.jetpackmvvm.ext.view.textString

/**
 * 作者　：hegaojian
 * 时间　：2025/11/4
 * 说明　：
 */
object PolicyUtil {
    fun showPolicyDialog(context: Context,confirm: () -> Unit) {
        if (CacheConfig.isAgree) {
            confirm.invoke()
            return
        }
        MaterialDialog(context)
            .show {
                val dialogView =
                    DialogAgreementBinding.inflate(LayoutInflater.from(context))
                customView(view = dialogView.root)
                cancelOnTouchOutside(false)
                cancelable(false)
                cornerRadius(6f)
                dialogView.agreeText.movementMethod = ClickableMovementMethod.getInstance()
                dialogView.agreeText.text =
                    dialogView.agreeText.textString().replaceSpan("《用户授权协议》") {
                        HighlightSpan(getColorExt(R.color.colorPrimary)) {
                            WebActivity.start(
                                title = "用户授权协议",
                                url = NetUrl.Agreement.TERMS_OF_SERVICE
                            )
                        }
                    }.replaceSpan("《隐私政策》") {
                        HighlightSpan(getColorExt(R.color.colorPrimary)) {
                            WebActivity.start(
                                title = "隐私政策",
                                url = NetUrl.Agreement.PRIVACY_POLICY
                            )
                        }
                    }
                dialogView.dialogConfirm.clickNoRepeat {
                    //同意协议
                    CacheConfig.isAgree = true
                    dismiss()
                    confirm.invoke()
                }
                dialogView.dialogCancel.clickNoRepeat {
                    dismiss()
                    currentActivity?.finish()
                }
            }

    }
}