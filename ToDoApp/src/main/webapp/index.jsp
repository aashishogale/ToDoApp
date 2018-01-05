<!DOCTYPE html>
<html>
<head>
<script src="bower_components/angular/angular.js"></script>
<script
	src="bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<!-- <script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>  -->

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
	crossorigin="anonymous">
<link type="text/css" rel="stylesheet"
	href="bower_components/bootstrap-social/bootstrap-social.css" />
<link type="text/css" rel="stylesheet" href="css/register.css" />
<link type="text/css" rel="stylesheet" href="css/login.css" />
<link type="text/css" rel="stylesheet" href="css/home.css" />
<script src="bower_components/jquery/dist/jquery.min.js"></script>


<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script type="text/javascript" src="script/ToDo.js"></script>
<script type="text/javascript" src="script/validation.js"></script>
<script type="text/javascript" src="controller/registerController.js"></script>
<script type="text/javascript" src="controller/loginController.js"></script>
<script type="text/javascript" src="service/registerService.js"></script>
<script type="text/javascript" src="service/loginService.js"></script>
<script type="text/javascript" src="controller/noteController.js"></script>
<script type="text/javascript" src="service/noteService.js"></script>
<script type="text/javascript" src="controller/dummyController.js"></script>
<script type="text/javascript" src="service/dummyService.js"></script>
<script type="text/javascript" src="directives/contenteditable.js"></script>
<script type="text/javascript" src="directives/customdirectives.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap-tpls.min.js"></script>
</head>
<body ng-app="ToDo">
	<div ui-view></div>
</body>
</html>
