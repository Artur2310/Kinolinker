<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Movie</title>

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
			<div class="persons">

				<div id=addPerson>
					<a class="btn btn-primary"
						href="${pageContext.request.contextPath}/admin/persons/add_person">Добавить
						кинодеятеля</a>
				</div>

				<div id="search">
<!-- 					<div id="searchPanel"> -->
<!-- 						<form action="persons" method="get" id="searchForm"> -->

<%-- 							<input type="text" name="search" value="${search}" --%>
<!-- 								id="search_query"> <input type="submit" value="Найти" -->
<!-- 								class="search_submit" /> -->

<!-- 						</form> -->
<!-- 					</div> -->
					
					<div id="searchPanel">
						<form action="persons" method="get" id="searchForm">

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

						<form accept-charset="UTF-8" action="persons" method="get">
							<div style="display: none"></div>


							<label>Сортировка: <select id="sort" name="sort" class="select"
								onchange="$(this).parent().parent().submit();">

									<option value="id">Дата добавления</option>
									<option
										<c:if test="${sort eq 'name'}">selected="selected"</c:if>
										value="name">Имя</option>
									<option
										<c:if test="${sort eq 'rating'}">selected="selected"</c:if>
										value="rating">Самые популярные</option>


							</select>
							</label>


						</form>

					</div>
				</div>

				<div id="catalogContent">
					<c:forEach items="${personsList}" var="personItem">
						<div class="person-item">
							<a
								href="${pageContext.request.contextPath}/admin/persons/person/${personItem.id}">

								<img alt="${personItem.name}" class="cover"
								src=<c:if test="${empty personItem.picturePath}"> 
				 "/resources/images/not_found.jpg" 
				</c:if>
								<c:if test="${not empty personItem.picturePath}"> 
				 "${pageContext.request.contextPath}/get_image?path=${personItem.picturePath}"
				</c:if> />
								<span class="name">${personItem.name}</span>
							</a>

						</div>
					</c:forEach>



				</div>

				<c:if test="${pageCount > '1'}">
					<div class="pagination-page">
						<ul class="simple-pagination pagination" id="pagination"></ul>
					</div>
				</c:if>



				<script type="text/javascript">
					$('#pagination').pagination({
						items : "${pageCount}",
						itemOnPage : 8,
						currentPage : "${page}",
						displayedPages : 4,

						hrefTextPrefix : "?sort=${sort}&search=${search}&page=",

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