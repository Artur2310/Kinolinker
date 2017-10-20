<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/admin_page.css" />" rel="stylesheet">
</head>
<body>
<div class="movie-list"> 
	<c:forEach items="${listMovie}" var="movie">


		<div class="movie-wrap">
			<div class="movie-image">
				<img alt="${movie.title}" class="cover"
					src="<c:url value="/image/movies/${movie.pictureId}.jpg" />" />
			</div>
			<h2>${movie.title}</h2>
			<div class="movie-description">${movie.description}</div>
		</div>
	</c:forEach>
	</div>
</body>
</html>