package com.starvision.config

import java.io.UnsupportedEncodingException
import java.util.Locale
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESHelper {
    // /** 算法/模式/填充 **/
    private const val CipherMode = "AES/ECB/PKCS5Padding"

    ///** 创建密钥 **/
    private fun createKey(password: String): SecretKeySpec {
        var password: String? = password
        var data: ByteArray? = null
        if (password == null) {
            password = ""
        }
        val sb = StringBuffer(32)
        sb.append(password)
        while (sb.length < 32) {
            sb.append("0")
        }
        if (sb.length > 32) {
            sb.setLength(32)
        }
        try {
            data = sb.toString().toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return SecretKeySpec(data, "AES")
    }

    // /** 加密字节数据 **/
    fun encrypt(content: ByteArray?, password: String): ByteArray? {
        try {
            val key = createKey(password)
            println(key)
            val cipher = Cipher.getInstance(CipherMode)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return cipher.doFinal(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    ///** 加密(结果为16进制字符串) **/
    @JvmStatic
    fun encrypt(content: String, password: String): String {
        var data: ByteArray? = null
        try {
            data = content.toByteArray(charset("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        data = encrypt(data, password)
        return byte2hex(data)
    }

    // /** 解密字节数组 **/
    fun decrypt(content: ByteArray?, password: String): ByteArray? {
        try {
            val key = createKey(password)
            val cipher = Cipher.getInstance(CipherMode)
            cipher.init(Cipher.DECRYPT_MODE, key)
            return cipher.doFinal(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    ///** 解密16进制的字符串为字符串 **/
    @JvmStatic
    fun decrypt(content: String, password: String): String? {
        var data: ByteArray? = null
        try {
            data = hex2byte(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        data = decrypt(data, password)
        if (data == null) return null
        var result: String? = null
        try {
            result = String(data, charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return result
    }

    // /** 字节数组转成16进制字符串 **/
    fun byte2hex(b: ByteArray?): String { // 一个字节的数，
        val sb = StringBuffer(b!!.size * 2)
        var tmp = ""
        for (n in b.indices) {
            // 整数转成十六进制表示
            tmp = Integer.toHexString(b[n].toInt() and 0XFF)
            if (tmp.length == 1) {
                sb.append("0")
            }
            sb.append(tmp)
        }
        return sb.toString().uppercase(Locale.getDefault()) // 转成大写
    }

    // /** 将hex字符串转换成字节数组 **/
    private fun hex2byte(inputString: String): ByteArray {
        var inputString: String? = inputString
        if (inputString == null || inputString.length < 2) {
            return ByteArray(0)
        }
        inputString = inputString.lowercase(Locale.getDefault())
        val l = inputString.length / 2
        val result = ByteArray(l)
        for (i in 0 until l) {
            val tmp = inputString.substring(2 * i, 2 * i + 2)
            result[i] = (tmp.toInt(16) and 0xFF).toByte()
        }
        return result
    }
}