<?php 

	header('Content-Type:text/html; charset=utf-8');

	$conn= mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
	mysqli_query($conn, "set names utf8");

	if(isset($_POST['bno'])){
		$bNo= $_POST['bno'];
	}

	$bPassword= $_POST['bPassword'];

	if(isset($bNo)){
		$sql= "SELECT count(b_password) AS cnt FROM admin_board WHERE b_password=password('$bPassword') AND b_no='$bNo'";
		$result= mysqli_query($conn, $sql);
		$row= $result->fetch_assoc();

		if($row['cnt']){
			$sql = "DELETE FROM admin_board WHERE b_no='$bNo'";
		} else {
			$msg = "비밀번호가 맞지 않습니다.";
?>
		<script type="text/javascript">
			alert('<?php echo $msg ?>');
			history.back();
		</script>
<?php
		exit; 
		}
	}	
	$result = mysqli_query($conn, $sql);

	if($result){
		$msg= "정상적으로 글이 삭제되었습니다.";
		$replaceURL="admin_board.php";
	} else {
		$msg= "글을 삭제하지 못했습니다.";
?>
		<script type="text/javascript">
			alert("<?php echo $msg ?>");
			history.back();
		</script>
<?php 
		exit;
	}
?>
	<script type="text/javascript">
		alert("<?php echo $msg ?>");
		location.replace("<?php echo $replaceURL ?>");
	</script>