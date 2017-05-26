'use strict';

(function() {

	var app = angular.module("surveyApp",
			[ 'ngCookies', 'restangular', 'ui.bootstrap', 'ngRoute','ngGrid', 'ui.bootstrap.datetimepicker']).config(
			function(RestangularProvider) {
				RestangularProvider.setBaseUrl('/survey');
				RestangularProvider.setErrorInterceptor(function(response,
						deferred, responseHandler) {
					 console.log("Error occurred - with response status = "+response.status);
					 return true;
				});
			});
	

	app.config(function($routeProvider){
		$routeProvider.
			when('/', {
			  templateUrl:'htm/application/survey.htm?v=1.0',
			  controller:'surveyController'
			  }).
			when('/thanks', {
				templateUrl:'htm/application/thanks.htm?v=1.0',
				controller:'thanksController'
					  }).
			when('/admin', {
				templateUrl:'htm/application/admin.htm?v=1.0',
				controller:'adminController'
								  }).		  
			otherwise( {
			  redirectTo : '/survey',
			  });
		});

		
		
}());