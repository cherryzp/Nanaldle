<?php 

	header('Content-Type:text/html; charset=utf-8');

	$date = $_POST['date'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT no FROM main_contents WHERE date='$date'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

	$no = $row[no];

	$sql = "SELECT comment FROM contents_comment WHERE content_no='$no'";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		mysqli_data_seek($result, $i);
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

		echo "{\"comment\":\"$row[comment]\"}";
		if($i!=$row_num-1){
			echo ",\n";
		}
	}

	echo "]";

	mysqli_close($conn);
 ?>