<?php 

	header('Content-Type:text/html; charset=utf-8');

	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT m.content, c.comment, c.comment_date , m.date FROM main_contents AS m JOIN contents_comment AS c ON m.no=c.content_no WHERE c.email='$email' ORDER BY c.no DESC";
	$result = mysqli_query($conn, $sql);
	$row_num = mysqli_num_rows($result);

	echo "[";

	for($i=0; $i<$row_num; $i++){
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
		echo "{\"content\":\"$row[content]\", \"comment\":\"$row[comment]\", \"date\":\"$row[comment_date]\", \"m_date\":\"$row[date]\"}";

		if($i!=$row_num-1){
			echo ",\n";
	 	}
	}

	echo "]";

	mysqli_close($conn);

 ?>