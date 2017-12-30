var ToDo = angular.module('ToDo', [ 'ui.router' ])

ToDo.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'template/register.html',
				controller:'registerController'

			})

			.state('login', {
				url : '/login',
				templateUrl : 'template/Login.html',
                controller:'loginController'
			})
			
			.state('home', {
				url : '/home',
				templateUrl : 'template/home.html',
				controller:'noteController'
            
			})
			.state('dummy', {
				url : '/dummy',
				templateUrl : 'template/dummy.html',
				controller:'dummyController'
            
			})
			
			
			
			$urlRouterProvider.otherwise('login');

		} ]);