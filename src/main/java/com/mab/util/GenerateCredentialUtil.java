package com.mab.util;

public class GenerateCredentialUtil {

    public String randomString(int length) {
        String stringSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] randomString = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * stringSet.length());
            randomString[i] = stringSet.charAt(rand);
        }

        return new String(randomString);
    }

}
