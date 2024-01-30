import pkcs11

lib_path = "/usr/local/lib/pkcs11/libeTPkcs11.dylib"

# lib_path = "/usr/local/Cellar/softhsm/2.6.1/lib/softhsm/libsofthsm2.so"

lib = pkcs11.lib(lib_path)
token = lib.get_token(token_label='dgdp')
# token = lib.get_token(token_label='29thJanNewToken')
data = b'hi all'

# Open a session on our token
# with token.open(user_pin='Pwforsafenet5110!') as session:
# with token.open(user_pin='1234') as session:
with token.open(user_pin='aSdf1234**') as session:
#     pub, priv = session.generate_keypair(pkcs11.KeyType.DSA,2048)
    pub, priv = session.generate_keypair(pkcs11.KeyType.RSA, 2048)

    # Sign
    signature = priv.sign(data)
    print(signature)