<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<head>
<meta charset="UTF-8">
<title>Kinolinka</title> 
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/jquery-ui.css" />" rel="stylesheet"> 
  
<script src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="<c:url value="/resources/js/js_list_movies_home_ajax.js" />"></script>
<script src="<c:url value="/resources/js/js_autocomplete_home.js" />"></script>
      

</head>
<body>

	<header>
		
	</header> 
	
	<div class="content">
		<div class="wrapper">
			<div id="centerBlock"> 
 
				<form action="listMoviesHome" method="post" id="lineForm">
 
					<ul class="ulForm"> 
						<li class="formText"><input type="text"
							name="person1" value="${nameOne}" class="inputPerson" id="personOne">
							</li>
 

						<li id="buttonSearch"><input type="submit" name="search"
							value="Search" class="submit" /></li> 

						<li class="formText"><input type="text" 
							name="person2" value="${nameTwo}" class="inputPerson" id="personTwo"></li>

					</ul>
				</form>
				
				<ul class="ulPersons"> 
				   <li id="liPersoneOne">
				   <div class="personeItem">
				   <img id="personeImageOne" src="/image/actors/291.jpg" >
				   <div id="namePersoneOne">Леонардо ДиКаприо</div> 
				   </div>
				   </li>   
				   <li id="liPersoneTwo">
				   <div class="personeItem">
				     <img id="personeImageTwo" src="/image/actors/291.jpg">
				   <div id="namePersoneTwo">Леонардо ДиКаприо</div> 
				    </div>
				   </li> 
				   
				</ul>

			</div>

		</div>


	</div>
	
	<footer> </footer>
	
</body>
</html>
