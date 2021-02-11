$(document).ready(function () {
	
    token = localStorage.getItem("token");

    if (token == null) {
		localStorage.clear();
		
		if(window.location.href.indexOf("drug_search.html") > -1){			
			document.body.appendChild(document.createElement('script')).src='../../js/navbars/unauthenticated_user.js';
		}
        else if (window.location.href.indexOf("login.html") ==  -1)
            window.location.href = "../user/login.html";

        return;
    }
    else
    {
		if(getRoleFromToken() == "ROLE_PHARMACIST"){
			document.body.appendChild(document.createElement('script')).src='../../js/navbars/pharmacist.js';
			$('#searchPredefinedAppointments').attr('hidden',true);
			$('#freeAppText').attr('hidden',true);
			
		}else if(getRoleFromToken() == "ROLE_DERMATOLOGIST"){
			 document.body.appendChild(document.createElement('script')).src='../../js/navbars/dermatologist.js';
				
		}else if(getRoleFromToken() == "ROLE_SUPPLIER"){
			 document.body.appendChild(document.createElement('script')).src='../../js/navbars/supplier.js';
		
		}else if(getRoleFromToken() == "ROLE_SYSTEMADMIN"){
			 document.body.appendChild(document.createElement('script')).src='../../js/navbars/system_admin.js';
		
		}else if(getRoleFromToken() == "ROLE_PHARMACYADMIN"){
			 document.body.appendChild(document.createElement('script')).src='../../js/navbars/pharmacy_administrator.js';
		}
	
		
		if(!isActiveFromToken() && getRoleFromToken() != "ROLE_PATIENT"){
			$('body').prepend($(
				'<div class="modal fade" id="changePassModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"'
		        + 'aria-hidden="true" data-backdrop="static">'
		        + '<div class="modal-dialog modal-notify modal-info modal-dialog-centered modal-lg" role="document">'
		        + '<div class="modal-content">'
		        + '    <div class="modal-header">'
		        + '         <p class="heading lead">Change password to activate Your profile</p>'
		        + '     </div>'
		        + '     <div class="modal-body">'
		        + '    	<form style="color: #757575;" id="changePassForm">'
		        + '    	<div class="row w-100 p-3 h-50">'
				+ '		 	<div class="col">'
				+ '	 		<label class="text-secondary mb-0">Old password:</label><br>'
				+ ' 	</div>'
				+ '<div class="col">'
				+ '<input type="password" class="form-control" id="oldPass" required>'
				+ '	</div>'
				+ '</div>'
		                	
				+ '<div class="row w-100 p-3 h-50">'
				+ '<div class="col">'
				+ '		<label class="text-secondary mb-0">Enter the new password:</label><br>'
				+ ' 	</div>'
				+ '	<div class="col">'
				+ '	<input type="password" class="form-control" id="newPass" required>'
				+ '	</div>'
				+ ' </div>'
							 
				+ '<div class="row w-100 p-3 h-50">'
				+ '	<div class="col">'
				+ '	<label class="text-secondary mb-0">Re-enter the new password:</label><br>'
				+ '	</div>'
				+ '	<div class="col">'
				+ '		<input type="password" class="form-control" id="newPassRepeat" required>'
		  		+ '	</div>'
				+ '</div>'
						 
				+ ' <button class="btn btn-outline-info btn-rounded btn-block my-4 waves-effect" type="submit" id="changePassBtn">Save</button>'
				+ '   </form>'
		        + '    </div>'
		        + '   </div>'
		      	+ '  </div>'
		   		+ ' </div>'
			 ));
			$('#changePassModal').modal('toggle');
			$('#changePassModal').modal('show');
		}
    }

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

});


function decodeToken(token) {
   var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function getRoleFromToken() {
	try{
		return decodeToken(localStorage.getItem("token")).role;
	}
    catch(err){
		window.location.href = "../user/login.html";
	}
}

function getUserIdFromToken(){
	try{
		return decodeToken(localStorage.getItem("token")).userId;
	}
    catch(err){
		window.location.href = "../user/login.html";
	}
}

function getUserAccountIdFromToken(){
	try{
		return decodeToken(localStorage.getItem("token")).userAccountId;
	}
    catch(err){
		window.location.href = "../user/login.html";
	}
}


function isActiveFromToken(){
	try{
		return decodeToken(localStorage.getItem("token")).isActive;
	}
	catch(err){
		window.location.href = "../user/login.html";
	}
}

function checkUserRole(trueRole) {
    var role = getRoleFromToken();
    if (role != trueRole) {
		if( (role == "ROLE_PHARMACIST" || role == "ROLE_DERMATOLOGIST") && trueRole == "ROLE_DERMATOLOGIST_PHARMACIST"){
			return;
		}
        else if (role == "ROLE_PHARMACIST" || role == "ROLE_DERMATOLOGIST") {
            window.location.href = "../doctor/calendar.html";
        }
		else if(role == "ROLE_PATIENT"){
			window.location.href = "../patient/patient_profile.html";
		}
		else if(role == "ROLE_SYSTEMADMIN"){
			window.location.href = "../system_admin/user_registration.html";
		}
		else if(role == "ROLE_PHARMACYADMIN"){
			window.location.href = "../pharmacy_administrator/pharmacy_profile.html";
		}
		else if(role == "ROLE_SUPPLIER"){
			window.location.href = "../supplier/supplier_profile.html";
		}
	}
}

function logOut() {
    localStorage.clear();
    window.location.href = "../user/login.html";
}