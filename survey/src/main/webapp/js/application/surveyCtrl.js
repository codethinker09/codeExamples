'use strict';

var surveyModule = angular.module("surveyApp");

surveyModule.controller("surveyController", function($scope, $rootScope, $http) {
	alert(99);
	$scope.doSubmitSurvey = function() {
		
		console.log($scope.surveyPojo);
		
		$('#loading_Overlay').show();
		$('#loading_img').show();
		
		alert(1);
		
		var dataObj = {
				"issue" : $scope.surveyPojo.issue,
				"servicerating" : scope.surveyPojo.servicerating,
				"servicetimetating" : scope.surveyPojo.servicetimetating,
				"feedback" : scope.surveyPojo.feedback,
				"optional" : scope.surveyPojo.optional,
				"username" : scope.surveyPojo.username
		};	
		
		/*$http({
			method : "POST",
			url : "../../../survey/addSurvey",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response,data) {
			console.log(response);
			console.log(data);
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
		});*/
		
		$.ajax({
			    url: '../../../survey/addSurvey',
			    type: 'POST',
			    dataType: 'json',
			    data: JSON.stringify(dataObj),
			    contentType: "application/json; charset=utf-8",
			    success: function(response) {
			    	 $('#loading_Overlay').hide();
			         $('#loading_img').hide();
					 console.log(response);
			        
			    },
			    error: function(){
			    		$('#loading_Overlay').hide();
			    		$('#loading_img').hide();
			      }
			});
		
	};
	
});