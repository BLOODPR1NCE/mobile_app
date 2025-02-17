package com.example.mobil.Domain.Utils

import android.text.TextUtils

fun String.EmailValid () : Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}