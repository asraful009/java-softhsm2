package com.simecsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

public class Main {
    public static int count = 0;
    final String KEYCHAIN_PATH = "/Users/imac/.keystore";
    final String SYSTEM_KEYCHAIN_PATH = "/System/Library/Keychains/SystemRootCertificates.keychain";
    final String AUTHORITY = "VeriSign";
    final String OSX_SEC_COMMAND = "security find-certificate -a -p -c %AUTHORITY% %KEYCHAIN%";
    final String OSX_SEC_COMMAND_test = "security find-certificate -a -p";
    final String cmd = "security find-certificate -a -p -c %AUTHORITY% %KEYCHAIN%".replace("%AUTHORITY%", "VeriSign").replace("%KEYCHAIN%", "/System/Library/Keychains/SystemRootCertificates.keychain");


    InputStream certificateStream = null;

    public void getCertificateList() {
        String keyStoreType = "KeychainStore";
        File file = new File("/Users/imac/.keystore");
        try (InputStream is = new FileInputStream(file)) {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//            String password = "123456";
            keyStore.load(null, null);

            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("alias: " + alias);
                Certificate certificate = keyStore.getCertificate(alias);
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509Certificate = (X509Certificate) certificate;
                    System.out.println("Alias: " + alias);
                    System.out.println("Subject: " + x509Certificate.getSubjectDN());
                    System.out.println("Issuer: " + x509Certificate.getIssuerDN());
                    System.out.println("Serial Number: " + x509Certificate.getSerialNumber());
                    System.out.println("Valid From: " + x509Certificate.getNotBefore());
                    System.out.println("Valid Until: " + x509Certificate.getNotAfter());
                    System.out.println("------------------------------");
                    PublicKey publicKey = x509Certificate.getPublicKey();
                    byte[] encodedPublicKey = publicKey.getEncoded();
                    String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new GetCertificateDongle().getCertificateListFromDongle();
//        new Main().getCertificateList();
    }
}

















































//package com.simecsystem;
//
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.NoSuchAlgorithmException;
//import java.security.Provider;
//import java.security.Security;
//import java.security.cert.Certificate;
//import java.util.Enumeration;
//
//
//public class Main {
//
//    public static String bytesToHex(byte[] bytes) {
//        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
//        char[] hexChars = new char[bytes.length * 2];
//        for (int j = 0; j < bytes.length; j++) {
//            int v = bytes[j] & 0xFF;
//            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
//            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
//        }
//        return new String(hexChars);
//    }
//    public static void main(String[] args) {
//        File file = new File("/Users/imac/.keystore");
//        try(InputStream is = new FileInputStream(file)) {
//
//            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//            String password = "123456";
//            keystore.load(is, password.toCharArray());
//
//
//            Enumeration<String> enumeration = keystore.aliases();
//            while(enumeration.hasMoreElements()) {
//                String alias = enumeration.nextElement();
//                System.out.println("alias name: " + alias);
//                Certificate certificate = keystore.getCertificate(alias);
//                System.out.println(certificate.toString());
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//}