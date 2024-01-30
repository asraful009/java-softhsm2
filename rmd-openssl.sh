echo "https://xn--verschlsselt-jlb.it/generate-rsa-ecc-and-aes-keys-with-opensc-pkcs11-tool/"

pkcs11-tool --module=/opt/softhsm2/lib/softhsm/libsofthsm2.so \
--token-label cyber009 --login --pin 1234 --keypairgen \
--mechanism ECDSA-KEY-PAIR-GEN --key-type EC:secp384r1 --usage-sign \
--label root0 --id 0

pkcs11-tool --module=/opt/softhsm2/lib/softhsm/libsofthsm2.so --login --pin 1234 --list-objects

pkcs11-tool --module=/opt/softhsm2/lib/softhsm/libsofthsm2.so \
--token-label cyber009 --login --pin 1234 --delete-object --label root0

pkcs11-tool --module=/opt/softhsm2/lib/softhsm/libsofthsm2.so \
--token-label cyber009 --login --pin 1234 -M

echo ">>>  SHA512-RSA-PKCS"


pkcs11-tool --module=/opt/softhsm2/lib/softhsm/libsofthsm2.so \
--token-label cyber009 --login --pin 1234 -r --id 0 --type cert  > sssssss.cert


echo "https://xn--verschlsselt-jlb.it/openssl-first-steps/"
openssl ecparam -list_curves
openssl ecparam -genkey -name prime256v1

openssl ecparam -genkey -name prime256v1 | openssl ec -aes256 -out private_key_prime256v1.pem
openssl ec -in private_key_prime256v1.pem -pubout -out public_key_prime256v1.pem

openssl dgst -sign private_key_prime256v1.pem \
-keyform PEM -sha256 -out signature_prime256v1_sign_py.sign -binary sign.py

openssl dgst -verify public_key_prime256v1.pem \
-keyform PEM -sha256 -signature signature_prime256v1_sign_py.sign -binary sign.py

# -------------------------------------------------------------------------------
