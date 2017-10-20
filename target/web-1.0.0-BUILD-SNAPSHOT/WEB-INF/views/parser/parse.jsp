<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie page</title>


</head>
<body>

	<form action="/admin/parser/genres" method="get">
		<input type="submit" value="Спарсить жанры" />
	</form>

	<form action="/admin/parser/countries" method="get">
		<input type="submit" value="Спарсить страны" />
	</form>
	<form action="/admin/parser/person" method="get">

		<input type="text" name="personUrl"> <input type="submit"
			value="Парсить кинодеятеля" />

	</form>

	<form action="/admin/parser/movie" method="get">

		<input type="text" name="movieUrl"> <input type="submit"
			value="Парсить фильм" />

	</form>
	<form <c:if test="${parseIsMovie eq 'false'}">action="/admin/parser/movies"</c:if> 
	<c:if test="${parseIsMovie eq 'true'}">action="/admin/parser/movies_stop"</c:if> 
	 method="get">

		<input type="submit"
		<c:if test="${parseIsMovie eq 'false'}">value="Парсить все фильмы"</c:if> 
	<c:if test="${parseIsMovie eq 'true'}">value="Остановить парсинг всех фильмов"</c:if> 
		  />

	</form>
	
	<form <c:if test="${parseIsPerson eq 'false'}">action="/admin/parser/persons"</c:if> 
	<c:if test="${parseIsPerson eq 'true'}">action="/admin/parser/persons_stop"</c:if> 
	 method="get">

		<input type="submit"
		<c:if test="${parseIsPerson eq 'false'}">value="Парсить всех кинодеятелей"</c:if> 
	<c:if test="${parseIsPerson eq 'true'}">value="Остановить парсинг всех кинодеятелей"</c:if> 
		  />

	</form>
	
	</form>
	

	</form>
	<form action="/admin/parser/persons_continue" method="get">

		<input type="submit" value="Продолжить парсить всех кинодеятелей" />

	</form>

	</form>
	<form action="/admin/parser/delete_all_movies" method="post">

		<input type="submit" value="Удалить все фильмы" />

	</form>

	</form>
	<form action="/admin/parser/delete_all_persons" method="post">

		<input type="submit" value="Удалить всех кинодеятелей" />

	</form>
	
	<form action="/admin/parser/parse_directors" method="post">

		<input type="submit" value="Парсить режисёров" />

	</form>

	<c:if test="${parseIsMovie eq 'true'}">Идёт парсинг фильмов</c:if>
	
	<c:if test="${parseIsPerson eq 'true'}">Идёт парсинг кинодеятелей</c:if>



</body>
</html>