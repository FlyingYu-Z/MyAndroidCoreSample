package cn.beingyi.androidcore.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import cn.beingyi.androidcore.R

/**
 * created by zhengyu
 * on 2020/5/23
 * 用于快速创建自定义对话框
 */
abstract class BaseDialogKt(var context: Context) {

    var dialog: Dialog
    abstract val contentView: Int
    abstract fun initView()
    abstract fun onCreate()
    abstract fun config(): Config

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    protected fun <T : View?> findView(viewId: Int): T {
        return dialog.findViewById<View>(viewId) as T
    }

    inner class Config {
        var isHasBgShadow = false
        var gravity = 0
    }

    init {
        dialog = Dialog(context)
        val config = config()
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        if (!config.isHasBgShadow) {
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        dialog.setContentView(contentView)
        initView()
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels
        val heightPixels = outMetrics.heightPixels
        val p = dialog.window!!.attributes
        p.gravity = Gravity.TOP
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT
        p.width = widthPixels
        //p.alpha = 0.9f;
        dialog.window!!.setGravity(config.gravity)
        dialog.window!!.attributes = p
        dialog.window!!.setWindowAnimations(R.style.ActionSheetDialogAnimation)
        onCreate()
    }
}