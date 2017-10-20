$(document).ready(
		function() {
			
			blockSend = false;

			/*Очистить поле ввода имени и обновить фильмы*/
			removeSelectBtn = function(element, number) {

			personBlockClear(element.parentElement, number);	 
		
			}

			
			/*Отправка формы с именами. Заполнение страницы фильмами*/
			sendingForm = function() {

				if(blockSend) return;
				deleteLinks();
				blockSend = true;
				 checkPersonsForm("One");
                 checkPersonsForm("Two");          


 				if($("#allEntriesMovie").length==0){
 						getMovies();

 				}
 				if($("#allEntriesMovie").length==1){

 					$("#allEntriesMovie").slideUp(600, function() {

 						$("#allEntriesMovie").remove();	

 						getMovies();
 						
 				});
 				}
				
					

			}
		
		//Получить список фильмов для двух имён
		getMovies = function(){
			var msg   = $('#formPersons').serialize();
			
			$.ajax({
				url : "/list_movies_for_pair",
				dataType : "json",
				method : "post",
				data: msg,
				success : function(data) {
					
					if (data !== null) {
			
						$('#descriptionOfSite').hide();
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
						var ms = 200;
						$(entriesBlock).hide().slideDown(Math.ceil(data.length/3)*ms,function(){
						
						});
						showLinks();
					}
					blockSend = false;		
				},
				error : function(e) {
					blockSend = false;

					console.log("ERROR: ", e);

				}

			});
			
		}
		
		//Заполнение поля ввода имени при ручном наборе (без автопоиска)
			checkPersonsForm = function(number){
				
				/*Убираем поле быстрого поиска*/
				if(number=="One")
					id = 1;
				else
					id = 2;

				$("#ui-id-"+id).css("display","none");
				

				//Если поле ввода пустое то очищаем блок с фото и убираем фильмы
				if($("#person"+number).val().length===0 && $("#personSelect"+number).length==true){
					
					personBlockClear($("#personSelect"+number),number);
				}else{
					$.ajax({
						url : "/get_person",
						dataType : "json",
						method : "post",
						data: {"name":$("#person"+number).val()},
						success : function(data) {
							
							
                                  if(data!=null){
	
                                	  //Заменяем блок с фото на новыйй
	                               if($("#personSelect"+number).length==false || $("#personSelect"+number+" p").text()!=data.name ){
	                                	personBlockClear($("#personSelect"+number),number);
	                                	showSelect(data, number);
	                                  }
	                                    $("#person"+number).val(data.name);
                                }
                                  
                                  else{
                                	  $("#person"+number).val('');
                                	  if($("#personSelect"+number).length==true){
          								$("#personSelect"+number).hide(400, function() {
          									$("#personSelect"+number).remove();		
          								});
          								
          							}
                                  }
                        },
						error : function(e) {
	
							console.log("ERROR: ", e);

						}

					});
				}
			
			}
			
			/*Очистка поля ввода имени и блока с фотографией (и автоматическое обновление фильмов)*/
			personBlockClear  =function(element, number){
                $("#person" + number).val("");
                hideLinks();
				if ($("#allEntriesMovie").length == false) {
					
					$(element).hide(400, function() {

						$(element).remove();
						
						if($('.personSelect').length==false){

							deleteLinks();
						}else{
							showLinks();
						}
							
					});
				}
				else{
				 
						/*Удаляем со страницы кинодеятеля*/
						$(element).hide(400, function() {
							
							$(element).remove();
							
							if($('.personSelect').length==false){

								deleteLinks();
							}else{
								showLinks();
							}
							
							 /*Обновляем список фильмов*/
							$('#formPersons').submit();
								
						});
						
					
				}
			}
			
		
			
});