<?php 

    header('Content-Type:text/html; charset=utf-8');

    $conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
    mysqli_query($conn, "set names utf8");

    if(isset($_GET['page'])){
        $page= $_GET['page'];
    } else {
        $page= 1;
    }

    $sql = "SELECT COUNT(*) AS cnt FROM admin_board ORDER BY b_no DESC";
    $result = mysqli_query($conn, $sql);
    $row= $result->fetch_assoc();

    $allPost= $row['cnt'];

    $onePage= 15;
    $allPage= ceil($allPost/ $onePage);

    if($page<1 || ($allPage && $page > $allPage)){
?>
        <script type="text/javascript">
            alert("존재하지 않는 페이지입니다.");
            history.back();
        </script>
<?php 
        exit;
    }

    $oneSection= 10;
    $currentSection= ceil($page/$oneSection);
    $allSection= ceil($allPage/$oneSection);

    $firstPage= ($currentSection*$oneSection)-($oneSection-1);

    if($currentSection==$allSection){
        $lastPage= $allPage;
    } else {
        $lastPage= $currentSection*$oneSection;
    }

    $prevPage= (($currentSection-1)*$oneSection);
    $nextPage= (($currentSection+1)*$oneSection)-($oneSection-1);

    $paging = '<ul>';

    if($page!=1){
        $paging .= '<li class="page_start"><a href="admin_board.php?page=1">처음</a></li>';
    }
    if($currentSection != 1){
        $paging .= '<li class="page_prev"><a href="admin_board.php?page='.$prevPage.'">이전</a></li>';
    }

    for($i=$firstPage; $i<=$lastPage; $i++){
        if($i==$page){
            $paging .= '<li class="page_current">'.$i.'</li>';
        } else {
            $paging .= '<li class="page"><a href="admin_board.php?page='.$i.'">'.$i.'</a></li>';
        }
    }

    if($currentSection != $allSection){
        $paging .= '<li class="page_next"><a href="admin_board.php?page='.$nextPage.'">다음</a></li>';
    }

    if($page != $allPage){
        $paging .= '<li class="page_end"><a href="admin_board.php?page='.$allPage.'">끝</a></li>';
    }
    $paging .= '</ul>';

    $currentLimit= ($onePage * $page)- $onePage;
    $sqlLimit= 'limit '.$currentLimit.', '.$onePage;

    $sql = 'SELECT * FROM admin_board ORDER BY b_no DESC '.$sqlLimit;
    $result= mysqli_query($conn, $sql);
?>
<!DOCTYPE <!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>관리자 게시판</title>
    <link rel="stylesheet" href="normalize.css" />
	<link rel="stylesheet" href="board.css" />
</head>
<body>
    <article class="boardArticle">
    	<h3>관리자 게시판</h3>
    	<table>
    		<caption class="readHide">관리자 게시판</caption>
    		<thead>
    			<tr>
    				<th scope="col" class="no">번호</th>
    				<th scope="col" class="title">제목</th>
    				<th scope="col" class="author">작성자</th>
    				<th scope="col" class="date">작성일</th>
    				<th scope="col" class="hit">조회</th>
    			</tr>
    		</thead>
    		<tbody>
    			<?php
                    while($row= $result->fetch_assoc()){
                        $datetime= explode(' ', $row['b_date']);
                        $date = $datetime[0];
                        $time = $datetime[1];
                        if($date == Date('Y-m-d')){
                            $row['b_date']= $time;
                        } else {
                            $row['b_date'] = $date;
                        }
				?>

    				<tr>
    					<td class="no"><?php echo $row[b_no] ?></td>
    					<td class="title"><a href="view.php?bno=<?php echo $row['b_no'] ?>"><?php echo $row[b_title] ?></a></td>
    					<td class="author"><?php echo $row[b_id] ?></td>
    					<td class="date"><?php echo $row[b_date] ?></td>
    					<td class="hit"><?php echo $row[b_hit] ?></td>
    				</tr>

				<?php
    				}
    			?>
    		</tbody>
    	</table>
        <div class="btnSet">
            <a href="write.php" class="btnWrite">글쓰기</a>
        </div>
        <div class="paging">
            <?php echo $paging ?>
        </div>
    </article>
</body>
</html>
