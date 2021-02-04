$(document).ready(function () {	

	/*Login patient on submit*/
	$('form#logging_in').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let username = $('#email').val();
		let password = $('#password').val();

		var userInfoDTO = {
			"username": username,
			"password": password
		};

		if ((email == "") || (password == "")) {
			return;
		}
		else {
			$("form#logging_in").removeClass("unsuccessful");
			
			$.ajax({
				url: "/auth/login",
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(userInfoDTO),
				success: function (token) {
					localStorage.setItem('token', token.accessToken);
					
					alert('token ' + token.accessToken);
					
				},
				error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert"> ERROR! '
						+ jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
			
		}
	});
});
