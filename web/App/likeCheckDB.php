<?php 

	header('Content-Type:text/html; charset=utf-8');

	$dateTime = $_POST['date'];
	$email = $_POST['email'];

	$dateTime = (string)$dateTime;
	$email = (string)$email;

	$conn = mysqli_connect('localhost', 'win9101', 'cpflwmq9094', 'win9101');
	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$dateTime'";
	$result = mysqli_query($conn, $sql);

	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
	$contents_no = $row[no];

	$like_date = date("Y/m/d H:i:s");

	$sql = "INSERT INTO contents_like(content_no, email, like_date) VALUES ('$contents_no', '$email', '$like_date')";
	$result = mysqli_query($conn, $sql);

	$sql = "SELECT COUNT(m.no=l.content_no) like_count FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no WHERE m.date='$dateTime'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

	echo "{\"like_count\":\"$row[like_count]\"}";

	mysqli_close($conn);

 ?>