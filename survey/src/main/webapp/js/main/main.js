'use strict';

(function() {

	var app = angular.module("surveyApp",
			[ 'ngCookies', 'restangular', 'ui.bootstrap', 'ngRoute','ngGrid']).config(
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
				controller:'thanksCtrl'
					  }).
			when('/admin', {
				templateUrl:'htm/application/admin.htm?v=1.0',
				controller:'adminCtrl'
								  }).		  
			otherwise( {
			  redirectTo : '/survey',
			  });
		});

		
		
}());