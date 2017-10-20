$(document).ready(function() {
  
//			     Autocomplite for actor input
			    $("#nameActor").autocomplete({    
					source : '/get_persons/'+document.getElementById("idMovie").value+'?role=actor',   
					minLength: 3, 
					focus: function (event, ui) {
				        event.preventDefault();
				        this.value = ui.item.name; 
				    },
			        select: function (event, ui) {
			            event.preventDefault();
			            this.value = ui.item.name;
			            var id = document.getElementById("idMovie").value
			            $.ajax({
			            	   type: "POST",
			            	   url: "/admin/movies/add_actor",
			            	   data: {"idMovie":id, "idPerson":ui.item.id},
			            	   dataType : "json",
			            	   success: function(data){ 
			            		     
 
			            		   if(data != null){ 
			            			    
			            			    var picPath; 
				   				    	if(data.picturePath){picPath="/get_image?path="+data.picturePath}
				   				    	else{picPath="/resources/images/not_found.jpg"} 
				   				    	
			            			   $("#castlist").append('<div class="person-list-item" id="actor'+data.id+'">'
			   							+'<button type="button" class="btn btn-primary btn-delete" onclick="deleteActor('+data.id+')">Удалить</button>'

										+'<a href="/admin/persons/person/'+data.id+'"'
										+'title="'+data.name+'"><img height="70"'
										+'src="'+picPath+'" width="50" /></a>'
									+'<div class="personname">'
										+'<a href="/admin/persons/person/'+data.id+'">'+data.name+'</a>'
									+'</div>'
								); 
			            			  
			            		   } 
			            	   } 
			            	 });
			            $("#nameActor").val("");
			           
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
					        .append( "<div><img class='person' src='"+picPath+"' width='30' height='35' /><div class='namePerson'>" + item.name  +"</div></div>")
				        .appendTo( ul );  
				    };  
				    
				    
//				    Autocomplite for director input
				    $("#nameDirector").autocomplete({    
						source : '/get_persons/'+document.getElementById("idMovie").value+'?role=director',   
						minLength: 3,
						focus: function (event, ui) {
					        event.preventDefault();
					        this.value = ui.item.name; 
					    },
				        select: function (event, ui) {
				            event.preventDefault();
				            
				            this.value = ui.item.name;
				            var id = document.getElementById("idMovie").value
				            $.ajax({ 
				            	   type: "POST",
				            	   url: "/admin/movies/add_director",
				            	   data: {"idMovie":id, "idPerson":ui.item.id},
				            	   dataType : "json",
				            	   success: function(data){  	            		 

				            		   if(data != null){
				            			   var picPath; 
				   				    	if(data.picturePath){picPath="/get_image?path="+data.picturePath}
				   				    	else{picPath="/resources/images/not_found.jpg"} 
				            			   
				            			   $("#directorslist").append('<div class="person-list-item" id=director'+data.id+'>'
										+'<button type="button" class="btn btn-primary btn-delete" onclick="deleteDirector('+data.id+')">Удалить</button>'

				            					   +'<a href="/admin/persons/person/'+data.id+'"'
											+'title="'+data.name+'"><img height="70"'
											+'src="'+picPath+'" width="50" /></a>'
										+'<div class="personname">' 
											+'<a href="/admin/persons/person/'+data.id+'">'+data.name+'</a>'
										+'</div>'
									);
				            		   }
				            	   }
				            	 }); 
				            $("#nameDirector").val("");
				           
				        }, 
				        open: function() {  
			                // After menu has been opened, set width to 100px
			                $('.ui-menu')  
			                    .width(262);  
			            } ,  
						       
					}).autocomplete( "instance" )._renderItem = function( ul, item ) {  
				    	var picPath; 
				    	if(item.picturePath){picPath="/get_image?path="+item.picturePath}
				    	else{picPath="/resources/images/not_found.jpg"} 
					      return $( '<li>' )   
					        .append( "<div><img class='person' src='"+picPath+"' width='30' height='35' /><div class='namePerson'>" + item.name  +"</div></div>")
					        .appendTo( ul );  
					    };  
					    
					    
					    
					    
					     
//					    Delete actor from current movie
					    deleteActor = function(idPerson){
					    	$.ajax({ 
				            	   type: "POST",
				            	   url: "/admin/movies/delete_actor",
				            	   data: {"idMovie":document.getElementById("idMovie").value, "idPerson":idPerson},
				            	   dataType : "json",

				            	   success: function(data, textStatus, xhr){ 
 
				            			   $("#actor"+idPerson).remove();
				             			   
  		   
				            	   }
				            	 });
					    }
					    
					    
					    
//Delete director from current movie			     
					    deleteDirector = function(idPerson){

					    	$.ajax({ 
				            	   type: "POST",
				            	   url: "/admin/movies/delete_director",
				            	   data: {"idMovie":document.getElementById("idMovie").value, "idPerson":idPerson},
				            	   dataType : 'text',
				            	    

				            	   success: function(data, textStatus, xhr){   
	         
				            			   $("#director"+idPerson).remove();			   
				             		   
				            	   },
				            	         	   
				            	   error: function(data){
				            	   }
				            	 });
					    	
					    }
					    
					    
					    
			
//					    Upload picture ajax
					    $('#my_form').on('submit', function(e){
					        e.preventDefault();
					        var $that = $(this),
					        formData = new FormData($that.get(0)); // создаем новый экземпляр объекта и передаем ему нашу форму (*)
					        $.ajax({
					          url: '/admin/movies/upload_image/'+document.getElementById("idMovie").value,
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