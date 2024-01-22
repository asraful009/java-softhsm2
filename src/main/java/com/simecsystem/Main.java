package com.simecsystem;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;


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
        try {
//            BouncyCastleProvider provider = new BouncyCastleProvider();
//            Security.addProvider((Provider) provider);

            Provider p = Security.getProvider("SunPKCS11");
            p = p.configure("/usr/lib/jvm/java-11-openjdk-amd64/conf/security/pkcs11.cfg");
//            Security.addProvider(p);
//            KeyStore keyStore = KeyStore.getInstance("PKCS11", p);
//            keyStore.load(null, "1234".toCharArray());
//            Enumeration<String> aliases = keyStore.aliases();
//            while (aliases.hasMoreElements()) {
//                String alias = aliases.nextElement();
//                System.out.println("alias: " + alias);
//                X509Certificate certificate = (X509Certificate)keyStore.getCertificate(alias);
//                PublicKey publicKey = certificate.getPublicKey();
//                byte[] encodedPublicKey = publicKey.getEncoded();
//                String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);
//                System.out.println("Certificate: " + alias);
//                if (certificate instanceof X509Certificate) {
//                    X509Certificate x509Certificate = certificate;
//                    System.out.println("Alias: " + alias);
//                    System.out.println("Subject: " + x509Certificate.getSubjectDN());
//                    System.out.println("Issuer: " + x509Certificate.getIssuerDN());
//                    System.out.println("Serial Number: " + x509Certificate.getSerialNumber());
//                    System.out.println("Valid From: " + x509Certificate.getNotBefore());
//                    System.out.println("Valid Until: " + x509Certificate.getNotAfter());
//                    System.out.println("------------------------------");
//                }
//            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}