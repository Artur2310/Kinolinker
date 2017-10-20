<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<head>
<meta charset="UTF-8">
 <meta name="keywords" content="актёры в одном фильме, режисёр с актёром, кинодеятели, связи в кино, фильмы в которых учавствовал, актёры режисёры" />
<title>KinoLinker</title>
 
 <link rel="shortcut icon" href="/resources/images/icon.ico" type="image/x-icon">

<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/jquery-ui.css" />"
	rel="stylesheet">


<link href="<c:url value="/resources/css/component_modal.css" />"
	rel="stylesheet">
<%-- <link href="<c:url value="/resources/css/component.css" />" --%>
<!-- 	rel="stylesheet"> -->
<link href="<c:url value="/resources/css/ui_style.css" />"
	rel="stylesheet">
	
<link href="<c:url value="/resources/css/infiniteCarousel.css" />"
	rel="stylesheet">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script
	src="<c:url value="/resources/js/js_list_movies_home_ajax.js" />"></script>
<script src="<c:url value="/resources/js/persons/js_autocomplete_home.js" />"></script>
<script src="<c:url value="/resources/js/hover_movies_js.js" />"></script>
<script src="<c:url value="/resources/js/persons/modal_window.js" />"></script>
<script src="<c:url value="/resources/js/persons/button_event.js" />"></script>
<script src="<c:url value="/resources/js/lib/formatDate.js" />"></script>
<script src="<c:url value="/resources/js/lib/infiniteCarousel.js" />"></script>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />">


</head>
<body>
	<div id="wrap">

<!-- 		<div class="header"> -->
<!-- 		<div id="navigation" > -->
<!-- 		    <div id="logo"> -->
<!--                  <a href="/"><img alt="KinoLinker"  src="/resources/images/logo.png" ></a> -->
<!-- 		    </div> -->
		    
<!-- 		    <ul class="nav-list"> -->
<!-- 		        <li class = "nav-item nav-item-select"><a href="">Кинодеятели</a></li> -->
<!-- 		        <li class ="nav-item"><a href="/movies">Фильмы</a></li> -->
<!-- 		    </ul> -->
<!-- 		    </div> -->
<!-- 		</div> -->
		
        <div class="container-fluid">
		 <div class="navbar navbar-inverse navbar-static-top" id="head-menu">
			     <div class="col-sm-1 hidden-xs">
				 </div>
			     <div class="col-sm-2 hidden-xs">
				      <a href="/" class="navbar-brand" id="logo"><img alt="KinoLinker" src="/resources/images/logo.png"></a>
				 </div>
				 
				 <div class="col-xs-12 col-sm-7">
				      <ul class="nav navbar-nav navbar-right" id="nav-list">
						    <li class="nav-item nav-item-select"><a href="" >Кинодеятели</a></li>						    
						    <li class="nav-item"><a href="/movies">Фильмы</a></li>	
					  </ul>
				 </div>
				 <div class="col-sm-2 hidden-xs">
				 </div>
		</div>
       </div>	
		
		
		<div id="content">

			<div id="searchPanel">
				<form action="javascript:void(null);" id="formPersons"
					onsubmit="sendingForm()" method="post">
                  <div class="col-md-4">
					<p class="i">
						<input type="text" id="personOne" class="personInput input" value="${name}"
							name="nameOne" placeholder="Введите имя" spellcheck="false">
					</p>
				  </div>	
				  <div class="col-md-4">
					<p class="i s">
						<button class="btnSubmit btn-1 btn-1a">НАЙТИ</button>
					</p>
				  </div>
				  <div class="col-md-4">
					<p class="i">
						<input type="text" id="personTwo" class="personInput input"
							name="nameTwo" placeholder="Введите имя" spellcheck="false">
					</p>
                  </div>

				</form>

				<div class="personInfo"></div>

			</div>

                <div id="descriptionOfSite"><h3>Привет всем любителям кино!  Наш сервис поможет вам найти фильмы, в которых участвуют ваши любимые актёры или режиссёры. Просто введите в первое окно ввода имя кинодеятеля,  и нажмите кнопку «Найти». Если хотите узнать его связующие  фильмы с другим кинодеятелем, введите его имя во второе окно или выберите вариант из предложенных. </h3></div>

		</div>


	</div>

	<form action="#modal" id="modalButton" type="hidden"></form>


	<div class="md-modal md-effect-1" id="modal-1">
		<div class="md-content">
			<div class="remodal-close" onclick="closeModal()"></div>


		</div>
	</div>

	<button class="md-trigger" data-modal="modal-1" style="display: none;">Fade
		in &amp; Scale</button>

	<div class="md-overlay"></div>

	<script src="<c:url value="/resources/js/modalEffects.js" />"></script>
	<script src="<c:url value="/resources/js/classie.js" />"></script>
	<script src="<c:url value="/resources/js/cssParser.js" />"></script>
	
	<script type="text/javascript">

	$( document ).ready(function() {
		if($("#personOne").val()!=="")
	    $( "#formPersons" ).submit();
	});
</script>

<!-- Yandex.Metrika counter -->
<script type="text/javascript" >
    (function (d, w, c) {
        (w[c] = w[c] || []).push(function() {
            try {
                w.yaCounter46156740 = new Ya.Metrika({
                    id:46156740,
                    clickmap:true,
                    trackLinks:true,
                    accurateTrackBounce:true,
                    webvisor:true
                });
            } catch(e) { }
        });

        var n = d.getElementsByTagName("script")[0],
            s = d.createElement("script"),
            f = function () { n.parentNode.insertBefore(s, n); };
        s.type = "text/javascript";
        s.async = true;
        s.src = "https://mc.yandex.ru/metrika/watch.js";

        if (w.opera == "[object Opera]") {
            d.addEventListener("DOMContentLoaded", f, false);
        } else { f(); }
    })(document, window, "yandex_metrika_callbacks");
</script>
<noscript><div><img src="https://mc.yandex.ru/watch/46156740" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
<!-- /Yandex.Metrika counter -->
</body>



<script src="<c:url value="/resources/js/jquery.remodal.js" />"></script>
</html>