package com.simecsystem;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;


public class GetCertificateDongle {

    public void getCertificateListFromDongle() {
        try {
            PrivateKey privateKey = null;
            char[] password = "aSdf1234**".toCharArray();
            String configName = "/Users/imac/projects/dgdp/java-softhsm2/pkcs11.cfg";
            String xmlFilePath = "/Users/imac/projects/dgdp/java-softhsm2/test.xml";
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
                X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
//                System.out.println(certificate);
                PrivateKey key = (PrivateKey)keyStore.getKey(alias, "aSdf1234**".toCharArray());
                System.out.println("Key: " + key);
                PublicKey publicKey = certificate.getPublicKey();
                byte[] encodedPublicKey = publicKey.getEncoded();
                String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);

                System.out.println("Certificate: " + alias);
                Security.addProvider((Provider)new BouncyCastleProvider());
                Provider pro = Security.getProvider("BC");
                System.out.println(pro.getName());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFilePath));
                System.out.println(doc.getDocumentElement());
                XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
                System.out.println(key);
                DOMSignContext dsc = new DOMSignContext(key, doc.getDocumentElement());
                Reference ref =
                    signatureFactory.newReference("",
                        signatureFactory.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
                        Collections.singletonList(
                                signatureFactory.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature",
                                (TransformParameterSpec)null)), null, null);
                SignedInfo si = signatureFactory.newSignedInfo(signatureFactory
                                .newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments",
                                        (C14NMethodParameterSpec)null),
                        signatureFactory.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null),
                        Collections.singletonList(ref));
                KeyInfoFactory kif = signatureFactory.getKeyInfoFactory();
                KeyValue kv = kif.newKeyValue(certificate.getPublicKey());
                KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
                XMLSignature signature = signatureFactory.newXMLSignature(si, ki);
                signature.sign(dsc);
                System.out.println(doc);
                Node root = doc.getDocumentElement();

                // Print information about the root element
                System.out.println("Root Element: " + root.getNodeName());

                // Process child nodes (elements) of the root
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);

                    // Check if the node is an element (as opposed to text, comment, etc.)
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        // Print information about each child element
                        System.out.println("Element Name: " + childNode.getNodeName());
                        System.out.println("Element Value: " + childNode.getTextContent());
                    }
                }
                saveXmlDocument(doc, xmlFilePath);
            }
        } catch (Exception e) {
            System.out.println(e);

        }
        }
    private static void saveXmlDocument(Document doc, String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(filePath)));
        System.out.println("Signed XML document saved to: " + filePath);
    }


    }
