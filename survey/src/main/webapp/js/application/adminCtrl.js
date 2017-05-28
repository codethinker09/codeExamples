'use strict';

var adminCtrl = angular.module("surveyApp");

adminCtrl.controller("adminController", function($scope, $rootScope, $http) {
	$scope.isSuperAdmin = true;
	$scope.pieDataSR;
	$scope.pieDataSTR;
	$scope.myChartObjectSR = {};
	$scope.myChartObjectSTR = {};
	$scope.myChartObjectSR.type = "PieChart";
	$scope.myChartObjectSTR.type = "PieChart";
	
	$scope.myChartObjectSR.data = {"cols": [
			{id: "t", label: "Rating", type: "string"},
			{id: "s", label: "count", type: "number"}
		], "rows": [
			{c: [
				{v: "Worse"},
				{v: 0},
			]},
			{c: [
				{v: "Bad"},
				{v: 0}
			]},
			{c: [
				{v: "Good"},
				{v: 0},
			]},
			{c: [
				{v: "VGood"},
				{v: 0},
			]}
		]};
		
		$scope.myChartObjectSTR.data = {"cols": [
			{id: "t", label: "Rating", type: "string"},
			{id: "s", label: "count", type: "number"}
		], "rows": [
			{c: [
				{v: "Worse"},
				{v: 0},
			]},
			{c: [
				{v: "Bad"},
				{v: 0}
			]},
			{c: [
				{v: "Good"},
				{v: 0},
			]},
			{c: [
				{v: "VGood"},
				{v: 0},
			]}
		]};
	
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
			$scope.pieDataSR = response.data.serviceRating.c;
			$scope.pieDataSTR = response.data.serviceTimeRating.c;
			
			if($scope.pieDataSR != null){
				$scope.myChartObjectSR.data = {"cols": [
					{id: "t", label: "Rating", type: "string"},
					{id: "s", label: "count", type: "number"}
				], "rows": [
				{c: [
					{v: $scope.pieDataSR[0].v},
					{v: $scope.pieDataSR[1].v},
				]},
				{c: [
					{v: $scope.pieDataSR[2].v},
					{v: $scope.pieDataSR[3].v}
				]},
				{c: [
					{v: $scope.pieDataSR[4].v},
					{v: $scope.pieDataSR[5].v},
				]},
				{c: [
					{v: $scope.pieDataSR[6].v},
					{v: $scope.pieDataSR[7].v},
				]}
		]};
}

			if($scope.pieDataSTR != null){
				$scope.myChartObjectSTR.data = {"cols": [
					{id: "t", label: "Rating", type: "string"},
					{id: "s", label: "count", type: "number"}
				], "rows": [
				{c: [
					{v: $scope.pieDataSTR[0].v},
					{v: $scope.pieDataSTR[1].v},
				]},
				{c: [
					{v: $scope.pieDataSTR[2].v},
					{v: $scope.pieDataSTR[3].v}
				]},
				{c: [
					{v: $scope.pieDataSTR[4].v},
					{v: $scope.pieDataSTR[5].v},
				]},
				{c: [
					{v: $scope.pieDataSTR[6].v},
					{v: $scope.pieDataSTR[7].v},
				]}
		]};
}


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

    $scope.myChartObjectSR.options = {
        'title': 'Service Rating'
    };
	
	$scope.myChartObjectSTR.options = {
        'title': 'Service Time Rating'
    };
	
	$scope.srSelected = function (selectedItem) {
	  alert(selectedItem.row);
	}
	
	$scope.strSelected = function (selectedItem) {
	  alert(selectedItem.row);
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