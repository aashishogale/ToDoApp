var toDo = angular.module('ToDo');
toDo.controller('noteController', function($scope, noteService, $uibModal) {
	$scope.noteList = [];

	var getAllNotes = function() {
		var listOfNote = noteService.service('POST', 'note/returnnotelist');

		listOfNote.then(function(response) {
			console.log(response.data);
			$scope.noteList = response.data;

		});

	};

	$scope.addNote = function() {
		var note = noteService.service('POST', 'note/createnote', $scope.note);
		note.then(function(response) {
			console.log(response.data);
		});
	};

	$scope.editNote = function(note) {
		console.log($scope.noteedit)
		var editnote = noteService.service('POST', 'note/updatenote', note);
		editnote.then(function(response) {
			console.log(response.data);
		});
	};
	getAllNotes();

	$scope.openCustomModal = function(note) {
		

		$uibModal.open({
			resolve: {
			    items: function () {
			      return note;
			    }
			  },
			  
			templateUrl : 'template/EditNote.html',
			 parent: angular.element(document.body),
			/*controller:['$modalInstance','note',noteController],*/
			controllerAs: 'controller',
			controller : dialogController,
		
		});
		
		
	}
	function dialogController($scope,items,noteService,$uibModalStack){
		$scope.data=items;
		console.log("inside dialog controller",items);
		$scope.editNote = function(note) {
			console.log(items);
			var editnote = noteService.service('POST', 'note/updatenote', items);
			editnote.then(function(response) {
				console.log(response.data);
			});
		};
	
	$scope.close=function(){
		$uibModalStack.dismissAll();
		}
	
	$scope.trashNote = function(note) {
		console.log(items);
		var trashednote = noteService.service('POST', 'note/trashnote', items);
		trashednote.then(function(response) {
			console.log(response.data);
		});
	};
	}
});


$("#collapsebutton").click(function(e) {
    e.preventDefault();
    console.log("toggle entered");
    $("#sidebar").toggleClass("toggled");
    alert(1);
});
