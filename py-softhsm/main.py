import pkcs11

lib_path = "/usr/local/Cellar/softhsm/2.6.1/lib/softhsm/libsofthsm2.so"
lib = pkcs11.lib(lib_path)
token = lib.get_token(token_label='MyLatestToken')

data = b'INPUT DATA'

# Open a session on our token
with token.open(user_pin='1234') as session:
    # Generate an AES key in this session
    key = session.generate_key(pkcs11.KeyType.AES, 256)

    # Get an initialisation vector
    iv = session.generate_random(128)  # AES blocks are fixed at 128 bits
    # Encrypt our data
    crypttext = key.encrypt(data, mechanism_param=iv)
    print(crypttext)