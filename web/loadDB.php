<?php 
	
	header('Content-Type:text/html; charset=utf-8');

	$email = $_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT id,name,email FROM user_login_infomation WHERE email='$email'";
	$result = mysqli_query($conn, $sql);

	$row_count = mysqli_num_rows($result);

	for($i=0; $i<$row_count; $i++){
		mysqli_data_seek($result, $i);

		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
		
		echo "$row[email]";
	}

	mysqli_close($conn);

 ?>