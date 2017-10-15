/**
 * Created by Indrajit Pingale on 6/10/14.
 */

/**
 * Create GUID
 * @returns {string}
 * @constructor
 */
function S4() {
    return (((1 + Math.random()) * 0x10000) | 0)
        .toString(16).substring(1);
}

/**
 * Encrypt Message
 * @param message A message to encrypt
 * @returns {Object}
 */
function encryptMessage(message) {
    // key size
    var keySize = 128;

    // number of iteration
    var iterations = 2;

    // randomly generated passphrase
    var passPhrase = (S4() + S4() + S4()
        + "4" + S4().substr(0, 3)
        + S4() + S4() + S4() + S4())
        .toLowerCase();

    // randomly generated iv
    var iv = (S4() + S4() + S4() + "4"
        + S4().substr(0, 3) + S4()
        + S4() + S4() + S4())
        .toLowerCase();

    // randomly generated SALT
    var salt = (S4() + S4() + S4() + "4"
        + S4().substr(0, 3) + S4()
        + S4() + S4() + S4())
        .toLowerCase();

    // instantiate AesUtils with key size and iterations
    var aesUtil = new AesUtil(keySize,
        iterations);

    // encrypt message
    var encrypt = aesUtil.encrypt(salt, iv,
        passPhrase, message);

    var privateObject = new Object();
    privateObject.kSize = keySize;
    privateObject.iterations = iterations;
    privateObject.passPhrase = passPhrase;
    privateObject.iv = iv;
    privateObject.salt = salt;
    privateObject.encrypted_msg = encrypt;

    return privateObject;
}

/**
 * Decrypt Message
 * @param privateObj
 * @returns {*}
 */
function decryptMessage(privateObj) {
    // key size
    var keySize = privateObj.kSize;

    // number of iteration
    var iterations = privateObj.iterations;

    // privateObject passphrase
    var passPhrase = privateObj.passPhrase;

    // privateObject iv
    var iv = privateObj.iv;

    // privateObject SALT
    var salt = privateObj.salt;

    // instantiate AesUtils with key size and iterations
    var aesUtil = new AesUtil(keySize,
        iterations);

    // encrypt message
    var decrypt = aesUtil.decrypt(salt, iv,
        passPhrase, privateObj.encrypted_msg);

    return decrypt;

}