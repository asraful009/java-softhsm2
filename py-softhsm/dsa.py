import pkcs11

lib_path = "/usr/local/lib/pkcs11/libeTPkcs11.dylib"

# lib_path = "/usr/local/Cellar/softhsm/2.6.1/lib/softhsm/libsofthsm2.so"

lib = pkcs11.lib(lib_path)
token = lib.get_token(token_label='dgdp')
# token = lib.get_token(token_label='29thJanNewToken')
data = b'hi all'

with token.open(user_pin='aSdf1234**') as session:


    objects = session.findObjects([
        (Attribute.CLASS, CKO_CERTIFICATE)
    ])

    for obj in objects:
        cert_value = session.getAttributeValue(obj, [CKA_VALUE])[0].to_bytes()

        # Display or process the certificate as needed
        print("Certificate:", cert_value.hex())

