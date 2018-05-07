<?php 

	header('Content-Type:text/html; charset=utf-8');

	$content = $_POST['content'];
	$tag = $_POST['tag'];
	$emoticon = $_POST['emoticon'];
	$email = $_POST['email'];

	$srcImgName = $_FILES['img']['name'];
	$tmpImgName = $_FILES['img']['tmp_name'];

	$arr = explode(".", $srcImgName);
	$len = count($arr);
	$fileExtension = ".".$arr[$len-1];

	$dstName = "uploads/IMG_".date(Ymd_his).$fileExtension;

	if ( move_uploaded_file($tmpImgName, $dstName)){
		echo "upload success\n";
	} else {
		echo "upload fail\n";
	}
	
	echo "$content\n";
	echo "$msg\n";
	echo "$dstName\n";
	echo "$email\n";

	$dateTime = date("Y/m/d h:i:s");

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");


	if($srcImgName){
		$sql = "INSERT INTO main_contents(content, img, tag, emoticon, date, email) VALUES('$content','$dstName','$tag', '$emoticon', '$dateTime', '$email')";	
	}else{
		$sql = "INSERT INTO main_contents(content, img, tag, emoticon, date, email) VALUES('$content', 'null', '$tag', '$emoticon', '$dateTime', '$email')";
	}
	
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";
	} else {
		echo "insert fail";
	}

	$email = (string)$email;
	$dateTime = (string)$dateTime;

	$sql = "SELECT no FROM main_contents WHERE date = '$dateTime' AND email = '$email'";
	
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";
	} else {
		echo "insert fail";
	}

	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

	$contents_no = "$row[no]";
	echo "\n$content_no\n";

	$sql = "INSERT INTO hashtag ( content_no, tag_item ) VALUES ('$row[no]', '$tag')";

	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";
	} else {
		echo "insert fail";
	}

	mysqli_close($conn);

 ?>