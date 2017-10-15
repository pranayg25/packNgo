/**
 * Created by Pranay on 4/12/2017.
 */

function showModal(modalId) {
    $("#"+modalId).modal("show");
    $("#"+modalId).css("top","0px");
};

function closeModal (modalId) {
    $("#"+modalId).modal("hide");
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
};

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
};

function S4() {
    return (((1 + Math.random()) * 0x10000) | 0)
        .toString(16).substring(1);
};