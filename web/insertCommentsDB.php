<?php 

	header('Content-Type:text/html; charset=utf-8');

	$comment = (string)$_POST['comments'];
	$date = (string)$_POST['date'];
	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	echo "$comments"."$date"."$email";

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$date'";
	$result = mysqli_query($conn, $sql);
	if($result) {
		echo "insert success";
	}else{
		echo "insert fail";
	}
	
	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

	$comment_date = date("Y/m/d H:i:s");

	echo "$row[no]";

	$sql = "INSERT INTO contents_comment(content_no, comment, email, comment_date) VALUES ('$row[no]', '$comment', '$email', '$comment_date')";
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";	
	} else {
		echo "insert fail";
	}

	mysqli_close($conn);

 ?>