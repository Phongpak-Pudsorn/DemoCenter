package com.starvision.config

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5 {
    @JvmStatic
    fun CMD5(Cmd5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val digested = md.digest(Cmd5.toByteArray())
            return digested.joinToString("") {
                String.format("%02x", it)
            }
        } catch (e: NoSuchAlgorithmException) {
        }

        return null
    }

}