var ToDo = angular.module('ToDo', [ 'ui.router', 'ui.bootstrap' ,'ngSanitize','ui.bootstrap.datetimepicker','toaster','toastr'])

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
			
			.state('trash', {
				url : '/trash',
				templateUrl : 'template/Trash.html',
				controller:'noteController'
            
			})
			
				.state('archive', {
				url : '/archive',
				templateUrl : 'template/archive.html',
				controller:'noteController'
            
			})
			
				.state('reminder', {
				url : '/reminder',
				templateUrl : 'template/Reminder.html',
				controller:'noteController'
            
			})
			
			
			
			$urlRouterProvider.otherwise('login');

		} ]);