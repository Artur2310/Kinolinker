<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie page</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="<c:url value="/resources/js/js_adm_movie_page.js" />"></script>


<script src="<c:url value="/resources/js/jquery.simplePagination.js" />"></script>

<link href="<c:url value="/resources/css/movie_page.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/jquery-ui.css" />"
	rel="stylesheet">
	
	<link href="<c:url value="/resources/css/admin_page.css" />"
	rel="stylesheet">

<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.remodal.css"/>">

<link rel="stylesheet"
	href="<c:url value="/resources/css/simplePagination.css"/>">
</head>
<body>

<div id="wrapper">

	<header>
<div class ="home">
	<a href="${contextPath}/">На сайт</a>
	</div>
	
	<div class ="logout">
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
	<div class="movie">
		<div class="system-menu">
			<form method="get" class="menu_button"
				action="${pageContext.request.contextPath}/admin/movies/update_movie">

				<input type="hidden" name="id" value="${movie.id}" /> <input class="btn btn-primary"
					type="submit" value="Редактировать" />
			</form>


			<form method="post" class="menu_button"
				action="${pageContext.request.contextPath}/admin/movies/delete_movie">

				<input type="hidden" name="id" value="${movie.id}" type="hidden"
					id="idMovie" /> <input class="btn btn-primary btn-delete" type="submit" value="Удалить" />
			</form>


		</div>
		<div class="movie_info">
			<h1 id="title">${movie.title}</h1>
			<h3 id="alternativeTitle">${movie.alternativeTitle }</h3>

			<div class="main-pic">
				<img alt="${movie.title}" class="cover"
					src=<c:if test="${empty movie.picturePath}"> 
				 "/resources/images/not_found.jpg" 
				</c:if>
					<c:if test="${not empty movie.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${movie.picturePath}"
				</c:if> />
				<br /> <a href="#modal">Заменить постер</a>

			</div>

			<div style="overflow: hidden;">
				<div class="video-online">

					<c:if test="${not empty movie.trailer}">
						<div>
							<a id="trailer-button" href="${movie.trailer}">Посмотреть
								трейлер</a>
							<div id="trailer" style="display: none;">
								<iframe width="640" height="360" src="${movie.trailer}"
									frameborder="0" allowfullscreen></iframe>
							</div>
						</div>
					</c:if>

				</div>

			</div>
			<ul>
				<li><strong>Жанр:</strong> <c:forEach items="${movie.genre}"
						var="genreItem">
						<a
							href="${pageContext.request.contextPath}/admin/movies?genre=${genreItem.id}">${genreItem.title}
						</a>
					</c:forEach>
				<li><strong>Дата выпуска:</strong> <span class="datetime">
						<fmt:formatDate pattern="dd.MMMM.yyyy"
							value="${movie.releaseDate}" />
				</span></li>



				<li><strong>Страна:</strong> <c:forEach
						items="${movie.country}" var="countryItem">
						<a
							href="${pageContext.request.contextPath}/admin/movies?country=${countryItem.id}">${countryItem.name}
						</a>
					</c:forEach></li>



				<li><a href="${movie.imdbLink}" rel="rel nofollow"
					target="_blank">IMDB:</a> ${movie.imdb}</li>
			</ul>

			<div style="overflow: hidden;">
				<h2>Описание</h2>
				<span itemprop="description"><p>${movie.description}</p></span>
			</div>
		</div>

		<div id="cast">
			<div class="view">

				<div class="directors">

					<div class="header_cast">
						<strong class="header_role">Режисёр:</strong>

						<div class="directorInput">
							<input type="text" name="director" value="${nameDirector}"
								class="inputPerson" id="nameDirector">
						</div>

					</div>

					<div id="directorslist">
						<c:forEach items="${movie.directors}" var="directorItem">
						
							<div class="person-list-item" id="director${directorItem.id}">
										<button type="button" class="btn btn-primary btn-delete" onclick="deleteDirector(${directorItem.id})">Удалить</button>
						
								<a
									href="${pageContext.request.contextPath}/admin/persons/person/${directorItem.id}"
									title="${directorItem.name}"><img height="70"
									src=<c:if test="${empty directorItem.picturePath}"> 
				 "/resources/images/not_found.jpg"  
				</c:if>
									<c:if test="${not empty directorItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${directorItem.picturePath}"
				</c:if>
									width="50" /></a>
								<div class="personname">
									<a
										href="${pageContext.request.contextPath}/admin/persons/person/${directorItem.id}">${directorItem.name}</a>

									
								</div>
							</div>
						</c:forEach>
					</div>

				</div>

				<div class="actors">
					<div class="header_cast">
						<strong class="header_role">Актёры:</strong>
						<div class="actorInput">
							<input type="text" name="actor" value="${nameActor}"
								class="inputPerson" id="nameActor">
						</div>
					</div>
					<div id="castlist">

						<c:forEach items="${movie.actors}" var="actorItem">

							<div class="person-list-item" id="actor${actorItem.id}">
								<button type="button" class="btn btn-primary btn-delete" onclick="deleteActor(${actorItem.id})">Удалить</button>
							
								<a
									href="${pageContext.request.contextPath}/admin/persons/person/${actorItem.id}"
									title="${actorItem.name}"><img height="70"
									src=<c:if test="${empty actorItem.picturePath}"> 
				 "/resources/images/not_found.jpg" 
				</c:if>
									<c:if test="${not empty actorItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${actorItem.picturePath}"
				</c:if>
									width="50" /></a>
								<div class="personname">
									<a
										href="${pageContext.request.contextPath}/admin/persons/person/${actorItem.id}">${actorItem.name}</a>

								</div>
								
							</div>
						</c:forEach>


					</div>

				</div>
			</div>
		</div>
		</div>
	</div>
	

	<div class="remodal-bg">
		<br> <br>

		<div class="remodal" data-remodal-id="modal">
			<form method="post" id="my_form" enctype="multipart/form-data">
				<input type="file" name="file" accept="image/jpeg,image/png"><br />
				<input type="submit" class="button_upload" value="Загрузить">

			</form>
		</div>


	</div>

	<!-- Events -->
	<script src="<c:url value="/resources/js/jquery.remodal.js" />"></script>
	<script>
    $(document).on('open', '.remodal', function () {
        console.log('open');
    });

    $(document).on('opened', '.remodal', function () {
        console.log('opened');
    });

    $(document).on('close', '.remodal', function () {
        console.log('close');
    });

    $(document).on('closed', '.remodal', function () {
        console.log('closed');
    });

    $(document).on('confirm', '.remodal', function () {
        console.log('confirm');
    });

    $(document).on('cancel', '.remodal', function () {
        console.log('cancel');
    }); 



    
    var inst = $('[data-remodal-id=modal2]').remodal();
   
</script>

	<footer> </footer>



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