<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Person page</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script
	src="<c:url value="/resources/js/js_adm_person_page.js" />"></script>
 
<link href="<c:url value="/resources/css/person_page.css" />"
	rel="stylesheet">
	
	<link href="<c:url value="/resources/css/admin_page.css" />"
	rel="stylesheet">
	
	<link href="<c:url value="/resources/css/jquery-ui.css" />"
	rel="stylesheet">

<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.remodal.css"/>">
</head>
<body>

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
	<div class="person">
		<div class="system-menu">
			<form method="get" class="menu_button"
				action="${pageContext.request.contextPath}/admin/persons/update_person">

				<input type="hidden" name="id" value="${person.id}" /> <input class="btn btn-primary"
					type="submit" value="Редактировать" />
			</form>


			<form method="post" class="menu_button"
				action="${pageContext.request.contextPath}/admin/persons/delete_person">

				<input type="hidden" name="id" value="${person.id}" type="hidden" 
				id="idPerson"/>
				<input class="btn btn-primary btn-delete" type="submit" value="Удалить" />
			</form>


		</div>
		<div class="person_info">
			<h1 id="name">${person.name}</h1>


			<div class="main-pic">
				<img alt="${person.name}" class="cover"
					src=<c:if test="${empty person.picturePath}"> 
				 "/resources/images/not_found.jpg" 
				</c:if>
					<c:if test="${not empty person.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${person.picturePath}"
				</c:if> />
					<br />
				<a
					href="#modal">Заменить 
					фото</a>

			</div>

			


			<div id="movies">
				<div class="view">

					<div class="director_of_movies">

						<div class="header_movies">
							<strong class="header_role">Режисёр:</strong>
							<div class="movieInput">
								<div class="dMovieInput">
									<input type="text" name="dMovie" value="${titleDMovie}"
										class="input_movie" id="title_dMovie">
								</div>
							</div>
						</div>

						<div id="dMovieslist">
							<c:forEach items="${person.directorOfMovies}" var="movieItem">
								<div class="movie-list-item" id="dMovie${movieItem.id}">
				<button type="button" class="btn btn-primary btn-delete" onclick="deleteDMovie(${movieItem.id})">Удалить</button>
								
									<a
										href="${pageContext.request.contextPath}/admin/movies/movie/${movieItem.id}"
										title="${movieItem.title}"><img height="70"
										src=<c:if test="${empty movieItem.picturePath}"> 
				 "/resources/images/not_found.jpg"  
				</c:if>
										<c:if test="${not empty movieItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${movieItem.picturePath}"
				</c:if>
										width="50" /></a>
									<div class="movie_title">
										<a
											href="${pageContext.request.contextPath}/admin/movies/movie/${movieItem.id}">${movieItem.title}</a>

									</div>
								</div>
							</c:forEach>
						</div>

					</div>

					<div class="actor_of_movies">

						<div class="header_movies">
							<strong class="header_role">Актёр:</strong>
							<div class="movieInput">
								<div class="aMovieInput">
									<input type="text" name="aMovie" value="${titleAMovie}"
										class="input_movie" id="title_aMovie">
								</div>
							</div>
						</div>

						<div id="aMovieslist">
							<c:forEach items="${person.actorOfMovies}" var="movieItem">
								<div class="movie-list-item" id="aMovie${movieItem.id}">
								
				<button type="button" class="btn btn-primary btn-delete" onclick="deleteAMovie(${movieItem.id})">Удалить</button>
								
									<a
										href="${pageContext.request.contextPath}/admin/movies/movie/${movieItem.id}"
										title="${movieItem.title}"><img height="70"
										src=<c:if test="${empty movieItem.picturePath}"> 
				 "/resources/images/not_found.jpg"  
				</c:if>
										<c:if test="${not empty movieItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${movieItem.picturePath}"
				</c:if>
										width="50" /></a>
									<div class="movie_title">
										<a
											href="${pageContext.request.contextPath}/admin/movies/movie/${movieItem.id}">${movieItem.title}</a>
									</div>
								</div> 
							</c:forEach>
						</div>

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
				<input type="file" name="file" accept="image/jpeg,image/png"><br /> <input type="submit"
					class="button_upload" value="Загрузить">

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
	
<script>
		$(document).scroll(function() {
			$('.block').css({
				top : $(document).scrollTop() + 50

			});

		});
	</script>
</body>
</html>