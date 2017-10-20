$(document).ready(function() {
  
//			     Autocomplite for actor input
			    $("#title_aMovie").autocomplete({    
					source : '/get_movies/'+document.getElementById("idPerson").value+'?role=actor',   
					minLength: 3, 
					focus: function (event, ui) {
				        event.preventDefault();
				        this.value = ui.item.title; 
				    },
			        select: function (event, ui) {
			            event.preventDefault();
			            this.value = ui.item.title;
			            var id = document.getElementById("idPerson").value
			            $.ajax({
			            	   type: "POST",
			            	   url: "/admin/persons/add_amovie",
			            	   data: {"idPerson":id, "idMovie":ui.item.id},
			            	   dataType : "json",
			            	   success: function(data){ 
			            		     
 
			            		   if(data != null){ 
			            			    
			            			    var picPath; 
				   				    	if(data.picturePath){picPath="/get_image?path="+data.picturePath}
				   				    	else{picPath="/resources/images/not_found.jpg"} 
				   				    	
			            			   $("#aMovieslist").append('<div class="movie-list-item" id="aMovie'+data.id+'">'
										+'<button type="button" class="btn btn-primary btn-delete" onclick="deleteAMovie('+data.id+')">Удалить</button>'
			            					   +'<a href="/admin/movies/movie/'+data.id+'"'
										+'title="'+data.title+'"><img height="70"'
										+'src="'+picPath+'" width="50" /></a>'
									+'<div class="movie_title">'
										+'<a href="/admin/movies/movie/'+data.id+'">'+data.title+'</a>'
									+'</div>'
								); 
			            			  
			            		   } 
			            	   } 
			            	 });
			            $("#title_aMovie").val("");
			           
			        },   
			        open: function() {  
		                $('.ui-menu')  
		                    .width(262);  
		            } , 
			 		      
				}).autocomplete( "instance" )._renderItem = function( ul, item ) { 
			    	var picPath; 
			    	if(item.picturePath){picPath="/get_image?path="+item.picturePath}
			    	else{picPath="/resources/images/not_found.jpg"}
				      return $( '<li>' )   
					        .append( "<div><img class='movie' src='"+picPath+"' width='30' height='35' /><div class='titleMovie'>" + item.title  +"</div></div>")
				        .appendTo( ul );  
				    };  
				    
				    
//				    Autocomplite for director input
				    $("#title_dMovie").autocomplete({    
						source : '/get_movies/'+document.getElementById("idPerson").value+'?role=director',   
						minLength: 3, 
						focus: function (event, ui) {
					        event.preventDefault();
					        this.value = ui.item.title; 
					    },
				        select: function (event, ui) {
				            event.preventDefault();
				            this.value = ui.item.title;
				            var id = document.getElementById("idPerson").value
				            $.ajax({
				            	   type: "POST",
				            	   url: "/admin/persons/add_dmovie",
				            	   data: {"idPerson":id, "idMovie":ui.item.id},
				            	   dataType : "json",
				            	   success: function(data){ 
				            		     
	 
				            		   if(data != null){ 
				            			    
				            			    var picPath; 
					   				    	if(data.picturePath){picPath="/get_image?path="+data.picturePath}
					   				    	else{picPath="/resources/images/not_found.jpg"} 
					   				    	
				            			   $("#dMovieslist").append('<div class="movie-list-item" id="dMovie'+data.id+'">'
													+'<button type="button" class="btn btn-primary btn-delete" onclick="deleteDMovie('+data.id+')">Удалить</button>'
											+'<a href="/admin/movies/movie/'+data.id+'"'
											+'title="'+data.title+'"><img height="70"'
											+'src="'+picPath+'" width="50" /></a>'
										+'<div class="movie_title">'
											+'<a href="/admin/movies/movie/'+data.id+'">'+data.title+'</a>'
										+'</div>'
									); 
				            			  
				            		   } 
				            	   } 
				            	 });
				            $("#title_dMovie").val("");
				           
				        },   
				        open: function() {  
			                $('.ui-menu')  
			                    .width(262);  
			            } , 
				 		      
					}).autocomplete( "instance" )._renderItem = function( ul, item ) { 
				    	var picPath; 
				    	if(item.picturePath){picPath="/get_image?path="+item.picturePath}
				    	else{picPath="/resources/images/not_found.jpg"}
					      return $( '<li>' )   
						        .append( "<div><img class='movie' src='"+picPath+"' width='30' height='35' /><div class='titleMovie'>" + item.title  +"</div></div>")
					        .appendTo( ul );  
					    };  
					    
					    
					    
					    
					     
//					    Delete actor from current movie
					    deleteAMovie = function(idMovie){
					    	$.ajax({ 
				            	   type: "POST",
				            	   url: "/admin/persons/delete_amovie",
				            	   data: {"idPerson":document.getElementById("idPerson").value, "idMovie":idMovie},
				            	   dataType : "json",

				            	   success: function(data, textStatus, xhr){ 
 
				            			   $("#aMovie"+idMovie).remove();
				             			   
  		   
				            	   }
				            	 });
					    }
					    
					    
					     
//Delete director from current movie			     
					    deleteDMovie = function(idMovie){
					    	$.ajax({ 
				            	   type: "POST",
				            	   url: "/admin/persons/delete_dmovie",
				            	   data: {"idPerson":document.getElementById("idPerson").value, "idMovie":idMovie},
				            	   dataType : "json",

				            	   success: function(data, textStatus, xhr){ 
 
				            			   $("#dMovie"+idMovie).remove();
				             			   
  		   
				            	   }
				            	 });
					    }
					    
					    
					    
			
//					    Upload picture ajax
					    $('#my_form').on('submit', function(e){
					        e.preventDefault();
					        var $that = $(this),
					        formData = new FormData($that.get(0)); // создаем новый экземпляр объекта и передаем ему нашу форму (*)
					        $.ajax({
					          url: '/admin/persons/upload_image/'+document.getElementById("idPerson").value,
					          type: 'POST',
					          contentType: false, 
					          processData: false, 
					          data: formData,
					          success: function(data){
					        	  console.log(data); 
					        
  
					        
					        	  var srcCurrent = $("img.cover").attr('src')
					        	  var src = "/get_image?path="+data;
					        	  if(srcCurrent===src){
					        		  src = src+'&rand='+Math.random();
					        	  }
					        	  $("img.cover").removeAttr('src').attr('src', src);
					        	   					         	  
					        	  [].forEach.call(document.getElementsByClassName('remodal-close'),function(elm){
					        		  elm.click();
					        		 });
					            
					          },
					          error:function(data){
					        	  console.log(data); 

					          } 
					        });
					      });
					     
					   
			    		        
	});   