/**
 * Created by Pranay on 4/23/2017.
 */

app.controller('FriendsController', ['$scope','friendsService','$location', function($scope,friendsService,$location) {

    $scope.init = function () {
        $scope.getMyFriends();
    };


    $scope.getMyFriends = function () {
        var user = JSON.parse(localStorage.getItem("user"));
        var value = friendsService.getMyFriends(user.id).then(function(response){
            if(response.status == 200){
                $scope.friends = response.objectList;
                $scope.$apply();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.findFriends = function (valid) {
        if(valid){
            var user = JSON.parse(localStorage.getItem("user"));
            var data = {};
            data.userId = user.id;
            data.email = $scope.email;
            var value = friendsService.findFriends(data).then(function(response){
                if(response.status == 200){
                    $scope.newFriends = response.objectList;
                    $scope.$apply();
                }else{
                    $("#errorMessage").text(response.message);
                    showModal("errorModal");
                    console.log(response.message);
                }
            });
        }
    };

    $scope.addFriend = function (friendId) {
        showModal("spinnerModal");
        var data = {};
        data.userId = JSON.parse(localStorage.getItem("user")).id;
        data.friendId = friendId;
        var value = friendsService.addFriend(data).then(function(response){
            closeModal("spinnerModal");
            if(response.status == 200){
               // $scope.friends = response.objectList;
                $scope.newFriends = [];
                $scope.init();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.removeFriend = function (friendId) {
        var data = {};
        data.userId = JSON.parse(localStorage.getItem("user")).id;
        data.friendId = friendId;
        var value = friendsService.removeFriend(data).then(function(response){
            if(response.status == 200){
                // $scope.friends = response.objectList;
                $scope.init();
            }else{
                console.log(response.message);
            }
        });
    };

    $scope.init();
}]);

