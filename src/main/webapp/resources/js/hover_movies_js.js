
$(document).ready(function(){
	
	/* Наведение мыши на постер фильма*/
	$(document).on(
			{
			    mouseenter: function() 
			    {
			        $(this).children(".poster").css({"background-color":"white","opacity":"0.8","transform":"scale(1.05)","transition":"1s","z-index":"1"});
					$(this).children(".infoMovie").css({"background-color":"black","opacity":"1","transition":"1s","z-index":"99"});
			    },
			    mouseleave: function()
			    {
			    	 $(this).children(".poster").css({"background-color":"","opacity":"","transform":"scale(1.00)"});
			 		$(this).children(".infoMovie").css({"background-color":"black","opacity":"0.75"});
			    }
			}
			, '.mBlock');
	

});

	
	
	
	
