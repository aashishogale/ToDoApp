
function checkvalid() {

	if (document.getElementById("fname").value == "") {

		//alert("Cannot leave firstname this field blank");
	/*	document.getElementById("fname").height="30%"*/
		document.getElementById("pfname").innerHTML="cannot leave this blank";
		return false;
	}
	 var regex = /^[a-zA-Z ]{2,30}$/;
	    var ctrl =  document.getElementById("fname");

	    if (regex.test(ctrl.value)!=true) {
	    	document.getElementById("pfname").innerHTML="pls enter correct name";
	        return false;
	    }


    
     regex=/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/
     ctrl=document.getElementById("email");
	if (document.getElementById("email").value == "") {
		document.getElementById("pemail").innerHTML="pls enter correct value";
		//alert("Cannot leave email field blank");
		return false;
	}
	 if (regex.test(ctrl.value)!=true) {
	    	document.getElementById("pemail").innerHTML="pls enter correct email";
	        return false;
	    }

	if (document.getElementById("psw").value == "") {
		//alert("Cannot leave password field blank");
		document.getElementById("ppass").innerHTML="pls enter password";
		return false;
	}
	if (document.getElementById("psw").value.length < 4
			|| document.getElementById("psw").value.length > 20) {
		document.getElementById("ppsw").innerHTML="pls enter password with correct length";
		//alert("pls enter correct number of character for password");
		return false;
	}



	if (document.getElementById("mobile").value.length < 10) {
		document.getElementById("pnumber").innerHTML="please enter correct 9 digit number";
		return false;
	}

	return true;
}


function checklogin(){
   var regex=/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/
      var  ctrl=document.getElementById("email");
   	if (document.getElementById("email").value == "") {
   		document.getElementById("pemail").innerHTML="email cannot be blank";
   		//alert("Cannot leave email field blank");
   		return false;
   	}
   	 if (regex.test(ctrl.value)!=true) {
   	    	document.getElementById("pemail").innerHTML="pls enter correct email";
   	        return false;
   	    }
   	 
 	if (document.getElementById("psw").value == "") {
		//alert("Cannot leave password field blank");
		document.getElementById("ppass").innerHTML="pls enter password";
		return false;
	}
	if (document.getElementById("psw").value.length < 4
			|| document.getElementById("psw").value.length > 20) {
		document.getElementById("ppsw").innerHTML="pls enter password with correct length";
		//alert("pls enter correct number of character for password");
		return false;
	}
	
}