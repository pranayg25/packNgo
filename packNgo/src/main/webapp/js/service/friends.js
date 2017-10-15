/**
 * Created by Pranay on 4/23/2017.
 */

app.service('friendsService', function() {

    this.getMyFriends = function (userId) {
        var promise = $.ajax({
            url: host+"/rest/friends/"+userId,
            type: "GET",
            contentType: "application/json",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.findFriends = function (data) {
        var promise = $.ajax({
            url: host+"/rest/friend/find",
            type: "GET",
            data:data,
            contentType: "application/json",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.addFriend = function (data) {
        var promise = $.ajax({
            url: host+"/rest/friend/add",
            type: "POST",
            data:JSON.stringify(data),
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.removeFriend = function (data) {
        var promise = $.ajax({
            url: host+"/rest/friend/delete",
            type: "DELETE",
            data:JSON.stringify(data),
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

});

