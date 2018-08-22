package com.chequer.tendermint.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

@Slf4j
public class HashUtils {

    public static String MD5(String str) {
        byte[] bytes = DigestUtils.md5(str);
        return bytesToString(bytes);
    }

    public static String SHA256(String str) {
        byte[] bytes = DigestUtils.sha256(str);
        return bytesToString(bytes);
    }

	/*
    public static String SHA512(String str) {
		byte[] bytes = DigestUtils.sha512(str);
		return bytesToString(bytes);
	}
	*/

    public static String SHA1(String str) {
        byte[] bytes = DigestUtils.sha1(str);
        return bytesToString(bytes);
    }

    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte _byte : bytes) {
            sb.append(Integer.toString((_byte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String base64Encode(String string) {
        try {
            return base64Encode(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return "";
    }

    public static String base64Encode(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }

    public static String base64Decode(byte[] byteArrays) {
        try {
            return new String(Base64.decodeBase64(byteArrays), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return "";
    }

    public static String base64Decode(String str) {
        try {
            return new String(Base64.decodeBase64(str.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    private static String iv;
    private static Key keySpec;

    static {
        String key = "l25232trf6104z3439q8zo6zix8k6s39";
        iv = key.substring(0, 16);

        byte[] keyBytes = new byte[0];
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        SecretKeySpec _keySpec = new SecretKeySpec(keyBytes, "AES");
        keySpec = _keySpec;
    }

    public static String aesEncode(String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec("c98bd8f1dfb5151cd368924dff2d1a24".getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            return new String(Base64.encodeBase64(cipher.doFinal(str.getBytes("UTF-8"))), "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static String aesDecode(String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec("c98bd8f1dfb5151cd368924dff2d1a24".getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return new String(cipher.doFinal(Base64.decodeBase64(str)), "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
}
