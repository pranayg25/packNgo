/**
 * Created by Pranay on 4/24/2017.
 */


app.controller('ExploreController', ['$scope','itineraryService','utilService','$location','$stateParams', function($scope,itineraryService,utilService,$location,$stateParams) {

    $scope.itineraryId = $stateParams.itineraryId;

    $scope.locations = [];
    $scope.selectedActivity = [];

    $scope.init = function () {
        $scope.getActivities();
    };

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

    $scope.viewLocation = function (locationId) {
        $location.path('location/'+locationId);
        $scope.$apply();
    };

    $scope.searchLocations = function () {
        showModal("spinnerModal");
        var data ={};
        data.activities = $scope.selectedActivity.join(",");
        //data.location =
        var value = itineraryService.searchLocations($scope.itineraryId,data).then(function(response){
            closeModal("spinnerModal");
            if(response.status == 200){
                $scope.locations = response.objectList;
                $scope.$apply();
            }else{
                console.log(response.message);
            }
        });
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

        $scope.searchLocations();
    };
    $scope.viewFriends = function () {
        $location.path('itinenary/'+$scope.itineraryId+"/friends");
        $scope.$apply();

    };
    $scope.viewItinerary = function () {
        $location.path('itinenary/'+$scope.itineraryId);
        $scope.$apply();
    };

    $scope.addLocation = function (index,location) {
        if($("[name=location_"+index+"]").val()!=""){
            var data = {};
            data.duration = $("[name=location_"+index+"]").val();
            data.placeId = location.placeId
            var value = itineraryService.addLocation($scope.itineraryId,data).then(function(response){
                if(response.status == 200){
                    $scope.locations.splice(index,1);
                    $scope.$apply();
                }else{
                    console.log(response.message);
                    $("#errorMessage").html(response.message);
                    showModal("errorModal");
                }
            });
        }
    };

    $scope.init();
}]);

