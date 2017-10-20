<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить кинодеятеля</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<link href="<c:url value="/resources/css/admin_page.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/add_person.css" />"
	rel="stylesheet">

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
		
	<div id="wrapper">

		<div class="person-form"> 
		<h2 class="title-form">Введите информацию</h2>
			<form:form method="POST" 
				action="${pageContext.request.contextPath}/admin/persons/add_person"
				modelAttribute="person">
				<ul>

 
					<form:input type="hidden" path="id" id="id" />
					
					
					<li><form:label path="name">Имя:</form:label> <form:input
							class="field-input" path="name" /> <form:errors
							path="name" cssClass="error" /></li>
							
					
					  
					<footer>
						<input  type="submit" class="btnLogin" value="Добавить" tabindex="4">
					</footer>
				</ul>

			</form:form>

			
		</div>

	</div>

	

</body>
</html>