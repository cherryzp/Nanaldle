<?php 

	header('Content-Type:text/html; charset=utf-8');

	$tag = $_POST['tag'];
	$tag = (string)"%".$tag."%";

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT tag_item, COUNT(tag_item) tag_count FROM hashtag WHERE tag_item LIKE '$tag' GROUP BY tag_item ORDER BY tag_count DESC";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

		echo "{\"tag\":\"$row[tag_item]\", \"tag_count\":\"$row[tag_count]\"}";
		if($i!=$row_num-1){
			echo ",\n";
		}
	}

	echo "]";

 ?>