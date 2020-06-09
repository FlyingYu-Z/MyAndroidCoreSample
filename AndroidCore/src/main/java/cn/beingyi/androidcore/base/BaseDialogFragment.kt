package cn.beingyi.androidcore.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.DialogFragment

/**
 * created by zhengyu
 * on 2020/6/9
 */
abstract class BaseDialogFragment : DialogFragment() {
    var rootView: View? = null
    abstract val contentView: Int
    abstract fun initView()
    abstract fun onCreate()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater.inflate(contentView, container, false)
        }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        initView()
        onCreate()
        return rootView
    }

    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        dialog?.window!!.setLayout(dm.widthPixels, dialog?.window!!.attributes.height)
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(-0x1000000))
        dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(true)
        //setStyle(R.style.ActionSheetDialogStyle, R.style.AppTheme)
        val dialogWindow = dialog?.window
        dialogWindow!!.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogWindow.attributes = lp

    }


    protected fun <T : View?> findView(viewId: Int): T {
        return rootView!!.findViewById<View>(viewId) as T
    }


}