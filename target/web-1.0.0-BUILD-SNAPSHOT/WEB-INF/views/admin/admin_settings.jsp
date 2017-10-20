<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Настройки</title>

<link href="<c:url value="/resources/css/admin_page.css" />"
	rel="stylesheet">

<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.remodal.css"/>">



<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="<c:url value="/resources/js/admin/adm_setting.js" />"></script>




</head>
<body>


	<header>
		<div class="home">
			<a href="${contextPath}/">На сайт</a>
		</div>

		<div class="logout">
			<a href="${contextPath}/logout">Выход</a>
		</div>
	</header>
	<div class="block">
		<div id="navigation">
			<ul>
				<li><a href="${pageContext.request.contextPath}/admin/movies">Фильмы</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/persons">Персоны</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/settings">Настройки</a></li>


			</ul>
		</div>
	</div>

	<div id="content">

		<ul>
			<li>
				<div id="changePassword">
					<a class="btn btn-primary" href="#modal">Изменить пароль</a>
				</div>
			</li>
		</ul>


	</div>

	<div class="remodal-bg">
		<br> <br>

		<div class="remodal" data-remodal-id="modal">
			<form method="POST" action="javascript:void(null);"
				onsubmit="changePassword()" class="form-signin" id="formChangePassword">
				<h2 class="form-heading">Новый пароль</h2>

				<div class="form-group ">
					<input name="password" type="password" class="form-control" min="7"
						placeholder="Password" autocomplete="off" id="passwordTwo"> 
				    <input
						name="passwordRepeat" type="password" class="form-control"
						placeholder="Password repeate" id="passwordOne"> 
						<span></span> <input
						type="hidden" name="" value="">

					<button class="btn btn-lg btn-primary btn-block" type="submit">Изменить</button>
				</div>

			</form>
		</div>


	</div>


	<!-- Events -->
	<script src="<c:url value="/resources/js/jquery.remodal.js" />"></script>
	<script>
		$(document).on('open', '.remodal', function() {
			console.log('open');
		});

		$(document).on('opened', '.remodal', function() {
			console.log('opened');
		});

		$(document).on('close', '.remodal', function() {
			console.log('close');
		});

		$(document).on('closed', '.remodal', function() {
			console.log('closed');
		});

		$(document).on('confirm', '.remodal', function() {
			console.log('confirm');
		});

		$(document).on('cancel', '.remodal', function() {
			console.log('cancel');
		});

		var inst = $('[data-remodal-id=modal2]').remodal();
	</script>

	<script>
		$(document).scroll(function() {
			$('.block').css({
				top : $(document).scrollTop() + 50

			});

		});
	</script>
</body>
</html>