checkUserRole("ROLE_DERMATOLOGIST_PHARMACIST");
var doctorId = getUserIdFromToken();
var doctorAccountId = getUserAccountIdFromToken();
var doctorObj = null;
$(document).ready(function () {
	
	//clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/country",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(countries){
			$('#countrySelect').empty();
			for (let c of countries) {
				addCountries(c);
			}
			getCities($("#countrySelect option:selected").val());
		},
		error:function(){
			console.log('error getting doctor countries');
		}
	});
	
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + doctorId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(doctor){
			doctorObj = doctor;
			addDoctorInfo(doctor);
		},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	});
	
	$("#countrySelect" ).change(function() {
	  	getCities($("#countrySelect option:selected").val());
	});
	
	$('#edit_profile').submit(function(event){
		event.preventDefault();
		enableFields();
		$('#change').text("Save");
		
		$('#edit_profile').submit(function(event){
			event.preventDefault();
			
			$.ajax({
				type:"PUT", 
				url: "/api/doctor",
				headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		        },
				data: JSON.stringify({ 
					id:doctorId,
					name: $('#name').val(), 
					surname: $('#surname').val(), 
					dateOfBirth: $('#dateOfBirth').val(),
					phoneNumber: $('#phone').val(),
					address: $('#address').val(),
					cityId: $("#citySelect option:selected").val()}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed profile informations.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					window.setTimeout(function(){location.reload()},1000);
					return;
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		});
	});
	
	$('#edit_password').submit(function(event){
		event.preventDefault();
		
		let oldPass = $('#oldPass').val();
		let newPass = $('#newPass').val();
		let newPassRepeat = $('#newPassRepeat').val();
		
		if(newPass != newPassRepeat){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Passwords don\'t match.'
			+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		$.ajax({
			type:"PUT", 
			url: "/api/user/" + doctorAccountId + "/password/" + oldPass+ "/" + newPass,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(){
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed password.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){location.reload()},1000)
				return;
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
});

function enableFields(){
	$('#name').attr("disabled",false);
	$('#surname').attr("disabled",false);
	$('#dateOfBirth').attr("disabled",false);
	$('#phone').attr("disabled",false);
	$('#address').attr("disabled",false);
	$('#country').attr("disabled",false);
	$('#city').attr("disabled",false);
	
	changeInputFiledsStatus(true);
	changeSelectOptionsStatus(false);
};


function addDoctorInfo(doctor){
	$('#name').val(doctor.name);
	$('#surname').val(doctor.surname);
	$('#dateOfBirth').val(doctor.dateOfBirth);
	$('#phone').val(doctor.phoneNumber);
	$('#email').text(doctor.email);
	$('#address').val(doctor.address);
	
	changeInputFieldsStatus(false);
	changeSelectOptionsStatus(true);
	$("#countryInput").val(doctor.countryName);
	$("#cityInput").val(doctor.cityName);
	
}

function changeInputFieldsStatus(hidden){
	if(hidden){
		$("#countryInput").attr("hidden",true);
		$("#cityInput").attr("hidden",true);
	}
	else{
		$("#countryInput").attr("hidden",false);
		$("#cityInput").attr("hidden",false);
	}
}

function changeSelectOptionsStatus(hidden){
	if(hidden){
		$("#countrySelect").attr("hidden",true);
		$("#citySelect").attr("hidden",true);
	}
	else{
		$("#countrySelect").attr("hidden",false);
		$("#citySelect").attr("hidden",false);
	}
}

function addCountries(country){
	let option = $('<option value="' + country.id +'">' + country.name + '</option>');
	$('#countrySelect').append(option);
}

function addCities(city){
	let option = $('<option value="' + city.id +'">' + city.name + '</option>');
	$('#citySelect').append(option);
}

function getCities(countryId){
	$.ajax({
		type:"GET", 
		url: "/api/city/country/" + countryId,
		headers: {
	        'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	    },
		contentType: "application/json",
		success:function(cities){
			$('#citySelect').empty();
			for (let c of cities) {
				addCities(c);
			}
		},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	});
}

function clearLocalStorage(){
	localStorage.removeItem("appointmentId");
}