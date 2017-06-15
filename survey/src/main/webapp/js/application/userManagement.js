'use strict';

var adminCtrl = angular.module("surveyApp");

adminCtrl.controller("userManagementController", function($scope, $rootScope, $http, $location) {
	
	$rootScope.isSuperAdmin = true;
	$rootScope.username = "";
	$scope.authMsg = "";
	
	$scope.loadAdminUsers = function(){

    	$('#loading_Overlay').show();
		$('#loading_img').show();
		
		var dataObj = {
				"page" : $scope.pagingOptions1.currentPage,
				"pageSize" : $scope.pagingOptions1.pageSize,
				"userName": $scope.userText,
				"roleId":1
		};		
	
		$http({
			method : "POST",
			url : baseUrl + "loadAdminUsers",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			$scope.myUserData = response.data.userPojoList;
			$scope.totalServerItems1 = response.data.count;		
			$('#loading_Overlay').hide();
			$('#loading_img').hide();		
		}).catch(function (data) {
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
		});
  
	}	
		$scope.totalServerItems1 = 0;
		$scope.pagingOptions1 = {
	          pageSizes: [10, 50, 100],
	          pageSize: "10",
	          currentPage: 1
	    };
		
	$http({
		method : "POST",
		url : baseUrl + "validateSuperAdmin",
		headers : {'Content-Type' : 'application/json'}
	}).then(function(response) {
		var JSONObject = response.data;
		
		
		for (var i=0; i < JSONObject.length; i++){
			var arrayItem = JSONObject[i];
			
			var key = arrayItem.key;
		   		    
		    if(key == "username"){
		    	$rootScope.username = arrayItem.value;
		    }else{
		    	if(key == "message"){
			    	var itemVal = arrayItem.value;
			    	if(itemVal == "Success"){
			    		$rootScope.isSuperAdmin = true;
			    	}else{
			    		$rootScope.isSuperAdmin = false;
						$scope.authMsg = "Not Authorized to view this page";
			    	}
			    }
		    }
		}
			
	});
	
	if($rootScope.isSuperAdmin){
		$scope.loadAdminUsers();
	}

	$scope.isActive = function (viewLocation) {		
        return viewLocation === $location.path();
    };
	
	// super admin
	$scope.addAdmin = function(){
		
		$('#loading_Overlay').show();
		$('#loading_img').show();
		
		var dataObj = {
				"userName" : $scope.superAdmin,
				"roleId":1
		};
		
		$http({
			method : "POST",
			url : baseUrl + "saveUser",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			
			var JSONObject = response.data;
			
			var messageVal = JSONObject["value"];
			
			if(messageVal == "Success"){
				$scope.message = "User "+$scope.superAdmin + " Added Successfully";
				$scope.loadAdminUsers();
				
			}else if(messageVal == "alreadyPresent"){
				$scope.message = "User "+$scope.superAdmin + " is already there";
				
			}else{
				$scope.message = "Error adding in User "+$scope.superAdmin;
			}
			
		});
	};	
	
	$scope.deactivateUsers = function(){
		
		$('#loading_Overlay').show();
		$('#loading_img').show();
		
		$http({
			method : "POST",
			url : baseUrl + "deleteAdminUser",
			data : $scope.selectedRows,
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			$scope.loadAdminUsers();
			$scope.gridOptions1.selectedItems.length = 0;
			$('#loading_Overlay').hide();
			$('#loading_img').hide();			
		});
	};
		  	
			
		$scope.$watch('pagingOptions1', function (newVal, oldVal) {
	          if ((newVal !== oldVal) && (newVal.currentPage !== oldVal.currentPage) || (newVal.pageSize !== oldVal.pageSize)) {
	        	  $scope.loadAdminUsers();
	          }
	      }, true);		
	

// grid options	
		  
		$scope.selectedRows=[];
		
		$scope.gridOptions1 = { 
		data: 'myUserData',
		enablePaging: true,
		totalServerItems:'totalServerItems1',
		pagingOptions: $scope.pagingOptions1,
		showFooter: true,
		multiSelect: true,
		enableHighlighting: true,
		enableRowSelection: true,
		selectedItems:$scope.selectedRows,
		showFilter : true,
	    enableColumnResize : true,
	    showSelectionCheckbox: true,
		columnDefs: [
				{field: 'userName', displayName: 'User Name',width: '305px'},
				{field:'roleName', displayName:'Role',width: '160px'}
			]
		};
		
});