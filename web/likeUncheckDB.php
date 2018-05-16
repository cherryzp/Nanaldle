<?php 

	header('Content-Type:text/html; charset=utf-8');

	$dateTime = $_POST['date'];

	$dateTime = (string)$dateTime;

	$conn = mysqli_connect('localhost', 'win9101', 'cpflwmq9094', 'win9101');
	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$dateTime'";
	$result = mysqli_query($conn, $sql);

	echo "$dateTime";

	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
	$contents_no = $row[no];
	echo "$contents_no";

	$sql = "DELETE FROM contents_like WHERE content_no = '$row[no]'";
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "delete";
	} else {
		echo "fail";
	}

	mysqli_close($conn);

 ?>