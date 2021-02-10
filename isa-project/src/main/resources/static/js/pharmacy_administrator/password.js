$(document).ready(function () {	
	
	if(!isActiveFromToken()){
		$('#changePassModal').modal('toggle');
		$('#changePassModal').modal('show');
		
		$('#changePassForm').submit(function(event){
		event.preventDefault();
		
		let newPass = $('#newPass').val();
		let newPassRepeat = $('#newPassRepeat').val();
		let oldPass = $('#oldPass').val();
		
		if(newPass != newPassRepeat){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Passwords don\'t match.'
			+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		$('#changePassBtn').attr("disabled",true);
				
		$.ajax({
			type:"POST", 
			url: "/auth/change-password",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			data: JSON.stringify({ 
				oldPassword:oldPass,
				newPassword: newPass}),
			contentType: "application/json",
			success:function(){
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed password and activated account.Please, log in again.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				localStorage.clear();
				window.setTimeout(function(){location.href = "../user/login.html";},1300)
				return;
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + JSON.parse(xhr.responseText).message
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#changePassBtn').attr("disabled",false);
				return;
			}
		});	
		
		});
	}

	
});