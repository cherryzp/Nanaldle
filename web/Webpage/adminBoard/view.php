<?php 
	header('Content-Type:text/html; charset=utf-8');

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
	mysqli_query($conn, "set names utf8");

	$bNo= $_GET['bno'];

	if(!empty($bNo) && empty($_COOKIE['admin_board_'.$bNo])){
		$sql = "UPDATE admin_board SET b_hit = b_hit + 1 WHERE b_no='$bNo'";
		$result= mysqli_query($conn, $sql);
		if(empty($result)){
?>
			<script type="text/javascript">
				alert('오류가 발생했습니다.');
				history.back();
			</script>
<?php 
		} else {
			setcookie('admin_board_'.$bNo, TRUE, time()+(60*60*24), '/');
		}
	}

	$sql= "SELECT b_title, b_content, b_date, b_hit, b_id FROM admin_board WHERE b_no='$bNo'";
	$result= mysqli_query($conn, $sql);
	$row = $result->fetch_assoc();
 ?>
 <!DOCTYPE html>
 <html>
 <head>
 	<meta charset="utf-8">
 	<title></title>
 	<link rel="stylesheet" href="normalize.css" />
	<link rel="stylesheet" href="board.css" />
 </head>
 <body>
 	<article class="boardArticle">
 		<h3>자유게시판 글쓰기</h3>
 		<div id="boardView">
 			<h3 id="boardTitle"><?php echo $row['b_id'] ?></h3>
 			<div id="boardInfo">
 				<span id="boardID">작성자: <?php echo $row['b_id'] ?></span>
 				<span id="boardDate">작성일: <?php echo $row['b_date'] ?></span>
 				<span id="boardHit">조회: <?php echo $row['b_content'] ?></span>
 			</div>
 			<div id="boardContent"><?php echo $row['b_content'] ?></div>
 			<div class="btnSet">
				<a href="write.php?bno=<?php echo $bNo ?>">수정</a>
				<a href="delete.php?bno=<?php echo $bNo ?>">삭제</a>
				<a href="admin_board.php">목록</a>
			</div>
 		</div>
 	</article>
 </body>
 </html>