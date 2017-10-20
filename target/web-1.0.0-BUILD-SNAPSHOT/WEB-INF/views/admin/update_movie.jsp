<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изменить информацию о фильме</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<link href="<c:url value="/resources/css/add-movie.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/admin_page.css" />"
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
			</ul>
		</div>
	</div>

	
		<div id="wrapper">

			<div class="movie-form">
				<h2 class="title-form">Введите информацию о фильме</h2>
				<form:form method="POST"
					action="${pageContext.request.contextPath}/admin/movies/update_movie"
					modelAttribute="movie">
					<ul>


						<form:input type="hidden" path="id" id="id" />

						<form:input type="hidden" path="picturePath" id="picturePath" />


						<li><form:label path="title">Название:</form:label> <form:input
								class="field-input" path="title" /> <form:errors path="title"
								cssClass="error" /></li>

						<li><form:label path="alternativeTitle">Альтернативное название:</form:label>
							<form:input class="field-input" path="alternativeTitle" /> <form:errors
								path="alternativeTitle" cssClass="error" /></li>

						<li><form:label path="trailer">Трейлер:</form:label> <form:input
								class="field-input" path="trailer" /> <form:errors
								path="trailer" cssClass="error" /></li>

						<li><form:label path="releaseDate">Дата релиза:</form:label>
							<form:input class="field-input" path="releaseDate"
								placeholder="dd/MM/yyyy" /> <form:errors path="releaseDate"
								cssClass="error" /></li>
						<li><form:label path="imdbLink">IMDB link:</form:label> <form:input
								class="field-input" path="imdbLink"
								placeholder="http://www.imdb.com/title/" /> <form:errors
								path="imdbLink" cssClass="error" /></li>

						<li class="description-li"><form:label path="description">Описание:</form:label>
							<form:textarea class="description-field" path="description" /></li>

						<li class="select-li"><form:label path="genre">Жанр:</form:label>
							<form:select class="field-select" path="genre"
								items="${listGenre}" multiple="true" itemValue="id"
								itemLabel="title" /></li>

						<li class="select-li"><form:label path="country">Страна:</form:label>
							<form:select class="field-select" path="country"
								items="${listCountry}" multiple="true" itemValue="id"
								itemLabel="name" /></li>


						<footer>
							<input type="submit" class="btnLogin" value="Обновить"
								tabindex="4">
						</footer>
					</ul>

				</form:form>

			</div>

		</div>

	

</body>