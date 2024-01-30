import pkcs11

lib_path = "/usr/local/lib/pkcs11/libeTPkcs11.dylib"
lib = pkcs11.lib(lib_path)
token = lib.get_token(token_label='dgdp')

data = b'INPUT DATA'

# Open a session on our token
with token.open(user_pin='Pwforsafenet5110!') as session:
    # Generate an EC keypair in this session from a named curve
    ecparams = session.create_domain_parameters(
        pkcs11.KeyType.EC, {
            pkcs11.Attribute.EC_PARAMS: pkcs11.util.ec.encode_named_curve_parameters('prime256v1'),
        }, local=True)
    pub, priv = ecparams.generate_keypair()

    # Sign
    signature = priv.sign(data)
