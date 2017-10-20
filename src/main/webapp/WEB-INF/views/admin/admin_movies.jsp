<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Movies</title>

<link href="<c:url value="/resources/css/admin_page.css" />"
	rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value="/resources/css/simplePagination.css"/>">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="<c:url value="/resources/js/jquery.simplePagination.js" />"></script>



</head>
<body>
	<div id="wrapper">

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
			<div class="movies">

				<div id=addMovie>
					<a class="btn btn-primary"
						href="${pageContext.request.contextPath}/admin/movies/add_movie">Добавить
						фильм</a>
				</div>

				<div id="search">
					<div id="searchPanel">
						<form action="movies" method="get" id="searchForm">

							<div class="input-group">
								<input type="text" value="${search}" name="search"
									id="search_query" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-primary" id="btn-search" type="submit">
										Найти <i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>

					<div>

						<form accept-charset="UTF-8" action="movies" method="get">
							<div style="display: none"></div>


							<label>Сортировка: <select id="sort" class="select"
								name="sort" onchange="$(this).parent().parent().submit();">

									<option value="id">Дата добавления</option>
									<option
										<c:if test="${sort eq 'releaseDate'}">selected="selected"</c:if>
										value="releaseDate">Новинки</option>
									<option
										<c:if test="${sort eq 'imdb'}">selected="selected"</c:if>
										value="imdb">Рейтинг</option>
									<option
										<c:if test="${sort eq 'title'}">selected="selected"</c:if>
										value="title">Название</option>

							</select>

							</label> <label>Страна: <select id="country_selector"
								class="select" name="country"
								onchange="$(this).parent().parent().submit();">
									<option value="0">Любая</option>
									<c:forEach items="${countryList}" var="countryItem">

										<option
											<c:if test="${countryId eq countryItem.id}">selected="selected"</c:if>
											value="${countryItem.id}">${countryItem.name}</option>

									</c:forEach>
							</select>
							</label> <label>Жанр: <select id="genre" name="genre"
								class="select" onchange="$(this).parent().parent().submit();"><option
										value="0">Любой</option>
									<c:forEach items="${genreList}" var="genreItem">

										<option
											<c:if test="${genreId eq genreItem.id}">selected="selected"</c:if>
											value="${genreItem.id}">${genreItem.title}</option>

									</c:forEach>
							</select>
							</label>

						</form>

					</div>
				</div>

				<div id="catalogContent">
					<c:forEach items="${moviesList}" var="movieItem">
						<div class="movie-item">
							<a
								href="${pageContext.request.contextPath}/admin/movies/movie/${movieItem.id}">

								<img alt="${movieItem.title}" class="cover"
								src=<c:if test="${empty movieItem.picturePath}"> 
				 "/resources/images/not_found.jpg" 
				</c:if>
								<c:if test="${not empty movieItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${movieItem.picturePath}"
				</c:if> />
								<span class="title">${movieItem.title}</span>
							</a> <span class="add-info"> <fmt:formatDate pattern="yyyy"
									value="${movieItem.releaseDate}" /> <span class="imdb">IMDb</span>
								${movieItem.imdb},

							</span> <span> ${movieItem.genre} </span>

						</div>
					</c:forEach>

				</div>
				<c:if test="${pageCount > '1'}">
					<div class="pagination-page">
						<ul class="simple-pagination pagination" id="pagination"></ul>
					</div>
				</c:if>



				<script type="text/javascript">
					$('#pagination')
							.pagination(
									{
										items : "${pageCount}",
										itemOnPage : 8,
										currentPage : "${page}",
										displayedPages : 4,

										hrefTextPrefix : "?sort=${sort}&country=${country}&genre=${genre}&search=${search}&page=",

										prevText : '<span aria-hidden="true">&laquo;</span>',
										nextText : '<span aria-hidden="true">&raquo;</span>',
										onInit : function() {
											// fire first page loading
										},
										onPageClick : function(page, evt) {
											// some code
										}
									});
				</script>

			</div>
		</div>
	</div>

	<script> 
 		$(document).scroll(function() {
 			$('.block').css({
 				top : $(document).scrollTop() + 50

 			});
 		});
 	</script>

</body>
</html>