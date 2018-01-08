var toDo = angular.module('ToDo');
toDo.controller('noteController', function($scope, noteService, $uibModal,toaster,toastr,$interval,$filter) {
	$scope.noteList = [];

	var getAllNotes = function() {
		var listOfNote = noteService.service('POST', 'note/returnnotelist');

		listOfNote.then(function(response) {
			console.log(response.data);
			$scope.noteList = response.data;
		console.log("reminder"+$scope.noteList[1].reminder)

		});

	};

	$scope.addNote = function(note) {
		console.log(note);
		var note = noteService.service('POST', 'note/createnote', note);
		note.then(function(response) {
			console.log(response.data);
			toastr.success("success","note entered");
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
	
	
	interVal();
	function interVal() {
		
		$interval(function(){
			var i=0;
			for(i;i<$scope.noteList.length;i++){
				console.log("enter");
				console.log("reminder"+$scope.noteList[i].reminder)
				if($scope.noteList[i].reminder!=null){
					console.log("reminder"+$scope.noteList[i].reminder)
					var reminderdate=$filter('date')($scope.noteList[i].reminder,'yyyy-MM-dd HH:mm Z');
					var currentDate=$filter('date')(new Date(),'yyyy-MM-dd HH:mm Z');
					console.log("current date"+currentDate);
					console.log("reminderdate"+reminderdate);
					if(reminderdate ===currentDate){
						console.log("toaster exeute");
						toastr.success($scope.noteList[i].title, 'Reminder');
						$scope.noteList[i].reminder=null;
						editNote($scope.noteList[i]);
						
					}
				}
			}	
			
		},2200);
	};
	
	
	
	
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

	};

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

	};

	function dialogController($scope, items, noteService, $uibModalStack,
			$timeout,toaster) {
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
		};
		$scope.deleteNote = function(note) {
			var deletednote = noteService.service('POST', 'note/deletenote',
					items);
			deletednote.then(function(response) {
				console.log(response.data);
			});
		};

		var that = this;

		/*
		 * this.date = { value : new Date(), showFlag : false };
		 */

		this.openCalendar = function(e, date) {
			that.open[date] = true;
		};

		$scope.setReminder = function(note,$scope,toastr) {
			var reminderset = noteService.service('POST', 'note/setReminder',
					note);
			reminderset.then(function(response) {

				console.log(response.data);
				console.log(note.title);


			});
		};

		$scope.archiveNote = function(note) {
			var deletednote = noteService.service('POST', 'note/archivenote',
					items);
			deletednote.then(function(response) {
				console.log(response.data);

			});
		};

		$scope.trashNote = function(note) {
			console.log(items);
			var trashednote = noteService.service('POST', 'note/trashnote',
					items);
			trashednote.then(function(response) {

				console.log(response.data);
			});
		};

	};
});
	

