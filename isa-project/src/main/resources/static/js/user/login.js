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
					redirectUser(token.accessToken);
				},
				error: function (xhr) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
			
		}
	});
});

function redirectUser(token){
	let role = decodeToken(token).role;
	if(role == "ROLE_PHARMACIST" || role == "ROLE_DERMATOLOGIST"){
    	 window.location.href = "../doctor/calendar.html";
	}
	if(role == "ROLE_PHARMACYADMIN"){
    	 window.location.href = "../pharmacy_administrator/pharmacy_profile.html";
	}
	if(role == "ROLE_SUPPLIER"){
    	 window.location.href = "../supplier/offer_search.html";
	}
	if(role == "ROLE_SYSTEMADMIN"){
    	 window.location.href = "../system_admin/adding_drug.html";
	}
	if(role == "ROLE_PATIENT"){
    	 window.location.href = "../patient/pharmacy_profile.html";
	}
}

function decodeToken(token) {
   var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
