package com.linji.cabinetutil.util

import java.security.MessageDigest

object Util {

    @JvmStatic
    fun getMD5String(s: String): String {
//        Log.e("string", s)
        val instance = MessageDigest.getInstance("MD5")
        val abyte0 = instance.digest(s.toByteArray())

        return abyte0.hex().substring(8, 24)
    }

    private fun ByteArray.hex(): String {
        return joinToString("") { "%02d".format(it.toInt() and 0xFF) }
    }

    private var lastClickTime:Long = 0
    @JvmStatic
    fun isFastDoubleClick(): Boolean {
        var flag = false
        val time = System.currentTimeMillis()
        if (time - lastClickTime >= 500) {
            flag = true
        }
        lastClickTime = time
        return flag
    }

}