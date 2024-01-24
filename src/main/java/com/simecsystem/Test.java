package com.simecsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Enumeration;

public class Test {

    final String KEYSTORE_PATH = "/Users/imac/.keystore";
    static final String KEYSTORE_PASSWORD = "123456";

    public static void getCertificateList() {
        String keyStoreType = "JKS";
        File file = new File("/Users/imac/.keystore");
        try (InputStream is = new FileInputStream(file)) {
            char[] password = KEYSTORE_PASSWORD.toCharArray();

            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(is, password);

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
                    System.out.println("Public Key: " + b64PublicKey);
                    // Use b64PublicKey as needed
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        getCertificateList();
    }
}
