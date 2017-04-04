var app = angular.module("myApp", ["ngRoute","ngCookies", "ngResource", "kendo.directives"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "login.html",
        controller  : "loginController"
        
    })
    .when("/stockIn", {
        templateUrl : "pages/stockIn.html",
        controller  : "stockInController"
        
    })
    .when("/stockOut", {
        templateUrl : "pages/stockOut.html",
        controller  : "stockOutController"
        
    })
     .when("/accounts", {
        templateUrl : "pages/accounts.html",
        controller  : "accountController"
        
    }) 
    .when("/remaining", {
        templateUrl : "pages/remaining.html",
        controller  : "remainingController"
        
    }) 
    .when("/soldItems", {
        templateUrl : "pages/soldItems.html",
        controller  : "soldController"
        
    }) 
    .when("/logout",{
    	controller : "logoutController"
    })
     .otherwise({
          redirectTo : '/'
    });
    
    /////////////copied scrap code adding starts here
    
    /*run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/');
            }else{
            	var isAuthorized =true;
            	//$cookieStore.put('isAuthorized',isAuthorized);
            	//var path= $cookieStore.get('path');
            	//$location.path(path);
            	
            }
            
      
        //alert('location change event ***** ')	
        });
    }*/
    
    //copied scrap code ends here
    
    
    
        
});

//Actual code starts here
app.run(runMe);

runMe.$inject = ['$rootScope', '$location', '$cookieStore'];
function runMe($rootScope, $location, $cookieStore) {
	$cookieStore.put("loggedInStatus", "false");
	
	$rootScope.$on("$locationChangeStart", function(event, next, current){
		var futurePath = next.split("#");
		if($cookieStore.get("loggedInStatus")=="false"){
			$location.path("/");
		}else{
			var path = $cookieStore.get("path");
			$location.path(futurePath[1]);
		}
	});
	
}

//Actual code ends here

