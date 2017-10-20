<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<head>
<meta charset="UTF-8">
<title>Kinolinka</title>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/ui_style.css" />" rel="stylesheet">
 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="<c:url value="/resources/js/js_list_movies_ajax.js" />"></script>
     
     
<link rel="stylesheet"  
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  
   
<script>
	$(document).ready(function() {

 		 $(".inputPerson").focus(); //Focus on search field		 	 
		$(".inputPerson").autocomplete({  
			source : '${pageContext.request.contextPath}/kinolinka/getPersons', 
			minLength: 3,
			focus: function (event, ui) {
		        event.preventDefault();
		        this.value = ui.item; 
		    },
	        select: function (event, ui) {
	            event.preventDefault();
	            this.value = ui.item.name; 
	        } 
			      
		}).autocomplete( "instance" )._renderItem = function( ul, item ) {   
		      return $( '<li>' )  
		        .append( "<div><img class='imdbImage' src='/image/actors/" + item.pictureId  +".jpg' width='30' height='35' /><a class='imdbTitle'>" + item.name  +"</a></div>")
// 		        .append("<a class='imdbTitle'>" + item.name  +"</a>")
		        .appendTo( ul );  
		    };
  
		    $(".inputPerson1").focus(); //Focus on search field		 	 
			$(".inputPerson1").autocomplete({  
				source : '${pageContext.request.contextPath}/kinolinka/getPersons', 
				minLength: 3,
				focus: function (event, ui) {
			        event.preventDefault();
			        this.value = ui.item; 
			    },
		        select: function (event, ui) {
		            event.preventDefault();
		            this.value = ui.item.name; 
		        } 
				      
			}).autocomplete( "instance" )._renderItem = function( ul, item ) {   
			      return $( '<li>' )  
			        .append( "<div><img class='imdbImage' src='/image/actors/" + item.pictureId  +".jpg' width='30' height='35' /><a class='imdbTitle'>" + item.name  +"</a></div>")
//	 		        .append("<a class='imdbTitle'>" + item.name  +"</a>")
			        .appendTo( ul );  
			    };
	});     

	 
</script>

<style type="text/css">
 .ui-menu-item .imdbTitle{ 
     font-size: 0.9em; 
     font-weight: bold; 
 } 
  
 .ui-menu-item .imdbImage{ 
     float: left; 
     margin-right: 5px; 
 } 
  
  li div.ui-menu-item-wrapper{    
   display: inline-block;   
   } 
  
   .ui-menu-item{ 
   position:relative;  
   display: inline-block;  
   } 
 
   #ui-id-1 { 
   width:200px;   
   } 
</style> 
</head>
<body>

	<header>
		
	</header> 
	<div class="content">
		<div class="wrapper">
			<div id="centerBlock"> 
 
				<form action="listMovies" method="post" id="lineForm">

					<ul class="ulForm">
						<li id="formText1" class="ui-widget"><input type="text"
							name="person1" value="${nameOne}" class="inputPerson"></li>


						<li id="buttonSearch"><input type="submit" name="search"
							value="Search" class="submit" /></li>

						<li id="formText2" class="ui-widget"><input type="text"
							name="person2" value="${nameTwo}" class="inputPerson1"></li>

					</ul>
				</form>

			</div>

		</div>


	</div>
	<footer> </footer>
	
</body>
</html>
