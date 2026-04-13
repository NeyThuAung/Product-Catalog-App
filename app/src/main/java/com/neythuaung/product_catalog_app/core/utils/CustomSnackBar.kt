package com.neythuaung.product_catalog_app.core.utils
import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.google.android.material.snackbar.Snackbar
import com.neythuaung.product_catalog_app.R

@SuppressLint("ShowToast", "InflateParams")
fun showCustomSnackBar(
    parent: ViewGroup,
    message: String,
    isError: Boolean,
    duration: Int = Snackbar.LENGTH_LONG,
    forInternetConnectivity: Boolean = false,
    isInternetConnected: Boolean = false
) {
    // Create a basic SnackBar
    val snackBar = Snackbar.make(parent, "", duration)

    val snackBarView = snackBar.view
    // Set the SnackBar's gravity to the top
    snackBarView.updateLayoutParams<FrameLayout.LayoutParams> {
        gravity = Gravity.TOP
        topMargin = getStatusBarHeight(parent.context)
    }

    snackBarView.setBackgroundColor(
        ContextCompat.getColor(
            parent.context,
            R.color.transparent
        )
    ) // Transparent to show custom background

    // Inflate your custom layout
    val customView = LayoutInflater.from(parent.context).inflate(R.layout.custom_snackbar, null)

    // Configure the custom layout
    val container = customView.findViewById<ViewGroup>(R.id.snackbar_container)
    val icon = customView.findViewById<ImageView>(R.id.snackbar_icon)
    val text = customView.findViewById<TextView>(R.id.snackbar_text)
    val cancelIcon = customView.findViewById<ImageView>(R.id.snackbar_cancel_icon)

    text.text = message
    if (forInternetConnectivity) {
        container.setBackgroundColor(
            ContextCompat.getColor(
                parent.context,
                R.color.text_dark_grey_color
            )
        )
        if (isInternetConnected) {
            icon.setImageResource(R.drawable.ic_wifi)
        } else {
            icon.setImageResource(R.drawable.ic_wifi_off)
        }
    } else {
        if (isError) {
            container.setBackgroundColor(ContextCompat.getColor(parent.context, R.color.colorError))
            icon.setImageResource(R.drawable.ic_error)
        } else {
            container.setBackgroundColor(
                ContextCompat.getColor(
                    parent.context,
                    R.color.colorSuccess
                )
            )
            icon.setImageResource(R.drawable.ic_success)
        }
    }

    cancelIcon.setOnClickListener {
        snackBar.dismiss()
    }

    // Add your custom view to the SnackBar's layout
    val snackBarLayout = snackBarView as ViewGroup
    snackBarLayout.removeAllViews()
    snackBarLayout.addView(customView)
    snackBarLayout.setPadding(0, 0, 0, 0)

    snackBar.show()
}

private fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}
