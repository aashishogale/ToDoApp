var toDo = angular.module('ToDo');
toDo.controller('registerController', function($scope, registerService,
		$location) {
	$scope.registerUser = function() {
		console.log($scope.user);
		var a = registerService.registerUser($scope.user, $scope.error);
		a.then(function(response) {

			console.log(response.data);

			$location.path('/login');

		}, function(response) {
			$scope.error = response.data;
		});
	}
})