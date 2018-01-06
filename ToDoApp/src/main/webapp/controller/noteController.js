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

	$scope.addNote = function(note) {
		console.log(note);
		var note = noteService.service('POST', 'note/createnote', note);
		note.then(function(response) {
			console.log(response.data);
		});
	};

	$scope.editNote = function(note) {
		console.log($scope.noteedit)
		var editnote = noteService.service('POST', 'note/updatenote', note);
		editnote.then(function(response) {
			console.log(response.data);
			getAllNotes();
		});
	};
	getAllNotes();

	$scope.openCustomModal = function(note) {

		$uibModal.open({
			resolve : {
				items : function() {
					return note;
				}
			},

			templateUrl : 'template/EditNote.html',
			parent : angular.element(document.body),
			/* controller:['$modalInstance','note',noteController], */
			controllerAs : 'controller',
			controller : dialogController,

		}).result.then(function() {
		}, function(res) {
		});

	}

	$scope.openTrashModal = function(note) {

		$uibModal.open({
			resolve : {
				items : function() {
					return note;
				}
			},

			templateUrl : 'template/trashnote.html',
			parent : angular.element(document.body),
			/* controller:['$modalInstance','note',noteController], */
			controllerAs : 'controller',
			controller : dialogController,

		});

	}
	
	$scope.openArchiveModal = function(note) {

		$uibModal.open({
			resolve : {
				items : function() {
					return note;
				}
			},

			templateUrl : 'template/archivemodal.html',
			parent : angular.element(document.body),
			/* controller:['$modalInstance','note',noteController], */
			controllerAs : 'controller',
			controller : dialogController,

		});

	}
	
	function dialogController($scope, items, noteService, $uibModalStack) {
		$scope.data = items;
		console.log("inside dialog controller", items);
		$scope.editNote = function(note) {
			console.log(items);
			var editnote = noteService
					.service('POST', 'note/updatenote', items);
			editnote.then(function(response) {
				console.log(response.data);
				getAllNotes();
			});
		};

		$scope.close = function() {
			$uibModalStack.dismissAll();
		}
		$scope.deleteNote=function(note){
			var deletednote=noteService.service('POST','note/deletenote',items);
			deletednote.then(function(response){
				console.log(response.data);
			})
		}
		
	
		
		$scope.archiveNote=function(note){
			var deletednote=noteService.service('POST','note/archivenote',items);
			deletednote.then(function(response){
				console.log(response.data);
			})
		}

		$scope.trashNote = function(note) {
			console.log(items);
			var trashednote = noteService.service('POST', 'note/trashnote',
					items);
			trashednote.then(function(response) {
				console.log(response.data);
			});
		};
	}
});
