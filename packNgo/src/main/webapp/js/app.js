/**
 * Created by Pranay on 4/6/2017.
 */

var host = "http://localhost:8080/websitename";
var app = angular.module('app', ['ui.router']);
app.directive('googlePlace', directiveFunction);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


/*
    $("#toggleSidebar").click(function(e) {
        $('.ui.labeled.icon.sidebar').sidebar('toggle');
    });
*/

    $stateProvider.state('home', {
        name: 'home',
        url: '/home',
        templateUrl: 'views/home.html',
        controller:"HomeController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('login', {
        name: 'login',
        url: '/',
        templateUrl: 'views/login.html',
        controller:"LoginController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('itinenary', {
        name: 'itinenary',
        url: '/itinenary/{itineraryId}',
        templateUrl: 'views/itinerary.html',
        controller:"ItineraryController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('myPlans', {
        name: 'myPlans',
        url: '/myPlans',
        templateUrl: 'views/myPlans.html',
        controller:"MyPlansController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('friends', {
        name: 'friends',
        url: '/friends',
        templateUrl: 'views/friends.html',
        controller:"FriendsController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('location', {
        name: 'location',
        url: '/location/{locationId}',
        templateUrl: 'views/location.html',
        controller:"LocationController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('itinenaryFriends', {
        name: 'itinenaryFriends',
        url: '/itinenary/{itineraryId}/friends',
        templateUrl: 'views/itinerary_friends.html',
        controller:"ItineraryFriendsController",
        data: {
            isLoggedIn: false
        }
    });
    $stateProvider.state('explore', {
        name: 'explore',
        url: '/itinenary/{itineraryId}/explore',
        templateUrl: 'views/explore.html',
        controller:"ExploreController",
        data: {
            isLoggedIn: false
        }
    });

    $urlRouterProvider.otherwise('/');
}]);
app.controller("navBarController",['$scope','$location','homeService',function ($scope,$location,homeService) {
    $scope.loginData = {};

    $scope.signOut = function () {
        $scope.$root.isLoggedIn = false;

        localStorage.setItem('isLoggedIn', false);
        sessionStorage.setItem("isLoggedIn",false);
        //  $scope.$apply();
        $location.path('login');
        //$location.refresh();//path('home');
        setTimeout(function(){
            $scope.$apply();
        },1);
    };
}]);

app.run(['$rootScope', '$location',  function ($rootScope, $location) {
    $rootScope.$on("$stateChangeStart", function (e, toState, toParams, fromState, fromParams) {
        var isLoggedIn = sessionStorage.getItem("isLoggedIn");
        var path = toState.name;
        if((isLoggedIn==null || isLoggedIn=="false")){
            $rootScope.isLoggedIn = false;
        }else{
            $rootScope.isLoggedIn = true;
        }
        if((isLoggedIn==null || isLoggedIn=="false") && path!="login" ){
            $location.path("login");
        }else if(isLoggedIn!=null && isLoggedIn=="true" && path=="login"){
            e.preventDefault();
            $location.path("home");
            // $location.reload();
            setTimeout(function(){
                $rootScope.$apply();
            },1);
        }
    });
}]);

directiveFunction.$inject = ['$rootScope'];

function directiveFunction($rootScope) {
    return {
        require: 'ngModel',
        scope: {
            ngModel: '=',
            details: '=?'
        },
        link: function(scope, element, attrs, model) {
            var options = {
                types: [],
                componentRestrictions: {}
            };
            scope.gPlace = new google.maps.places.Autocomplete(element[0], options);

            google.maps.event.addListener(scope.gPlace, 'place_changed', function() {
                scope.$apply(function() {
                    scope.details = scope.gPlace.getPlace();
                    model.$setViewValue(element.val());
                    $rootScope.$broadcast('place_changed', scope.details);
                });
            });
        }
    };
}


