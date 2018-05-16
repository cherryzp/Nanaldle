<?php 

	header('Content-Type:text/html; charset=utf-8');

	$email = (string)$_POST['email'];
	
	$conn = mysqli_connect('localhost', 'win9101', 'cpflwmq9094', 'win9101');
	mysqli_query($conn, "set names utf8");

	$sql = "SELECT * FROM main_contents WHERE email = '$email' ORDER BY no DESC";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
		echo "{\"content\":\"$row[content]\", \"date\":\"$row[date]\"}";
		if($i!=$row_num-1){
			echo ",\n";
		}
	}

	echo "]";

	mysqli_close($conn);

 ?>