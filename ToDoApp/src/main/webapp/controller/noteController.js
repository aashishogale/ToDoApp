var toDo = angular.module('ToDo');
toDo
		.controller('noteController',
				function($scope, noteService, $uibModal, toaster, toastr,
						$interval, $filter, $state, $location, $uibModalStack,
						$timeout) {

				
					
					 

					$scope.noteList = [];
					$scope.userList = [];
					

					var getAllNotes = function() {
						var listOfNote = noteService.service('POST',
								'note/returnnotelist');

						listOfNote.then(function(response) {
							console.log(response.data);
							$scope.noteList = response.data;
						
							console.log("reminder"
									+ $scope.noteList[1].reminder)

						});

					};
					
					var getUser=function(){
						var userget=noteService.service('POST','user/getUser');
						userget.then(function(response){
							$scope.user=response.data;
							console.log($scope.user);
						})
					}
					
					$scope.logout=function(){
						sessionStorage.removeItem('Token');
						$location.path('#!/login');
					}
					$scope.getAllCollaborators = function(note) {
						var getListUsers = noteService.service('POST',
								'note/getcollaborator', note);
						getListUsers.then(function(response) {
							console.log(response.data);
							$scope.userList = response.data;
						})
					}
				
					$scope.addCollaborators = function(note, email) {
						var addCollaborators = noteService.service('POST',
								'note/setcollaborator', note, email);
						addCollaborators.then(function(response) {
							console.log(response.data);
						})
					}
					$scope.addNote = function(note) {
						console.log(note);
						var note = noteService.service('POST',
								'note/createnote', note);
						note.then(function(response) {
							console.log(response.data);
							toastr.success("success", "note entered");
							$state.reload();
						});
					};
					$scope.getuser = function() {
						var userdetails = noteService.service('POST',
								'note/getuserbyid');
						userdetails.then(function(response) {

							$scope.user = response.data;
							console.log(user);

						});

					};
					$scope.editNote = function(note) {
						console.log($scope.noteedit)
						var editnote = noteService.service('POST',
								'note/updatenote', note);
						editnote.then(function(response) {
							console.log(response.data);
							getAllNotes();
						});
					};
					getAllNotes();
				
					interVal();
					function interVal() {

						$interval(function() {
							var i = 0;
							for (i; i < $scope.noteList.length; i++) {
								console.log("enter");
								console.log("reminder"
										+ $scope.noteList[i].reminder)
								if ($scope.noteList[i].reminder != null) {
									console.log("reminder"
											+ $scope.noteList[i].reminder)
									var reminderdate = $filter('date')(
											$scope.noteList[i].reminder,
											'yyyy-MM-dd HH:mm Z');
									var currentDate = $filter('date')(
											new Date(), 'yyyy-MM-dd HH:mm Z');
									console.log("current date" + currentDate);
									console.log("reminderdate" + reminderdate);
									if (reminderdate === currentDate) {
										console.log("toaster exeute");
										toastr.success(
												$scope.noteList[i].title,
												'Reminder');
										$scope.noteList[i].reminder = null;
										editNote($scope.noteList[i]);

									}
								}
							}

						}, 22000);
					}
					;

					$scope.checked = "col-lg-3";
					$scope.changeview = function() {
						if ($scope.checked == "col-lg-3") {
							console.log($scope.checked);
							$scope.checked = "col-lg-9"
							console.log($scope.checked);
						} else {
							$scope.checked = "col-lg-3";
						}
					}

					$scope.openCustomModal = function(note) {
						$scope.note = note
						$uibModal.open({
							scope : $scope,

							templateUrl : 'template/EditNote.html',
							parent : angular.element(document.body)

						}).result.then(function() {
						}, function(res) {
						});

					}
					
					$scope.openCollabModal = function(note) {
						$scope.note = note
						$uibModal.open({
							scope : $scope,

							templateUrl : 'template/Collab.html',
							parent : angular.element(document.body)

						}).result.then(function() {
						}, function(res) {
						});

					}

					$scope.openTrashModal = function(note) {

						$scope.note = note
						$uibModal.open({
							scope : $scope,

							templateUrl : 'template/trashnote.html',
							parent : angular.element(document.body)

						}).result.then(function() {
						}, function(res) {
						});

					};

					$scope.openArchiveModal = function(note) {

						$scope.note = note
						$uibModal.open({
							scope : $scope,

							templateUrl : 'template/archivemodal.html',
							parent : angular.element(document.body)

						}).result.then(function() {
						}, function(res) {
						});

					};

					$scope.close = function() {
						$uibModalStack.dismissAll();
					};

					$scope.deleteNote = function(note) {
						var deletednote = noteService.service('POST',
								'note/deletenote', note);
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

					$scope.setReminder = function(note, $scope, toastr) {
						var reminderset = noteService.service('POST',
								'note/setReminder', note);
						reminderset.then(function(response) {

							console.log(response.data);
							console.log(note.title);

						});
					};

					$scope.archiveNote = function(note) {
						var deletednote = noteService.service('POST',
								'note/archivenote', note);
						deletednote.then(function(response) {
							console.log(response.data);

						});
					};

					$scope.trashNote = function(note) {
						console.log(note);
						var trashednote = noteService.service('POST',
								'note/trashnote', note);
						trashednote.then(function(response) {

							console.log(response.data);
						});
					};

				});
