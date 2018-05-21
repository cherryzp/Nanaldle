<?php 

	header('Content-Type:text/html; charset=utf-8');

	$date = (string)$_POST['date'];
	$email = (string)$_POST['email'];

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");

	$sql = "SELECT m.content, m.img, m.emoticon, COUNT(m.no=l.content_no) like_count FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no WHERE m.date='$date' GROUP BY m.no";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

	$sql2 = "SELECT t.tag_item FROM main_contents AS m LEFT OUTER JOIN hashtag AS t ON m.no=t.content_no WHERE m.date= '$date'";
	$result2 = mysqli_query($conn, $sql2);
	$row2 = mysqli_fetch_array($result2, MYSQLI_ASSOC);

	$sql3 = "SELECT COUNT( m.no=l.content_no ) is_liked FROM main_contents m LEFT OUTER JOIN contents_like l ON m.no=l.content_no WHERE l.email = '$email' AND m.date='$date'";
	$result3 = mysqli_query($conn, $sql3);
	$row3 = mysqli_fetch_array($result3, MYSQLI_ASSOC);

	$sql4 = "SELECT COUNT(m.no=c.content_no) comment_count FROM main_contents m LEFT OUTER JOIN contents_comment c ON m.no=c.content_no WHERE m.date='$date'";
	$result4 = mysqli_query($conn, $sql4);
	$row4 = mysqli_fetch_array($result4, MYSQLI_ASSOC);

	echo "{\"content\":\"$row[content]\", \"img\":\"$row[img]\", \"emoticon\":\"$row[emoticon]\", \"date\":\"$date\", \"like_count\":\"$row[like_count]\", \"tag\":\"$row2[tag_item]\", \"is_liked\":\"$row3[is_liked]\", \"comment_count\":\"$row4[comment_count]\"}";

	mysqli_close($conn);

 ?>