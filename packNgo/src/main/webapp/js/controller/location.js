/**
 * Created by Pranay on 4/23/2017.
 */

app.controller('LocationController', ['$scope','itineraryService','utilService','$location','$stateParams', function($scope,itineraryService,utilService,$location,$stateParams) {

    $scope.locationId = $stateParams.locationId;

    $scope.location = {};

    $scope.init = function () {
        $scope.getLocation();
    };

    $scope.getLocation = function () {
        var value = itineraryService.getLocation($scope.locationId).then(function(response){
            if(response.status == 200){
                console.log(response);
                $scope.location = response.objectList[0];
                $scope.$apply();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.init();

}]);

