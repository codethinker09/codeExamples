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
			
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			
			var JSONObject = response.data;
			var messageVal = JSONObject["value"];
			
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
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			
			$scope.myData = response.data.searchSurveyResponseList;			
			
		});
	}
	
	// download report
	
	$scope.reportDownload = function(){
		
			$('#loading_Overlay').show();
			$('#loading_img').show();
			
		var dataObj = {
				"fromDate" : $scope.fromDate,
				"toDate" : $scope.toDate
		};	
		
		$http({
			method : "POST",
			url : "/survey/survey/downloadCSV",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			var anchor = angular.element('<a/>');
			angular.element(document.body).append(anchor);
			 anchor.attr({
				 href: 'data:attachment/csv;charset=utf-8,' + encodeURI(response.data),
				 target: '_blank',
				 download: 'Service_rating.csv'
			 })[0].click();
			 anchor.remove();
		});
	}
	
	
// grid options
		
		$scope.gridOptions = { 
		data: 'myData',
		columnDefs: [
				{field: 'user', displayName: 'Person Name'},
				{field:'issueType', displayName:'Service Name'},
				{field:'servicerating', displayName:'Service Rating'},
				{field:'servicetimetating', displayName:'Service Time Rating'},
				{field:'optional', displayName:'Optional issue link'},
				{field:'feedback', displayName:'Feedback'},
				{field:'createdDate', displayName:'Date'}
			]
		};
		
});