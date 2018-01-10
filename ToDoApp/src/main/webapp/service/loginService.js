var toDo = angular.module('ToDo');

toDo.factory('loginService', function($http, $location) {

	var details = {};

	details.service = function(method, url, user) {
		return $http({
			method : method,
			url : url,
			data : user
		})
	}

	details.social = function(method, url) {
		return$http({
			method : method,
			url : url
		})
	}
	return details;
});