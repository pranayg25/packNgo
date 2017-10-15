/**
 * Created by Pranay on 4/23/2017.
 */

app.service('myPlansService', function() {

    this.getMyPlans = function (userId) {
        var promise = $.ajax({
            url: host+"/rest/plans/"+userId,
            type: "GET",
            contentType: "application/json",
            headers: {"Authorization": "Basic " + btoa(userId + ":" + "password")},
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

    this.deletePlan = function (planId) {
        var promise = $.ajax({
            url: host+"/rest/plan/"+planId,
            type: "DELETE",
            contentType: "application/json",
            headers: {"Authorization": "Basic " + btoa("userId" + ":" + "password")},
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };


});
