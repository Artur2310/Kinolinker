$(document).ready(function(){

/* Открыть модальное окно*/
	openModalWindow = function(id){ 
		        	
		$.ajax({
			url : "/get_movie/"+id,
			dataType : "json",
			method : "get",
			success : function(data) {
			
                      if(data!=null){
                    	
                    	  
                    	  var genres ='';
							$.each(data.genre, function() {
								genres = genres+this.title+' ';
							});
	                    	  

							 var countries ='';
								$.each(data.country, function() {
									countries = countries+this.name+' ';
								});
	
							date = new Date(data.releaseDate).format("dd mmmm yyyy");
											
                           
							                 
                           

                    	  var picPath;
							if (data.picturePath) {
								picPath = "/get_image?path=" + data.picturePath;
							} else {
								picPath = "/resources/images/not_found.jpg";
							}
							
							var imdb = '';
							if(data.imdb){
								imdb = '<li><a href='+data.imdbLink+' rel="rel nofollow" target="_blank">IMDB:</a> '+data.imdb+'</li>';
							}
						

                  		$(".md-trigger").click();
                  		
                  		$(".md-content").append('<div class="movie_info">'+
                  		'<div class="titleBlock"><h3 id="title">'+data.title+'</h3>'+
                  		
                  		'</div>'+
                  			'<div class="main-pic">'+
                  				'<img alt="'+data.title+'" class="cover" src='+picPath+'>'+                				

                  			'</div>'+
       			
                  			'<ul>'+
                  				'<li><strong>Жанр:</strong> '+
                  				'<p class="genre">'+genres+'</p>'+
                  					
                  				'</li><li><strong>Дата релиза:</strong> <span class="datetime">'+
                  						date+
                  				'</span></li>'+
                  				'<li><strong>Страна:</strong> '+
                  				'<p class="country">'+countries+'</p>'+
                  					'</li>'+
                  				imdb+
                  			'</ul>'+

                  			'<div class="description" style="overflow: hidden;">'+
                  				'<h2>Описание</h2>'+
                  				'<span itemprop="description">'+data.description+'<p></p></span>'+
                  			'</div>'+
                  		'<div id="cast">'+               				
                  				
                  			
                  				'</div>');
                      
                    }                    
                      getActors(data.id);
                      getDirectors(data.id);                      
                    
            },
			error : function(e) {			
								
				console.log("ERROR: ", e);

			}

		});		
	}
	
	/* Закрыть модальное окно*/
	closeModal = function(){
	console.log("Button click");
			$(".md-modal").removeClass("md-show");
			$(".movie_info").remove();
    }
	
	getActors = function(id){
          
		var actors="";
		
          $.ajax({
  			url : "/get_actors/"+id,
  			dataType : "json",
  			method : "get",
  			success : function(data) {
  				
  				if(data!=null){
  					
  			  data.sort(comparePerson);
  			  actors = '<p><strong>Актёры: </strong></p>';
  	          actors = actors+'<div id="actors-list" class="persons-list">';
  	          $.each(data, function() {
  	        	 
  	          	var picActor;
  	          	if (this.picturePath) {
  						picActor = "/get_image?path=" + this.picturePath;
  					} else {
  						picActor = "/resources/images/not_found.jpg";
  					}
  	          
  	          	actors = actors+
  	          	'<div class="person-item" id='+this.id+' onclick="clickPerson(\''+this.name+'\')">'+
  					'<img height="70" src='+picActor+' width="50">'+
  					'<div class="personname-item">'
  						+this.name+
  					'</div>'+
  				'</div>';

  				});   
  	          actors = actors + '</div></div>';
  	          $("#cast").append(actors);
  				}
              },
  			error : function(e) {		
  				
  				console.log("ERROR: ", e);

  			}

  		});
        
	}
	
	getDirectors = function(id){
        
		var directors="";
		
          $.ajax({
  			url : "/get_directors/"+id,
  			dataType : "json",
  			method : "get",
  			success : function(data) {
  				
  				if(data!=null){
  					
  					data.sort(comparePerson);
				directors = '<p><strong>Режисёр: </strong></p>';
  				 directors = directors+'<div id="directors-list" class="persons-list">'
                     $.each(data, function() {
                     	var picDirector;
                     	if (this.picturePath) {
								picDirector = "/get_image?path=" + this.picturePath;
							} else {
								picDirector = "/resources/images/not_found.jpg";
							}
                     	
							directors = directors+
           				'<div class="person-item" id='+this.id+' onclick="clickPerson(\''+this.name+'\')">'+
							'<img height="70" src='+picDirector+' width="50">'+
							'<div class="personname-item">'+
								this.name+
							'</div>'+
						'</div>';
			
						});
  	        
  				 directors = directors+'</div>';
  				$("#cast").prepend(directors);
  				}
  				},       
  	         
  			error : function(e) {		
  				
  				console.log("ERROR: ", e);

  			}

  		});
        
	}
	
	function comparePerson(a, b) {
		if (a.rating < b.rating) return 1;
		  if (a.rating > b.rating) return -1;
		  
		}
	
	clickPerson = function(name){
		funcSelect='';
        	if($('#formPersons').length==true){
        		closeModal();
        		$(".personSelect").remove();
        		$(".personInput").val("");
        		$("#allEntriesMovie").remove();
        		$("#personOne").val(name);
        		$("#formPersons").submit();
        	}else{
        		document.location.href = "/persons/?name="+name;
        	}
		
		
	}
	
	
	
	/* Наведение мыши на кинодеятелся*/
	$(document).on(
			{
			    mouseenter: function() 
			    {
			        $(this).children("img").css({"transform":"scale(1.1)","transition":"1s","z-index":"1"});
					
			    },
			    mouseleave: function()
			    {
			    	 $(this).children("img").css({"transform":"scale(1.00)"});
			 		
			    }
			}
			, '.person-item');
	
});