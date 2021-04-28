package com.mobiproplus.sharedplanet.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showLongToast(message: CharSequence) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showShortToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}