var toDo = angular.module('ToDo');
toDo.directive('sidebar', function() {
	return {
		templateUrl : 'template/sidebar.html'
	}
})

toDo.directive('navbar', function() {
	return {
		templateUrl : 'template/navbar.html'
	}
})

toDo.directive('clicknote', function($timeout) {
	return {
		restrict : 'AE',
		link : function(scope, elem, attrs) {

			elem.on('click', function() {
				// This $timeout trick is necessary to run
				// the Angular digest cycle
				$timeout(function() {
					elem.css("max-height", "450px");

				});
			});
		}
	};
});

toDo.directive('sidebarcollapse', function($timeout) {
	return {
		restrict : 'AE',
		link : function(scope, elem, attrs) {

			elem.on('click', function() {

				$timeout(function() {
					var myEl = angular.element(document
							.querySelector('#sidebar'));
					myEl.toggleClass('active');
				});
			});
		}
	};
});

toDo.directive('changeview', function($timeout) {
	return {
		restrict : 'AE',
		link : function(scope, elem, attrs) {

			elem.on('click', function() {

				$timeout(function() {

					var newEl = angular
							.element(document.querySelector('#note'));
					newEl.toggleClass('col-lg-9');

				});
			});
		}
	};
});

toDo
		.directive(
				'editbutton',
				function() {
					return {
						template : ' <input type="submit"'
								+ 'value="edit" ng-click="editNote(note);close();" class="noteenter">'
							
					}
				})

toDo.directive('trashbutton', function() {

	return {
		template : ' <i ng-click="trashNote(note);close();"'
				+ 'class="notetrash glyphicon glyphicon-trash">' + '</i>'
	}
})

toDo.directive('archivebutton', function() {

	return {
		template : '<img src="img/archive.svg" ng-click="archiveNote(note);close();"'
				+ '>' + '</i>'
	}
})
