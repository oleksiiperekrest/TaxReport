package org.taxreport.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMd5(String input) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] messageDigest = md.digest(input.getBytes());


            BigInteger no = new BigInteger(1, messageDigest);
        //todo string builder
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;

    }
}
