$(document).ready(function() {

	//Быстрый поиск для первой персоны
					$("#personOne").autocomplete({
						source : '/get_persons',
						minLength : 2,
						focus : function(event, ui) {
							event.preventDefault();
							this.value = ui.item.name;
						},
						select : function(event, ui) {
							console.log("enter");
							event.preventDefault();
							this.value = ui.item.name;
							if ($("#personSelectOne").length == true) {
								removeSelect("#personSelectOne")
							}
							showSelect(ui.item, "One");
							
							
						},
						open : function() {
							
							$('.ui-menu').width($('#personOne').width());
							
							$('#ui-id-1').hide().show('blind',500);
						},

					}).autocomplete("instance")._renderItem = function(ul, item) {
						$("#ui-id-1").scrollTop();
						var picPath;
						if (item.picturePath) {
							picPath = "/get_image?path=" + item.picturePath
						} else {
							picPath = "/resources/images/not_found.jpg"
						}
						return $('<li>')
								.append(
										"<div><img class='person' src='"
												+ picPath
												+ "' width='30' height='35' /><div class='namePerson'>"
												+ item.name + "</div></div>")
								.appendTo(ul)
					};

					//Быстрый поиск для второй персоны
					$("#personTwo").autocomplete({
						source : '/get_persons',
						minLength : 2,
						focus : function(event, ui) {
							event.preventDefault();
							this.value = ui.item.name;
						},
						select : function(event, ui) {
							event.preventDefault();
							this.value = ui.item.name;
							if ($("#personSelectTwo").length == true) {
								removeSelect("#personSelectTwo")
							}
							showSelect(ui.item, "Two");
						},
						open : function() {
							$('.ui-menu').width($('#personTwo').width());
							$('#ui-id-2').hide().show('blind',500);
						},

					}).autocomplete("instance")._renderItem = function(ul, item) {
						$("#ui-id-2").scrollTop();
						$("#ui-id-2").css('width','266');
						var picPath;
						if (item.picturePath) {
							picPath = "/get_image?path=" + item.picturePath
						} else {
							picPath = "/resources/images/not_found.jpg"
						}
						return $('<li>')
								.append(
										"<div><img class='person' src='"
												+ picPath
												+ "' width='30' height='35' /><div class='namePerson'>"
												+ item.name + "</div></div>")
								.appendTo(ul);
					};

					showSelect = function(person, number) {
						deleteLinks();
						$("#ui-id-1").scrollTop();
						picPath = "/get_image?path=" + person.picturePath;
						
						var blockPerson = $('<div class="personSelect"  id="personSelect'+ number + '"></div>');
						
						if(number=="One"){
							$(".personInfo").prepend(blockPerson);
						}
						else{
						$(".personInfo").append(blockPerson);
						}
						
						blockPerson.append(

										'<img src="' + picPath + '" alt="'
										+ person.name + '">' + '<p>'
										+ person.name + '</p>' +

										'<button class="buttonClear" id="buttonClear'
										+ number
										+ '" onclick="removeSelectBtn(this,\''
										+ number + '\')" >ОЧИСТИТЬ</button>'
									).hide().show('blind',600,function(){
										showLinks(person.name,number);
									});
						
					};

					removeSelect = function(id) {
						$(id).remove();
						
					}
					
					//Есть ли на странице список связующих персон
					checkLinks = false;
					
					//Отобразить список связующих персон
					showLinks = function(name,number){
						
						if ($('.infiniteCarousel').length == true) console.log("Exist");
						if (checkLinks == true || $('.personSelect').length==2 || $("#carousel").length==true){
							return;
						}
						checkLinks = true
						var name = '';
						var side = '';
						if($('#personSelectOne').length==true){
							name = $('#personSelectOne p').text();
							side = 'right';
						}
						
						if($('#personSelectTwo').length==true){
							name = $('#personSelectTwo p').text();
							side = 'left';
						}

						$.ajax({
				  			url : "/get_links",
				  			data: {"name":name},
				  			dataType : "json",
				  			method : "post",
				  			success : function(data) {
				  				
				  				if(data!==null){
				  					var carousel=$('<div class="infiniteCarousel" id="carousel"></div>');
				  					var wrap = '<div class="wrapper"><ul>';
				  					
				  					$.each(data, function() {
				  						var picPerson;
				  		  	          	if (this.picturePath) {
				  		  	          	    picPerson = "/get_image?path=" + this.picturePath;
				  		  					} else {
				  		  					picPerson = "/resources/images/not_found.jpg";
				  		  					}
				  						wrap = wrap+'<li><div class="linkItem" href="#" onclick="clickLink(\''+this.name+'\')" title="'+this.name+'"><img src='+picPerson+' height="95" width="75" alt='+this.name+' /></div></li>';
				  		  				});  
				  					
				  					wrap = wrap + '</ul></div>';
				  					
				  					carousel.append(wrap);
				  					
				  					if(side=='left'){
				  						carousel.addClass('infiniteCarouselLeft');
				  					}
				  					if(side=='right'){
				  						carousel.addClass('infiniteCarouselRight');
				  					}
				  					
				  					
				  					$('.personInfo').append(carousel);
				  					
				  					 $('.infiniteCarousel').infiniteCarousel();
				  					carousel.hide().show('1');
				  				}
				  				
				  				checkLinks = false;
				  				
				              },
				  			error : function(e) {		
				  				checkLinks = false;
				  				console.log("ERROR: ", e);

				  			}

				  		});
					}
					
					deleteLinks = function(){
					console.log('delete');
						if(checkLinks == false){
							checkLinks = true;
							if($('.infiniteCarousel').length==true){
								$('.infiniteCarousel').remove();

							}
                            checkLinks = false;
						}
					}
					
					clickLink = function(name){
					
						if($('#personSelectOne').length==true){
							
							$('#personTwo').val(name);
						}
						
						if($('#personSelectTwo').length==true){
							$('#personOne').val(name);
						}
						
						$('#formPersons').submit();
						
					}
					
					hideLinks = function(){
						if($('.infiniteCarousel').length==true){
							console.log('hide');
							
							$('#carousel').hide();
						}
						
					}
					
					$(document).on(
							{
							    mouseenter: function() 
							    {
							        $(this).css({"transform":"scale(1.1)","transition":"0.5s"});
							    },
							    mouseleave: function()
							    {
							    	 $(this).css({"transform":"scale(1.00)"});
							    }
							}
							, '.linkItem');

});