import pkcs11


def main():
    lib = pkcs11.lib("/opt/softhsm2/lib/softhsm/libsofthsm2.so")
    token = lib.get_token(token_label='dgdp')
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

if __name__ == "__main__":
    main()