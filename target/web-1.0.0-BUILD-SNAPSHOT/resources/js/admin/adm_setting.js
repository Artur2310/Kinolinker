$(document).ready(function() {
	
	changePassword = function(){
		if($("#passwordOne").val()==$("#passwordTwo").val()){
			
			var msg   = $('#formChangePassword').serialize();
			$.ajax({
         	   type: "POST",
         	   url: "/admin/change_password",
         	   data: msg,
         	   dataType : "json",
         	   success: function(data){ 
     
         		   closeModal(); 
         	   },
         	  error : function(e) {		
    				
    				

    			}
         	 });
		}
	}
	
	/* Закрыть модальное окно*/
	closeModal = function(){
	
			$(".md-modal").removeClass("md-show");
			$(".movie_info").remove();
    }
	
});