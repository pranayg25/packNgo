/**
 * Created by Pranay on 4/22/2017.
 */

app.controller('ItineraryController', ['$scope','itineraryService','utilService','$location','$stateParams', function($scope,itineraryService,utilService,$location,$stateParams) {

    $scope.itineraryId = $stateParams.itineraryId;

    $scope.itineraries = [];
    $scope.init = function () {
        $scope.getItinerary();
    };

    $scope.getItinerary = function () {
        var value = itineraryService.getItinerary($scope.itineraryId).then(function(response){
            if(response.status == 200){
                console.log(response);
                $scope.itineraries = response.objectList;
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

    $scope.viewExplorer = function (locationId) {
        $location.path("/itinenary/"+$scope.itineraryId+"/explore");
        $scope.$apply();
    };

    $scope.viewFriends = function () {
        $location.path('itinenary/'+$scope.itineraryId+"/friends");
        $scope.$apply();

    };

    $scope.removeLocation = function(locationId){
        var value = itineraryService.removeLocation($scope.itineraryId,locationId).then(function(response){
            if(response.status == 200){
                $scope.init();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.increaseTime = function(locationId){
        var value = itineraryService.increaseTime($scope.itineraryId,locationId).then(function(response){
            if(response.status == 200){
                $scope.init();
            }else{
                console.log(response.message);
                $("#errorMessage").html(response.message);
                showModal("errorModal");
            }
        });
    };

    $scope.decreaseTime = function(locationId){
        var value = itineraryService.decreaseTime($scope.itineraryId,locationId).then(function(response){
            if(response.status == 200){
                $scope.init();
            }else{
                console.log(response.message);
                $("#errorMessage").html(response.message);
                showModal("errorModal");
            }
        });
    };

    $scope.init();

}]);
