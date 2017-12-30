var toDo = angular.module('ToDo');
toDo.controller('noteController', function($scope, noteService) {
	$scope.notes = [];
	
	var getAllNotes = function() {
		var note = noteService.service('POST', 'note/returnnotelist');
		
		note.then(function(response) {
			console.log(response.data);
			$scope.notes = response.data;
			
		});

	};
	
	getAllNotes();
});
