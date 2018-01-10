var toDo = angular.module('ToDo');
toDo.directive('sidebar',function(){
	return{
		templateUrl:'template/sidebar.html'
	}
})

toDo.directive('navbar',function(){
	return{
		templateUrl:'template/navbar.html'
	}
})



toDo.directive('clicknote', function($timeout) {
  return {
    restrict: 'AE',
    link: function(scope, elem, attrs) {
   
      elem.on('click', function() {
        // This $timeout trick is necessary to run
        // the Angular digest cycle
        $timeout(function() {
         elem.css("max-height","450px");
       
        });
      });
    }
  };
});


toDo.directive('sidebarcollapse', function($timeout) {
	  return {
	    restrict: 'AE',
	    link: function(scope, elem, attrs) {
	   
	      elem.on('click', function() {
	      
	        $timeout(function() {
	        	var myEl = angular.element( document.querySelector( '#sidebar' ) );
	       myEl.toggleClass('active');
	        });
	      });
	    }
	  };
	});



toDo.directive('changeview', function($timeout) {
	  return {
	    restrict: 'AE',
	    link: function(scope, elem, attrs) {
	   
	      elem.on('click', function() {
	     
	        $timeout(function() {
	      
	       var newEl=angular.element( document.querySelector( '#note' ) );
	       newEl.toggleClass('col-lg-9');
	     
	        });
	      });
	    }
	  };
	});