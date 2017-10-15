/**
 * Created by Pranay on 4/12/2017.
 */

app.service('loginService', function() {


    this.loginUser = function (data,header) {
        var promise = $.ajax({
            url: host+"/rest/user/authenticate",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            headers: {'token1': header.iv, 'token2': header.salt, 'token3': header.passPhrase, 'size': header.kSize, 'iteration': header.iterations},
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.signUp = function (data,header) {
        var promise = $.ajax({
            url: host+"/rest/user/signup",
            type: "POST",
            data: JSON.stringify(data),
            headers: {'token1': header.iv, 'token2': header.salt, 'token3': header.passPhrase, 'size': header.kSize, 'iteration': header.iterations},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

});
