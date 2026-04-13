package com.neythuaung.product_catalog_app.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun CharSequence?.isValidEmail() =
    Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()

fun isStrongPassword(password: String): Boolean {
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$".toRegex()
    return passwordPattern.matches(password)
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneRegex = Regex("^09\\d{9}$")
    return phoneRegex.matches(phoneNumber)
}

fun isValidInternationalPhoneNumber(number: String): Boolean {
    val phoneRegex = Regex("^[1-9]\\d{5,13}$")
    return phoneRegex.matches(number)
}

fun Context.showRationaleDialog(
    message: String,
    permissionLauncher: (Boolean) -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle("Permission Needed")
        .setMessage(message)
        .setPositiveButton("Allow") { _, _ ->
            permissionLauncher(true)
        }
        .show()
}

fun Context.showSettingsDialog() {
    AlertDialog.Builder(this)
        .setTitle("Permissions Denied")
        .setMessage("You have denied permissions permanently. Go to App Settings to manually enable them for full functionality.")
        .setPositiveButton("Go to Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        .show()
}

inline fun <T> sdk29AndUp(onSdk29: () -> T): T? { // Android SDK Version 29 = Android Version 10
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        onSdk29()
    } else null
}