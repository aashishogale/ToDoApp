var toDo=angular.module('ToDo');
toDo.factory('noteService',function($http,$location){
	var details = {};
	details.service=function(method,url,note){
	
	
	return $http({
		method : method,
		url : url,
		headers: {
		'token':sessionStorage.getItem('token')
		},
	    data:note
		
	})
	}
	return details;
});