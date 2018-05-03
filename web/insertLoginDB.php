<?php 

	header('Content-Type:text/html; charset=stf-8');

	$id = $_POST['id'];
	$name = $_POST['name'];
	$email = $_POST['email'];

	echo "$id\n";
	echo "$name\n";
	echo "$email\n";

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	if (mysqli_connect_errno($conn)){
	    echo "DB 연결 실패:" . mysqli_connect_error();
	}else{
	    echo "DB 연결 성공 <br>" ;  
	}

	mysqli_query($conn, "set name utf8");

	$sql = "INSERT INTO user_login_infomation(id, name, email) VALUES('$id','$name','$email')";
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";
	}else{
		echo "insert fail";
	}

	mysqli_close($conn);
	
 ?>