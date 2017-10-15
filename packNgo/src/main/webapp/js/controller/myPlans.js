/**
 * Created by Pranay on 4/23/2017.
 */

app.controller('MyPlansController', ['$scope','myPlansService','$location', function($scope,myPlansService,$location) {

    $scope.init = function () {
        $scope.getMyPlans();
    };

    $scope.getMyPlans = function () {
        var user = JSON.parse(localStorage.getItem("user"));
        var value = myPlansService.getMyPlans(user.id).then(function(response){
            if(response.status == 200){
                $scope.plans = response.objectList;
                $scope.$apply();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.viewPlan = function (planId) {
        $location.path('itinenary/'+planId);
        $scope.$apply();
    };

    $scope.deletePlan = function (planId) {
        var value = myPlansService.deletePlan(planId).then(function(response){
            if(response.status == 200){
                $scope.init();
            }else{
                console.log(response.message);
            }
        });
    };


    $scope.init();
}]);
