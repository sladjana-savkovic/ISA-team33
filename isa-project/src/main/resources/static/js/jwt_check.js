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
		}
		
        return;
    }

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


function isEnabledFromToken(){
	try{
		return decodeToken(localStorage.getItem("token")).isEnabled;
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