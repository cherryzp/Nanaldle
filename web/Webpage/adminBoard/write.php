<?php 

	header('Content-Type:text/html; charser=utf-8');

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
	mysqli_query($conn, "set names utf8");

	if(isset($_GET['bno'])){
		$bNo= $_GET['bno'];
	}
	
	if(isset($bNo)){
		$sql = "SELECT b_title, b_content, b_id from admin_board where b_no ='$bNo'";
		$result = mysqli_query($conn, $sql);
		$row = $result->fetch_assoc();
	}

?>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
	<link rel="stylesheet" href="normalize.css" />
	<link rel="stylesheet" href="board.css" />
</head>
<body>
	<article class="bodyArticle">
		<h3>자유게시판 글쓰기</h3>
		<div id="boardWrite">
			<form action="write_update.php" method="post">
				<?php 
				if(isset($bNo)){
					echo '<input type="hidden" name="bno" value="'.$bNo.'">';
				}
				 ?>
				<table id="boardWriteTable">
					<caption class="readHide">자유게시판 글쓰기</caption>
					<tbody>
						<tr>
							<th scope="row"><label for="bID">아이디</label></th>
							<td class="id">
								<?php 
									if(isset($bNo)){
										echo $row['b_id'];
									} else { ?>
										<input type="text" name="bID" id="bIDForm"></td>
								<?php
									}
							 	?>
						</tr>
						<tr>
							<th scope="row"><label for="bPassword">비밀번호</label></th>
							<td class="password"><input type="password" name="bPassword" id="bPasswordForm"></td>
						</tr>
						<tr>
							<th scope="row"><label for="bTitle">제목</label></th>
							<td class="title"><input type="text" name="bTitle" id="bTitleForm" value="<?php echo isset($row['b_title'])?$row['b_title']:null?>"></td>
						</tr>
						<tr>
							<th scope="row"><label for="bContent">내용</label></th>
							<td class="content"><textarea name="bContent" id="bContentForm" ><?php echo isset($row['b_content'])?$row['b_content']:null?></textarea></td>
						</tr>
					</tbody>
				</table>
				<div class="btnSet">
					<button type="submit" class="btnSubmit"><?php echo isset($bNo)?'수정':'작성'?></button>
					<a href="admin_board.php" class="btnList">목록</a>
				</div>
			</form>			
		</div>
		
	</article>
</body>
</html>