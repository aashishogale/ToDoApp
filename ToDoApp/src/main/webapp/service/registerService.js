
var toDo = angular.module('ToDo');

toDo.factory('registerService', function($http, $location) {

	var details = {};
	
	details.registerUser = function(user) {
		return $http({
			method : "POST",
			url : "user/register",
			data : user
		})
	}
	return details;

});