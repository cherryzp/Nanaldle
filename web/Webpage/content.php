<?php 

	header('Content-Type:text/html; charset=utf-8');

	$conn = mysqli_connect("localhost", "win9101", "cpflwmq9094", "win9101");
	mysqli_query($conn, "set names utf8");

	$sql = "SELECT * FROM main_contents ORDER BY no DESC";
	$result = mysqli_query($conn, $sql);

	$row_num = mysqli_num_rows($result);

?>
<!DOCTYPE html>
<html>
<head>
	<title></title>

	<link rel="stylesheet" type="text/css" href="content.css">
</head>
<body>
<div>
<table>
	<tr>
		<th>no</th>
		<th>content</th>
		<th>image</th>
		<th>emoticon</th>
		<th>date</th>
		<th>email</th>
	</tr>
<?php
	for($i=0; $i<$row_num; $i++){
		$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
?>
	<tr>
		<td><?php echo $row[no]?></td>
		<td><?php echo $row[content]?></td>
		<td><?php echo $row[img]?></td>
		<td><?php echo $row[emoticon]?></td>
		<td><?php echo $row[date]?></td>
		<td><?php echo $row[email]?></td>
	</tr>
<?php	
	}
?>
</table>
</div>
</body>
</html>
