var toDo = angular.module('ToDo');
toDo.controller('noteController', function($scope, noteService) {
	$scope.noteList = [];
	
	var getAllNotes = function() {
		var listOfNote = noteService.service('POST', 'note/returnnotelist');
		
		listOfNote.then(function(response) {
			console.log(response.data);
			$scope.noteList = response.data;
			
		});

	};
	
	 $scope.addNote=function(){
		var note=noteService.service('POST','note/createnote',$scope.note);
		note.then(function(response){
			console.log(response.data);
		});
	};
	
	$scope.editNote=function(note){
		console.log($scope.noteedit)
		var editnote=noteService.service('POST','note/updatenote',note);
		editnote.then(function(response){
			console.log(response.data);
		});
	};
	getAllNotes();
});
