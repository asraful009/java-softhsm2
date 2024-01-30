from pkcs11 import PKCS11, CKM_RSA_PKCS_KEY_PAIR_GEN, CKO_PUBLIC_KEY, CKO_CERTIFICATE, CKM_SHA256_RSA_PKCS

# Specify the path to your PKCS#11 module (.so file)
pkcs11_lib_path = '/usr/local/lib/pkcs11/libeTPkcs11.dylib'

# Initialize PKCS#11 library
pkcs11 = PKCS11(pkcs11_lib_path)

# Set the PIN for the PKCS#11 device
pin = 'aSdf1234**'

# Load the certificates from the PKCS#11 device
with pkcs11.open_session(0, CKF_SERIAL_SESSION | CKF_RW_SESSION) as session:
    session.login(pin)

    # Get the list of object handles (certificates) in the token
    objects = session.get_objects({
        'class': CKO_CERTIFICATE,
    })

    for obj in objects:
        # Get the certificate information
        cert_info = obj.get_attribute({
            'class': CKO_CERTIFICATE,
            'type': CKC_X_509,
        })

        # Get the public key handle associated with the certificate
        public_key_handle = obj.get_attribute({
            'class': CKO_PUBLIC_KEY,
            'type': CKC_X_509,
        })

        # Get the public key information
        public_key_info = public_key_handle.get_attribute({
            'class': CKO_PUBLIC_KEY,
        })

        # Encode the public key in Base64 format
        b64_public_key = public_key_info['value'].hex()

        # Print or process the certificate information
        print("Alias:", cert_info['label'])
        print("Subject:", cert_info['subject'])
        print("Public Key:", b64_public_key)
        print()

        # You can store or process the information as needed
