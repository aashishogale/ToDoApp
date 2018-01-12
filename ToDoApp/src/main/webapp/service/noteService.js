var toDo=angular.module('ToDo');
toDo.factory('noteService',function($http,$location){
	var details = {};
	details.service=function(method,url,note,email,collabemail){
	console.log("seriice entered");
	
	return $http({
		method : method,
		url : url,
		headers: {
		'token':sessionStorage.getItem('token'),
		'email':email,
		'collabemail':collabemail
		},
	    data:note
		
	})
	}
	return details;
});