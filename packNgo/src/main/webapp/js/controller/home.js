/** * Created by prana on 12/3/2016.
 */
//var homeControllers = angular.module('homeControllers', []);
app.controller('HomeController', ['$scope','homeService','utilService','$location', function($scope,homeService,utilService,$location) {

    $scope.selectedActivity = [];

    $scope.$on('place_changed', function (e, place) {
        // do something with place
        console.log(place)
        var location = place.address_components[0];
        if(jQuery.inArray("locality",location.types)>=0 && jQuery.inArray("political",location.types)>=0){
            console.log(location.long_name);
            $scope.location = location.long_name;
            $scope.latitude = place.geometry.location.lat();
            $scope.longitude = place.geometry.location.lng()
        }else{
            console.log("Kindly enter a city");
            $("#errorMessage").html("Invalid selection. Kindly enter a city.");
            showModal("errorModal");
        }

    });

    $scope.getActivities = function () {
        var value = utilService.getActivities().then(function(response){
            if(response.status == 200){
                $scope.activities = response.objectList;
                $scope.$apply();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.makeMyPlan = function (valid) {
        if(valid){
            showModal("spinnerModal");
            var planData = {};
            planData.location = $scope.location;
            planData.dateStart = new Date($scope.startDate);
            planData.dateEnd = new Date($scope.endDate);
            planData.hours = $scope.hours;
            planData.activities = $scope.selectedActivity;
            planData.lat = $scope.latitude;
            planData.lon = $scope.longitude;
            planData.userId = (JSON.parse(localStorage.getItem("user"))).id;
            var value = homeService.makeMyPlan(planData).then(function(response){
                closeModal("spinnerModal");
                if(response.status == 200){
                    console.log(response);
                    $location.path('itinenary/'+response.objectList[0]);
                    $scope.$apply();
                }else{
                    console.log(response.message);
                    $("#errorMessage").html(response.message);
                    showModal("errorModal")
                }
            });
        }
    };

    $scope.toggleActivitySelection = function (activity) {
        var idx = $scope.selectedActivity.indexOf(activity);

        // Is currently selected
        if (idx > -1) {
            $scope.selectedActivity.splice(idx, 1);
        }

        // Is newly selected
        else {
            $scope.selectedActivity.push(activity);
        }
    };

    $scope.init = function () {
        $scope.getActivities();
    };

    $scope.init();

}]);