$(window).load(
		function() {
//Отправляем ajax запрос и отображаем список фильмов на странице
			$.ajax(
					{
						url: "/kinolinka/listMovies'",
						dataType : "json",
						success : function(data) {

							$(".movie-list").remove();

							if (data.result !== null) {

								var code = '<div class="movie-list"></div>';
								$("#centerBlock").append(code);
 
								$.each(data.result, function() {

									
									var list = '<div class="movie-wrap">'
										+'<h2 class="movieTitle">'+ this.title+'</h2>'
											+ '<div class="movie-image">'
											+ '<img alt="' + this.pictureId 
											+ '" class="cover"'
											+ 'src="/image/movies/'
											+ this.pictureId + '.jpg" />'
											+ '</div>' 
											+'<div class="genre">'
											+'<strong>Жанр: </strong>'
											+this.genre+'</div>'
											+'<div class="imdb">'
											+'<strong>IMDB: </strong>' 
											+imdb+'</div>'  
											+ '</div>';
									$(".movie-list").append(list); 

								});
							}

						},
						error : function(e) {
							console.log("ERROR: ", e);

						}

					});
		});