var toDo=angular.module('ToDo');
toDo.factory('noteService',function($http,$location){
	var details = {};
	details.service=function(method,url,note,email){
	console.log("seriice entered");
	
	return $http({
		method : method,
		url : url,
		headers: {
		'token':sessionStorage.getItem('token'),
		'email':email
		},
	    data:note
		
	})
	}
	return details;
});