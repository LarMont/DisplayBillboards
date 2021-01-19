package com.example.displaybillboards.utilities.serverapi

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.util.Log
import com.example.displaybillboards.R
import com.example.displaybillboards.utilities.BaseApplication

class DefaultProgressTracker(private val context: Activity) : ProgressTracker { ////заменить на более удобный

    private var dialog: ProgressDialog? = null
    private var callback: (() -> Unit)? = null

    override fun initCancel(callback: () -> Unit) {
        this.callback = callback
    }
    override fun start(loadingMessage: String) {
        dialog = ProgressDialog(context).apply {
            setMessage(loadingMessage)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            callback?.let { c ->
                setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    BaseApplication.getString(R.string.button_cancel)
                ) { _, _ -> c.invoke() }
            }
            try {
                show()
            } catch (t: Throwable) {
                Log.e("ProgressTracker", "start error: $t")
            }
        }
    }

    override fun end() {
        try {
            dialog?.dismiss()
        } catch (t: Throwable) {
            Log.e("ProgressTracker", "end error: $t")
        }
        dialog = null
    }

    override fun setProgress(progress: Float) {
    }
}