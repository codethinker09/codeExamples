'use strict';

var adminCtrl = angular.module("surveyApp");

adminCtrl.controller("adminController", function($scope, $rootScope, $http) {
	$scope.isSuperAdmin = false;
	$scope.pieDataSR;
	$scope.pieDataSTR;
	$scope.myChartObjectSR = {};
	$scope.myChartObjectSTR = {};
	$scope.myChartObjectSR.type = "PieChart";
	$scope.myChartObjectSTR.type = "PieChart";
	$scope.selectedItemSr = "";
	$scope.selectedItemStr = "";
	
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
	
		$scope.totalServerItems = 0;
		$scope.pagingOptions = {
	          pageSizes: [10, 50, 100],
	          pageSize: "10",
	          currentPage: 1
	      };	      
	  	
		$scope.$watch('pagingOptions', function (newVal, oldVal) {
	          if ((newVal !== oldVal) && (newVal.currentPage !== oldVal.currentPage) || (newVal.pageSize !== oldVal.pageSize)) {
	            	$('#loading_Overlay').show();
					$('#loading_img').show();
					
					var dataObj = {
							"fromDate" : $scope.fromDate,
							"toDate" : $scope.toDate,
							"servicerating" : $scope.selectedItemSr,
							"servicetimetating": $scope.selectedItemStr,
							"page" : $scope.pagingOptions.currentPage,
							"pageSize" : $scope.pagingOptions.pageSize
					};	
				
					$http({
						method : "POST",
						url : "/survey/survey/searchSurveyPieClick",
						data : JSON.stringify(dataObj),
						headers : {'Content-Type' : 'application/json'}
					}).then(function(response) {
						$scope.myData = response.data.searchSurveyResponseList;	
						$scope.totalServerItems = $scope.totalServerItems;				
						$('#loading_Overlay').hide();
						$('#loading_img').hide();		
					}).catch(function (data) {
						$('#loading_Overlay').hide();
						$('#loading_img').hide();
					});
	          }
	      }, true);
		  
		// search
		
		$scope.searchSurvey = function(){
		
			$('#loading_Overlay').show();
			$('#loading_img').show();
			
			var dataObj = {
					"fromDate" : $scope.fromDate,
					"toDate" : $scope.toDate,
					"servicerating" : "",
					"servicetimetating": "",
					"page" : $scope.pagingOptions.currentPage,
					"pageSize" : $scope.pagingOptions.pageSize
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
				$scope.totalServerItems = response.data.count;
				
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
        'title': 'Service Rating',
		'colors': ['#fe041f', '#fe8904', '#b6fe04', '#1ba189']
    };
	
	$scope.myChartObjectSTR.options = {
        'title': 'Service Time Rating',
		'colors': ['#fe041f', '#fe8904', '#b6fe04', '#1ba189']
    };
	
	$scope.srSelected = function (selectedItem) {
		if(selectedItem != undefined){
			$scope.selectedItemStr = "";
			$('#loading_Overlay').show();
			$('#loading_img').show();
			
			$scope.selectedItemSr = selectedItem.row + 1;
			
			var dataObj = {
					"fromDate" : $scope.fromDate,
					"toDate" : $scope.toDate,
					"servicerating" : selectedItem.row + 1,
					"servicetimetating": "",
					"page" : 1,
					"pageSize" : $scope.pagingOptions.pageSize
			};	
		
			$http({
				method : "POST",
				url : "/survey/survey/searchSurveyPieClick",
				data : JSON.stringify(dataObj),
				headers : {'Content-Type' : 'application/json'}
			}).then(function(response) {
				$scope.myData = response.data.searchSurveyResponseList;	
				$scope.totalServerItems = response.data.count;				
				$('#loading_Overlay').hide();
				$('#loading_img').hide();		
			}).catch(function (data) {
				$('#loading_Overlay').hide();
				$('#loading_img').hide();
			});
		}
	}
	
	$scope.strSelected = function (selectedItem) {
		if(selectedItem != undefined){
			$scope.selectedItemSr = "";
			$('#loading_Overlay').show();
			$('#loading_img').show();
			
			$scope.selectedItemStr = selectedItem.row + 1;
			
			
			var dataObj = {
					"fromDate" : $scope.fromDate,
					"toDate" : $scope.toDate,
					"servicerating" : "",
					"servicetimetating": selectedItem.row + 1,
					"page" : 1,
					"pageSize" : $scope.pagingOptions.pageSize
			};	
	
			$http({
				method : "POST",
				url : "/survey/survey/searchSurveyPieClick",
				data : JSON.stringify(dataObj),
				headers : {'Content-Type' : 'application/json'}
			}).then(function(response) {		
				$scope.myData = response.data.searchSurveyResponseList;
				$scope.totalServerItems = response.data.count;
				$('#loading_Overlay').hide();
				$('#loading_img').hide();		
			}).catch(function (data) {
				$('#loading_Overlay').hide();
				$('#loading_img').hide();
			});
		}
	}

// grid options	
		  
		$scope.gridOptions = { 
		data: 'myData',
		enablePaging: true,
		totalServerItems:'totalServerItems',
		pagingOptions: $scope.pagingOptions,
		showFooter: true,
		columnDefs: [
				{field: 'user', displayName: 'Person Name',width: '140px'},
				{field:'issueType', displayName:'Service Name',width: '120px'},
				{field:'servicerating', displayName:'Service Rating',width: '170px'},
				{field:'servicetimetating', displayName:'Service Time Rating',width: '200px'},
				{field:'optional', displayName:'Optional issue link',width: '210px'},
				{field:'feedback', displayName:'Feedback',width: '100px'},
				{field:'createdDate', displayName:'Date',width: '200px'}
			]
		};
		
});