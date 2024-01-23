package com.simecsystem;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;


public class Main {

    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static void main(String[] args) {
        File file = new File("/home/pavel/.keystore");
        try(InputStream is = new FileInputStream(file)) {

            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = "Dgdp@2023";
            keystore.load(is, password.toCharArray());


            Enumeration<String> enumeration = keystore.aliases();
            while(enumeration.hasMoreElements()) {
                String alias = enumeration.nextElement();
                System.out.println("alias name: " + alias);
                Certificate certificate = keystore.getCertificate(alias);
                System.out.println(certificate.toString());

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}