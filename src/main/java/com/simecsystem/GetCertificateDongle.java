package com.simecsystem;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

public class GetCertificateDongle {

    public void getCertificateListFromDongle() {
        try {
            PrivateKey privateKey = null;
            char[] password = "Pwforsafenet5110!".toCharArray();
            String configName = "pkcs11.cfg";
            Provider p = Security.getProvider("SunPKCS11");
            p = p.configure(configName);
            Security.addProvider(p);
            KeyStore keyStore = KeyStore.getInstance("PKCS11", p);
            keyStore.load(null, password);
            System.out.println("Executed properly");
            System.out.println("size: " + keyStore.size());
            String alias = null;
            for (Enumeration<String> e = keyStore.aliases(); e.hasMoreElements(); ) {
                alias = e.nextElement();
                String aliasTemp = URLEncoder.encode(alias, StandardCharsets.UTF_8);
                X509Certificate certificate = (X509Certificate)keyStore.getCertificate(alias);
                PublicKey publicKey = certificate.getPublicKey();
                byte[] encodedPublicKey = publicKey.getEncoded();
                String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);
                System.out.println("Name: " + certificate.getSubjectX500Principal().getName());
                System.out.println(b64PublicKey);
                System.out.println(aliasTemp);
                System.out.println(alias.replaceAll("\\s", ""));
                System.out.println("Certificate: " + alias);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }




}
