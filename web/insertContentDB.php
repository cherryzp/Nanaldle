<?php 

	header('Content-Type:text/html; charset=utf-8');

	$content = $_POST['content'];
	$tag = $_POST['tag'];
	$emoticon = $_POST['emoticon'];

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

	$dateTime = date("Y/m/d h:i:s");

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");

	mysqli_query($conn, "set names utf8");


	if($srcImgName){
		$sql = "INSERT INTO main_contents(content, img, tag, emoticon, date) VALUES('$content','$dstName','$tag', '$emoticon', '$dateTime')";	
	}else{
		$sql = "INSERT INTO main_contents(content, tag, emoticon, date) VALUES('$content', '$tag', '$emoticon', '$dateTime')";
	}
	
	$result = mysqli_query($conn, $sql);

	if($result){
		echo "insert success";
	} else {
		echo "insert fail";
	}

	mysqli_close($conn);

 ?>