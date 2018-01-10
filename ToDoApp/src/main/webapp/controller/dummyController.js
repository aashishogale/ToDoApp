var toDo = angular.module('ToDo');
toDo.controller('dummyController', function($scope, loginService,$location){
	var socialLogin = function() {
		var login = loginService.service('POST', 'user/retrieveToken');
		
		login.then(function(response) {
			console.log(response.data);
			sessionStorage.setItem("token",response.data.token);
			$location.path('/home');
			
		});

	}
	socialLogin();
});
