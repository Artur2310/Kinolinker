<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">

<link href="<c:url value="/resources/css/styles_admin.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/animate.css" />" rel="stylesheet">


<title>Вход</title>
</head>
<body>
<div id="wrap">

<div id="container">
		<form>
			<label for="name" value=''>Логин:</label>
			<input type="name"> 
			<label for="username" value=''>Пароль:</label>
			<p><a href="#">Забыли пароль?</a></p>
			<input type="password">
			<div id="lower">
				<input type="checkbox"><label class="check" for="checkbox">Запомнить меня</label>
				<input type="submit" value="Войти">
			</div>
		</form>
	</div>

	


</div>
</body>
</html>