'use strict';

var surveyModule = angular.module("surveyApp");

surveyModule.controller("surveyController", function($scope, $rootScope, $http, $location) {
	
	$scope.doSubmitSurvey = function() {
		
		$('#loading_Overlay').show();
		$('#loading_img').show();
		
		var dataObj = {
				"issue" : $scope.issue,
				"servicerating" : $scope.servicerating,
				"servicetimetating" : $scope.servicetimetating,
				"feedback" : $scope.feedback,
				"optional" : $scope.optional,
				"username" : $scope.username
		};	
		
		$http({
			method : "POST",
			url : "/survey/survey/addSurvey",
			data : JSON.stringify(dataObj),
			headers : {'Content-Type' : 'application/json'}
		}).then(function(response) {
			$('#loading_Overlay').hide();
			$('#loading_img').hide();
			window.location = "#/thanks";
		});
		
		/*$.ajax({
			    url: '/survey/survey/addSurvey',
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
			});*/
		
	};
	
});