package com.example.encodingMD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncoderPassword {
    public static String getEncodePassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return generatedPassword;
    }
}