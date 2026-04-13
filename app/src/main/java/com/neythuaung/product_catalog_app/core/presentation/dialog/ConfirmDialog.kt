package com.neythuaung.product_catalog_app.core.presentation.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import com.neythuaung.product_catalog_app.R

class ConfirmDialog(var activity: FragmentActivity) {
    var dialog: Dialog? = null
    private lateinit var btnOkay: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var iconImg: ImageView
    private lateinit var imgClose: ImageButton

    fun showDialog(
        title: String,
        description: String = "",
        img: Int = R.drawable.ic_red_delete,
        tvOkayText: String = "",
        okayTextColor: String = "#FF4d4d",
        alertButtonListener: AlertButtonListener,
    ) {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
                return
            } else {
                setData(title, description, img, okayTextColor)
                dialog!!.show()
            }
        } else {

            dialog = Dialog(activity)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.delete_dialog_layout)
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog!!.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.CENTER

            dialog!!.window!!.attributes = lp

            btnOkay = dialog?.findViewById(R.id.mbDelete)!!
            btnCancel = dialog?.findViewById(R.id.mbDeleteDialogCancel)!!
            tvTitle = dialog?.findViewById(R.id.tvDeleteDialogTitle)!!
            tvDescription = dialog?.findViewById(R.id.tvDeleteDialogDesc)!!
            iconImg = dialog?.findViewById(R.id.ivDeleteDialogAlarm)!!
            imgClose = dialog?.findViewById(R.id.ibDeleteDialogCancel)!!

            if (tvOkayText.isNotEmpty()) {
                btnOkay.text = tvOkayText
            }
            btnOkay.setBackgroundColor(Color.parseColor(okayTextColor))
            setData(title, description, img, okayTextColor)


            imgClose.setOnClickListener {
                dialog?.hide()
            }

            btnOkay.setOnClickListener {
                dialog?.hide()
                alertButtonListener.onPositiveClick()
            }
            btnCancel.setOnClickListener {
                dialog?.hide()
                alertButtonListener.onCancelClick()
            }

            dialog?.show()
        }
    }

    private fun setData(
        title: String,
        description: String,
        img: Int,
        okayTextColor: String
    ) {

        tvDescription.isVisible = description.isNotEmpty()
        tvDescription.text = description

        tvTitle.text = title
        iconImg.setImageResource(img)
        btnOkay.setBackgroundColor(Color.parseColor(okayTextColor))
    }

    fun hideDialog() {
        if (dialog != null)
            dialog?.dismiss()
    }

    interface AlertButtonListener {
        fun onPositiveClick()
        fun onCancelClick()
    }

}


