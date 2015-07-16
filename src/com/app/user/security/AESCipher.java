/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class AESCipher {

    private final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,};

    public String encryptPassword(String username, String p1password, String p2password) throws AESCipherException {
        try {
            return encrypt(p2password, DigestUtils.md5Hex((username + p1password).getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | GeneralSecurityException ex) {
            throw new AESCipherException("AESCipher exception in encryptPassword method",ex);
        }
    }

    public String decryptPassword(String username, String p1password, String encryptedpass) throws AESCipherException {
        try {
            return decrypt(encryptedpass, DigestUtils.md5Hex((username + p1password).getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | GeneralSecurityException ex) {
            throw new AESCipherException("AESCipher exception in decryptPassword method",ex);
        } catch (IOException ex) {
            throw new AESCipherException("AESCipher exception in decryptPassword method",ex);
        }
    }

    private String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private byte[] base64Decode(String property) throws IOException {
        return Base64.decodeBase64(property);
    }

    private SecretKey makeKey(String key) throws GeneralSecurityException {
        char[] password = key.toCharArray();
        SecretKeyFactory factory
                = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password, SALT, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    private String encrypt(String plaintext, String key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher cipher;
        SecretKey secret = makeKey(key);
        cipher = Cipher.getInstance("AES/OFB8/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(new byte[16]));

        return base64Encode(cipher.doFinal(plaintext.getBytes("UTF-8")));

    }

    private String decrypt(String ciphertext, String key) throws GeneralSecurityException, IOException {
        SecretKey secret = makeKey(key);
        Cipher cipher = Cipher.getInstance("AES/OFB8/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(new byte[16]));
        return new String(cipher.doFinal(base64Decode(ciphertext)), "UTF-8");

    }
}
