<?php 

	header('Content-Type:text/html; charset=utf-8');

	$date = (string)$_POST['date'];
	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$date'";
	$result = mysqli_query($conn, $sql);

	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
	$contents_no = $row[no];

	$sql = "DELETE FROM contents_bookmark WHERE content_no = '$row[no]'";
	$result = mysqli_query($conn, $sql);

	mysqli_close($conn);

 ?>