/**
 * Created by Pranay on 4/22/2017.
 */

app.service('itineraryService', function() {

    this.getItinerary = function (itineraryId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId,
            type: "GET",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.getLocation = function (locationId) {
        var promise = $.ajax({
            url: host+"/rest/location/"+locationId,
            type: "GET",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };


    this.getItineraryFriends = function (itineraryId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/friends",
            type: "GET",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };



    this.addFriendsToPlan = function (itineraryId,friendId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/friend/"+friendId,
            type: "POST",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.removeFriendsFromPlan = function (itineraryId,friendId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/friend/"+friendId,
            type: "DELETE",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.removeLocation = function (itineraryId,locationId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/location/"+locationId,
            type: "DELETE",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.decreaseTime = function (itineraryId,locationId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/location/"+locationId+"/time",
            type: "DELETE",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };
    this.increaseTime = function (itineraryId,locationId) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/location/"+locationId+"/time",
            type: "POST",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.searchLocations = function (itineraryId,data) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/locations",
            type: "GET",
            data:data,
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.addLocation = function (itineraryId,data) {
        var promise = $.ajax({
            url: host+"/rest/itinerary/"+itineraryId+"/location",
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

});
