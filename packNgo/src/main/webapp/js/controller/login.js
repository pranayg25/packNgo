/**
 * Created by Pranay on 4/12/2017.
 */

app.controller('LoginController', ['$scope','loginService','$location', function($scope,loginService,$location) {

    $scope.login = function (valid) {
        if(valid){
            var encryptPassword = encryptMessage($scope.loginData.password);
            var user = {};
            user.email = $scope.loginData.email;
            user.password = encryptPassword.encrypted_msg;
            var value = loginService.loginUser(user,encryptPassword).then(function(response){
                if(response.status == 200){
                    $scope.$root.isLoggedIn = true;
                    sessionStorage.setItem("isLoggedIn",true);
                    localStorage.setItem("user",JSON.stringify(response.objectList[0]));

                    $location.path('home');
                    $scope.$apply();
                }
                else{
                    console.log(response.message);
                    $("#errorMessage").text(response.message);
                    showModal("errorModal")
                }
            });
        }
    };


    $scope.signUp = function (valid) {
        if(valid){
            showModal("spinnerModal");
            var encryptPassword = encryptMessage($scope.password);
            var user = {};
            user.firstName = $scope.firstName;
            user.lastName = $scope.lastName;
            user.email = $scope.email;
            user.password = encryptPassword.encrypted_msg;
            var value = loginService.signUp(user,encryptPassword).then(function(response){
                closeModal("spinnerModal");
                if(response.status == 200){
                    $scope.loginData = {};
                    $scope.loginData.password = $scope.password;
                    $scope.loginData.email = $scope.email;
                    $scope.login(true);
                }
                else{
                    console.log(response.message);
                    $("#errorMessage").text(response.message);
                    showModal("errorModal")
                }
            });
        }
    };
}]);
