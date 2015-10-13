<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="ProcessBank" id="form">
		<table width="100%" border="0" cellpadding="3" cellspacing="3">
			<tr>
				<td colspan="2" align="center">
					<h2>Nạp tiền qua bank</h2>
				</td>
			</tr>

			<tr>

				<td align="right">Nhập số tiền :</td>
				<td><input type="text" id="amount" name="amount" placeholder="lớn hơn 10.000" /></td>
			</tr>
			<tr>
				<td align="right">Nhập order info :</td>
				<td><input type="text" id="order_info" name="order_info" placeholder="thông tin order" /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input type="submit" value="Nạp bank" /></td>
			</tr>
		</table>
	</form>
</body>
</html>