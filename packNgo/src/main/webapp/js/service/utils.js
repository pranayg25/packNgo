/**
 * Created by Pranay on 4/13/2017.
 */

app.service('utilService', function() {


    this.getActivities = function () {
        var promise = $.ajax({
            url: host+"/rest/activities",
            type: "GET",
            contentType: "application/json",
            dataType: "json"
        }).then(function (response) {
            return response;
        });
        return promise;
    };

});
