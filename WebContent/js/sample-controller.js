(function() {
    'use strict';

    angular.module('myApp')
        	.controller('sampleController', sampleController)
        	.controller('loginController', loginController)
        	.controller('stockInController', stockInController)
        	.controller('stockOutController', stockOutController)
        	.controller('accountController', accountController)
			.controller('remainingController',remainingController)
			.controller('soldController',soldController)
			.controller('logoutController',logoutController);
    
    function logoutController($scope, $location, $cookieStore){
    	$cookieStore.put("loggedInStatus", "false");
    	$location.path("/");
    }

    function sampleController($scope,$http) { 
    	console.log("Hello World");
    	$scope.message="Welcome";
    }
    
    
    function loginController($scope,$http,$location, $cookieStore){
       $cookieStore.put("loggedInStatus", "false");
       $scope.checkCredentials=function(){
       
        if (($scope.name == null) ||  ($scope.password == null)) {
           window.alert("Please fill the fields !!!!");
            } else if(($scope.name == "hangers" ) || ($scope.password == "Admin")){
              $scope.message="Welcome Admin!!!";
              $cookieStore.put("loggedInStatus", "true");
              $location.path("/stockIn");              
            }else{
              $scope.message="Wrong Credentials";
            }
       }
    }
    
     function stockInController($scope,$http) { 
    	
    	$scope.addStock=function(){
    	if (($scope.itemCode == null) ||  ($scope.itemType == null) ||($scope.brand == null) ||  ($scope.quantity == null) ||($scope.size == null) ||($scope.unitPrice == null) || ($scope.date== null)) {
           window.alert("Please fill the fields !!!!");
            } else{
            var payload = {
						"itemCode"   : $scope.itemCode,
						"itemType"	 : $scope.itemType,
						"brand"      : $scope.brand,
						"size"       : $scope.size,
						"quantity"   : $scope.quantity,
						"priceIn"    : $scope.unitPrice,
						"dateIn"     : $scope.date
				};
				 
				$http.post("https://hangers.herokuapp.com/service/rest/addStock", payload)
					.success(function(response){
						console.log(response);
						console.log(payload.dateIn);
						 $scope.message=response;
					})
					.error(function(response){
						console.log("Error : "+response);
						 $scope.message=response;
					});
             
            }
    	}
    }
    
     function stockOutController($scope,$http) { 
    	
    	$scope.sell=function(){
    
    	if (($scope.itemCode == null) || ($scope.quantity == null) ||($scope.sellingPrice == null) || ($scope.date== null)){
           window.alert("Please fill the fields !!!!");
            } else{
            var payload = {
						"itemCode"   : $scope.itemCode,
						"quantity"   : $scope.quantity,
						"priceOut"   : $scope.sellingPrice,
						"dateOut"    : $scope.date
				};
				$http.post("https://hangers.herokuapp.com/service/rest/sell", payload)
					.success(function(response){
						console.log(response);
						    $scope.message=response;
					})
					.error(function(response){
						console.log("Error : "+response);
						    $scope.message="Failed!!!";
					});
            
          
            }
    	}
    }
    
     function accountController($scope,$http) { 
    	
    	$scope.accounts=function(){
    	if (($scope.fromDate == null) || ($scope.toDate== null)) {
           window.alert("Please fill the fields !!!!");
            } else{
            var payload = {
						"dateIn"     : $scope.fromDate,
						"dateOut"    : $scope.toDate
				};
				$http.post("https://hangers.herokuapp.com/service/rest/accounts", payload)
					.success(function(response){
						console.log(response);
						$scope.message="Profit:"+response;
					})
					.error(function(response){
						console.log("Error : "+response);
						$scope.message="Profit:"+response;
					});
            
              
            }
    	}
		
    }
	
	function remainingController($scope,$http){
		$http.get("https://hangers.herokuapp.com/service/rest/getAll")
					.success(function(response){
						console.log(response);
						$scope.mainGridOptions = {
                dataSource: response,
                sortable: true,
                pageable: true,
           
                columns: [{
                    field: "itemCode",
                    title: "ITEM CODE",
                    width: "120px"
                    },
                    {
                    field: "itemType",
                    title: "TYPE",
                    width: "120px"
                    },
                    {
                    field: "brand",
                    title: "BRAND",
                    width: "120px"
                    },
                    {
                    field: "quantity",
                    title: "QUANTITY",
                    width: "120px"
                    },
                    {
                    field: "size",
                    title: "SIZE",
                    width: "120px"
                    },
                    {
                    field: "price",
                    title: "UNIT PRICE",
                    width: "120px"
                    },
                    {
                    field: "dateIn",
                    title: "STOCK DATE",
                    width: "120px"
                    }]
					
    };
					})
					.error(function(response){
						console.log("Error : "+response);
					});
					
		 
	}
   	function soldController($scope,$http){
		$http.get("https://hangers.herokuapp.com/service/rest/sold")
					.success(function(response){
						console.log(response);
						$scope.mainGridOptions = {
                dataSource: response,
                sortable: true,
                pageable: true,
           
                columns: [{
                    field: "itemCode",
                    title: "ITEM CODE",
                    width: "120px"
                    },
                    {
                    field: "quantity",
                    title: "QUANTITY",
                    width: "120px"
                    },
                    {
                    field: "price",
                    title: "UNIT PRICE",
                    width: "120px"
                    },
                    {
                    field: "dateOut",
                    title: "SOLD DATE",
                    width: "120px"
                    },
					{
					field: "transactionId",
                    title: "TRANSACTION ID",
                    width: "120px"	
					}]
					
    };
					})
					.error(function(response){
						console.log("Error : "+response);
					});
					
		 
	}
   
})();