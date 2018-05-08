<?php 

	header('Content-Type:text/html; charset=utf-8');

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT DISTINCT m.content, m.img, t.tag_item, m.emoticon, date FROM main_contents AS m LEFT OUTER JOIN hashtag AS t ON m.no=t.content_no ORDER BY m.no DESC";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	$sql2 = "SELECT m.no, COUNT(m.no=l.content_no) like_count FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no GROUP BY m.no ORDER BY m.no DESC";
	$result2 = mysqli_query($conn, $sql2);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		mysqli_data_seek($result, $i);
		mysqli_data_seek($result2, $i);

		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
		$row2 = mysqli_fetch_array($result2, MYSQLI_ASSOC);

		echo "{\"content\":\"$row[content]\", \"img\":\"$row[img]\", \"tag\":\"$row[tag_item]\", \"emoticon\":\"$row[emoticon]\", \"date\":\"$row[date]\",";
		echo " \"like_count\":\"$row2[like_count]\"}";
		if($i!=$row_num-1){
			echo",\n";
		}
	}

	echo "]";

	mysqli_close($conn);

 ?>