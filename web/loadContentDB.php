<?php 

	header('Content-Type:text/html; charset=utf-8');

	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT DISTINCT m.content, m.img, t.tag_item, m.emoticon, date FROM main_contents AS m LEFT OUTER JOIN hashtag AS t ON m.no=t.content_no ORDER BY m.no DESC";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

	$sql2 = "SELECT m.no, COUNT(m.no=l.content_no) like_count FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no GROUP BY m.no ORDER BY m.no DESC";
	$result2 = mysqli_query($conn, $sql2);

	$sql3 = "SELECT m.no, COUNT( m.no=l.content_no ) is_liked FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no WHERE m.email = '$email' GROUP BY m.no ORDER BY m.no DESC";
	$result3 = mysqli_query($conn, $sql3);

	$sql4 = "SELECT COUNT(m.no=c.content_no) comment_count FROM main_contents m LEFT OUTER JOIN contents_comment c ON m.no=c.content_no GROUP BY m.no ORDER BY m.no DESC";
	$result4 = mysqli_query($conn, $sql4);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		mysqli_data_seek($result, $i);
		mysqli_data_seek($result2, $i);
		mysqli_data_seek($result3, $i);

		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
		$row2 = mysqli_fetch_array($result2, MYSQLI_ASSOC);
		$row3 = mysqli_fetch_array($result3, MYSQLI_ASSOC);
		$row4 = mysqli_fetch_array($result4, MYSQLI_ASSOC);

		echo "{\"content\":\"$row[content]\", \"img\":\"$row[img]\", \"tag\":\"$row[tag_item]\", \"emoticon\":\"$row[emoticon]\", \"date\":\"$row[date]\",";
		echo " \"isliked\":\"$row3[is_liked]\",";
		echo " \"comment_count\":\"$row4[comment_count]\",";
		echo " \"like_count\":\"$row2[like_count]\"}";

		if($i!=$row_num-1){
			echo",\n";
		}
	}

	echo "]";

	mysqli_close($conn);

 ?>