/**
 * Created by prana on 12/4/2016.
 */

app.service('homeService', function() {

    this.makeMyPlan = function (data) {
        var promise = $.ajax({
            url: host+"/rest/plan/make",
            type: "POST",
            data: JSON.stringify(data),
            headers: {"Authorization": "Basic " + btoa(data.userId + ":" + "password")},
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

});