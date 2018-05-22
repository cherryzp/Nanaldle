<?php 

	header('Content-Type:text/html; charset=utf-8');

	$date = (string)$_POST['date'];
	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$date'";
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";	
	}

	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
	$contents_no = $row[no];

	$bookmark_date = date("Y/m/d H:i:s");

	$sql = "INSERT INTO contents_bookmark(content_no, email, bookmark_date) VALUES ('$contents_no', '$email', '$bookmark_date')";
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";	
	}
	

	mysqli_close($conn);

 ?>

 <!-- SELECT m.no, COUNT(b.email = 'win9101@nate.com') bookmark_check FROM main_contents m LEFT OUTER JOIN contents_bookmark b ON m.no=b.content_no GROUP BY m.no -->
