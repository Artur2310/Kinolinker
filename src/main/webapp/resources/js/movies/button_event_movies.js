$(document).ready(
		function() {	
			
			blockSend = false;

			
			/*Отправка формы с названием. Заполнение страницы фильмами*/
			sendingForm = function() {
        
				if(blockSend) return;

				blockSend = true;
            	if($("#allEntriesMovie").length==0){
 						getMovies();

 				}
 				if($("#allEntriesMovie").length==1){

 					$("#allEntriesMovie").slideUp(500, function() {

 						$("#allEntriesMovie").remove();	

 						getMovies();
 						
 				});
 				}		
			}
		
		//Получить список фильмов для двух имён
		getMovies = function(){
			var msg   = $('#formMovies').serialize();
			title = $('#movieInput').val();
			
			console.log(title);
			$.ajax({
				url : "/get_movies",
				dataType : "json",
				method : "post",
				data: {'title':title},
				success : function(data) {
					
					if (data !== null) {
			
						/*Создаём внешний блок для всех фильмов*/
						var entriesBlock = $('<div id="allEntriesMovie"></div>');

						//Заполнение главного блока фильмами
						$.each(data, function() {

							var genres ='';
							$.each(this.genre, function() {
								genres = genres+this.title+' ';
							});
							
							date = new Date(this.releaseDate).getFullYear();
							
							var picPath;
							if (this.picturePath) {
								picPath = "/get_image?path=" + this.picturePath;
							} else {
								picPath = "/resources/images/not_found.jpg";
							}
							
							imdb = '';
							if(this.imdb!==0){
								imdb = ', IMDb '+this.imdb;
							}
							var list = '<div id="movie'+this.id+'" class="entryMovie">'+
							'<div class="mBlock" onclick="openModalWindow('+this.id+')">'+
							'<div class="poster"'+
								'style="background: url('+picPath+') #fff; background-position: center center; height: 100%;">'+
							'</div>'+
							'<div class="infoMovie">'+
								'<p class="title">'+this.title+'</p>'+
								date+imdb+
								'<p class="genre">'+genres+'</p>'+
							'</div>'+
						'</div>'+
					'</div>';
							$(entriesBlock).append(list);
							
						});
					
						$("#content").append(entriesBlock);
						
						//Анимация показа списка фильмов
						var ms = 100;
						$(entriesBlock).hide().slideDown(Math.ceil(data.length/3)*ms,function(){
						
						});
					}
					blockSend = false;		

				},
				error : function(e) {
					blockSend = false;		

					console.log("ERROR: ", e);

				}

			});
			
		}
		
		
		
			
});