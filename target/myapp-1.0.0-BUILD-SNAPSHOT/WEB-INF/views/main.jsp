<html>
<head>
	<title> MAIN </title>
</head>

<body>
	<p>Your name ${user.name}</p>
	<p>Your email ${user.email}</p>
	<script type="text/javascript">
		if (${user.isAdmin}){
			document.write('User is admin');
		}
		else{
			document.write('User is not admin');
		}
	</script>
</body>
</html>