'use strict';

var adminCtrl = angular.module("surveyApp");

adminCtrl.controller("adminController", function($scope, $rootScope, $http) {
	$scope.isSuperAdmin = true;
	
	$scope.fromDate = new Date();
	$scope.toDate = new Date();
	
	  $scope.dpOpenStatus = {};
      
      $scope.setDpOpenStatus = function(id) {
        $scope.dpOpenStatus[id] = true
      };
	  
      // super admin
	$scope.validateSuperAdmin = function(){
		
		$('#loading_Overlay').show();
		$('#loading_img').show();
		
		$http({
			method : "POST",
			url : "/survey/survey/validateSuperAdmin",
			data : $scope.superAdmin,
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			console.log(response);
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			
			var JSONObject = JSON.parse(response);
			var messageVal = JSONObject["message"];
			
			if(messageVal == "Success"){
				// show here
				$scope.isSuperAdmin = true;
			}else{
				// error message
				$scope.isSuperAdmin = false;
			}
			
		});
	}	
		// search
		
		$scope.searchSurvey = function(){
		
			$('#loading_Overlay').show();
			$('#loading_img').show();
			
		var dataObj = {
				"fromDate" : $scope.fromDate,
				"toDate" : $scope.toDate
		};	
		
		$http({
			method : "POST",
			url : "/survey/survey/searchSurvey",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			console.log(response);
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			
			$scope.myData = response.data.searchSurveyResponseList;			
			
		});
	}
	
// grid options
		
		$scope.gridOptions = { 
		data: 'myData',
		columnDefs: [
				{field: 'user', displayName: 'User'},
				{field:'issueType', displayName:'Issue Type'},
				{field:'servicerating', displayName:'Service Rating'},
				{field:'servicetimetating', displayName:'Service Time Rating'},
				{field:'feedback', displayName:'Feedback'},
				{field:'optional', displayName:'Optional'}
			]
		};
		
});