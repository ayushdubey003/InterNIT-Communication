package com.nitconclave.internitcommunication.Helpers;

import android.util.Log;

import java.security.MessageDigest;

public class Encryptor {
    private static String mSecret;

    public static String GenerateSecret(String s) {
        byte[] plaintext = s.getBytes();
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(plaintext);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            mSecret = sb.toString();
            return mSecret;
        } catch (Exception e) {
            Log.e("this", "" + e);
            return null;
        }
    }
}