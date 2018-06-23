<?php 

	header('Content-Type:text/html; charset=utf-8');

	$tag = (string)$_POST['tag'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql1 = "SELECT m.content, m.date FROM main_contents m LEFT OUTER JOIN hashtag t ON m.no=t.content_no WHERE t.tag_item='$tag' GROUP BY t.content_no ORDER BY t.content_no DESC";
	$result1 = mysqli_query($conn, $sql1);

	$row_num = mysqli_num_rows($result1);

	$sql2 = "SELECT COUNT(t.content_no=l.content_no) like_count FROM hashtag t LEFT OUTER JOIN contents_like l ON t.content_no=l.content_no WHERE t.tag_item = '$tag' GROUP BY t.content_no ORDER BY t.content_no DESC";
	$result2 = mysqli_query($conn, $sql2);

	$sql3 = "SELECT COUNT(t.content_no=c.content_no) comment_count FROM hashtag t LEFT OUTER JOIN contents_comment c ON t.content_no=c.content_no WHERE t.tag_item = '$tag' GROUP BY t.content_no ORDER BY t.content_no DESC";
	$result3 = mysqli_query($conn, $sql3);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		$row1 = mysqli_fetch_array($result1, MYSQLI_ASSOC);
		$row2 = mysqli_fetch_array($result2, MYSQLI_ASSOC);
		$row3 = mysqli_fetch_array($result3, MYSQLI_ASSOC);

		echo "{\"content\":\"$row1[content]\", \"date\":\"$row1[date]\", \"like_count\":\"$row2[like_count]\", \"comment_count\":\"$row3[comment_count]\"}";

		if($i!=$row_num-1){
			echo ",\n";
		}
	}

	echo "]";

	mysqli_close($conn);
 ?>