<?php 

	header('Content-Type:text/html; charset=utf-8');

	$tag = $_POST['tag'];
	$tag = (string)"%".$tag."%";

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT DISTINCT h.tag_item FROM hashtag h WHERE h.tag_item LIKE '$tag'";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

		echo "{\"tag_item\":\"$row[tag_item]\"}";
		if($i!=$row_num-1){
			echo ",\n";
		}
	}

	echo "]";

 ?>