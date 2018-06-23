<?php 

	header('Content-Type:text/html; charset=utf-8');

	$conn= mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
	mysqli_query($conn, "set names utf8");

	if(isset($_POST['bno'])){
		$bNo= $_POST['bno'];
	}

	if(empty($bNo)){
		$bID = $_POST['bID'];
		$date = date('Y-m-d H:i:s');	
	}

	$bPassword = $_POST['bPassword'];		
	$bTitle = $_POST['bTitle'];
	$bContent = $_POST['bContent'];

	if(isset($bNo)){
		$sql = "SELECT count(b_password) AS cnt FROM admin_board WHERE b_password=password('$bPassword') AND b_no='$bNo'";
		$result = mysqli_query($conn, $sql);
		$row = $result->fetch_assoc();

		if($row['cnt']){
			$sql= "UPDATE admin_board SET b_title='$bTitle', b_content='$bContent' WHERE b_no='$bNo'";
			$msgState= '수정';
		} else {
			$msg = '비밀번호가 맞지 않습니다.';
		?>
			<script type="text/javascript">
				alert("<?php echo $msg ?>");
				history.back();
			</script>
		<?php
			exit;
		} 
		
	} else {
		$sql = "INSERT INTO admin_board(b_title, b_content, b_date, b_hit, b_id, b_password) VALUES ('$bTitle', '$bContent','$date', 0,'$bID', password('$bPassword'))";
		$msgState = '등록';
	}

	if(empty($msg)){
		$result= mysqli_query($conn, $sql);

		if($result){
			$msg = "정상적으로 글이 '$msgState' 되었습니다.";
			if(empty($bNo)){
				$bNo= $conn->insert_id;	
			}
			$replaceURL= 'view.php?bno='.$bNo;
		} else {
			$msg = "글을 등록하지 못했습니다.";
?>
			<script type="text/javascript">
				alert("<?php echo $msg ?>");
				history.back();
			</script>
<?php 
			exit;
		}
	}
?>
<script type="text/javascript">
	alert("<?php echo $msg ?>");
	location.replace("<?php echo $replaceURL ?>");
</script>
