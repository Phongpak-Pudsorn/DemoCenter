package com.starvision.config;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHandler {

    public String encrypt(String message, String key, String IV) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {

        byte[] srcBuff = message.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0,32).getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.substring(0,16).getBytes());
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] dstBuff = ecipher.doFinal(srcBuff);
        return Base64.encodeToString(dstBuff, Base64.DEFAULT);
    }

    public String decrypt(String encrypted, String key, String IV) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {

        SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0,32).getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.substring(0,16).getBytes());
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] raw = Base64.decode(encrypted.getBytes(), Base64.DEFAULT);
        byte[] originalBytes = ecipher.doFinal(raw);
        return new String(originalBytes, StandardCharsets.UTF_8);
    }

}

