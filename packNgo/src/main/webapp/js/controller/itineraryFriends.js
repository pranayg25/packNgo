/**
 * Created by Pranay on 4/24/2017.
 */

app.controller('ItineraryFriendsController', ['$scope','itineraryService','utilService','friendsService','$location','$stateParams', function($scope,itineraryService,utilService,friendsService,$location,$stateParams) {

    $scope.itineraryId = $stateParams.itineraryId;

    $scope.friends =[];
    $scope.user = {};

    $scope.friendsNotInPlan = [];

    $scope.function1 = function(){
        if($scope.friends!=null && $scope.friends!=undefined && $scope.friends.length>0){
            var finalFriends = $scope.friendsNotInPlan.concat([]);

            if($scope.friendsNotInPlan!=null && $scope.friendsNotInPlan!=undefined && $scope.friendsNotInPlan.length>0){
                for(var i=0;i<$scope.friendsNotInPlan.length;i++){
                    for(var j=0;j<$scope.friends.length;j++){
                        if($scope.friendsNotInPlan[i].id == $scope.friends[j].id){
                            finalFriends.splice(finalFriends.indexOf($scope.friendsNotInPlan[i]),1);
                        }
                    }
                }
            }

            $scope.friendsNotInPlan = finalFriends;
            $scope.$apply();
        }
    };

    $scope.init = function () {
        $scope.user = JSON.parse(localStorage.getItem("user"));
        $scope.getItineraryFriends(function () {
            $scope.getMyFriends(function () {
                $scope.function1();
                for(var i = 0;i<$scope.friends.length;i++){
                    if($scope.user.id == $scope.friends[i].id){
                        $scope.user.creator = $scope.friends[i].creator;
                        $scope.$apply();
                        break;
                    }
                }
            });
        });

    };


    $scope.getMyFriends = function (callback) {
        var user = JSON.parse(localStorage.getItem("user"));
        var value = friendsService.getMyFriends($scope.user.id).then(function(response){
            if(response.status == 200){
                $scope.friendsNotInPlan = response.objectList;
                $scope.$apply();
                callback();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.getItineraryFriends = function (callback) {
        var value = itineraryService.getItineraryFriends($scope.itineraryId).then(function(response){
            if(response.status == 200){
                $scope.friends = response.objectList;
                $scope.$apply();
                callback();
                console.log(response);
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.addFriendsToPlan = function (friendId) {
        showModal("spinnerModal");
        var value = itineraryService.addFriendsToPlan($scope.itineraryId,friendId).then(function(response){
            closeModal("spinnerModal");
            if(response.status == 200){
                $scope.init();
                console.log(response);
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.removeFriendsFromPlan = function (friendId) {
        var value = itineraryService.removeFriendsFromPlan($scope.itineraryId,friendId).then(function(response){
            if(response.status == 200){
                $scope.init();
                console.log(response);
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.viewItinerary = function () {
        $location.path('itinenary/'+$scope.itineraryId);
        $scope.$apply();
    };
    $scope.viewExplorer = function (locationId) {
        $location.path("/itinenary/"+$scope.itineraryId+"/explore");
        $scope.$apply();
    };


    $scope.init();

}]);

