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
				templateUrl : 'template/home.html'
            
			})
			$urlRouterProvider.otherwise('register');

		} ]);