package com.simecsystem;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;


public class Main {


    public static void main(String[] args) {
        Provider provider = Security.getProvider("SunPKCS11");
        String configName = "./pkcs11.cfg";
        provider = provider.configure(configName);
//        Security.addProvider(provider);
//        provider.getService("KeyStore", "PKCS11");
    }
}