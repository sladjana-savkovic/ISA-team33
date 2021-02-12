checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();

$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/country",
		contentType: "application/json",
		success:function(countries){
			$('#countrySelect').empty();
			for (let c of countries) {
				addCountries(c);
			}
			getCities($("#countrySelect option:selected").val());
		},
		error:function(){
			console.log('error getting patients countries');
		}
	});
	 
	$.ajax({
		type:"GET",
		url:"/api/drug/all",
		contentType:"application/json",
		success:function(drugs){
			for(let drug of drugs){
				addDrug(drug, patientId)
			}
		},
		error:function(){
			alert('error getting drugs')
		}
	})
	
	$.ajax({
		type:"GET", 
		url: "/api/patient/" + patientId,
		headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
		contentType: "application/json",
		success:function(patient){
			addPatientInfo(patient);
		},
		error:function(){
			console.log('error getting patient');
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
			var amenities = []
			var $boxes = $('input[name=amenities]:checked');
			$boxes.each(function(){
				amenities.push({"id":$(this).val()})
			})
			$.ajax({
				type:"PUT", 
				url: "/api/patient",
				headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	      		  },
				contentType: "application/json",
				data: JSON.stringify({ 
					id:patientId,
					name: $('#name').val(), 
					surname: $('#surname').val(), 
					telephone: $('#phone').val(),
					dateOfBirth: $('#dateOfBirth').val(),
					email: $('#email').val(),
					address: $('#address').val(),
					allergies: amenities,
					cityId: $("#citySelect option:selected").val()}),
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed profile informations.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error changing profile informations.'
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
		
		$('#change_pass').attr("disabled",true);
		
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
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed password.Please, log in again.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				localStorage.clear();
				window.setTimeout(function(){location.href = "../user/login.html";},1000)
				return;
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + JSON.parse(xhr.responseText).message
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#change_pass').attr("disabled",false);
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
	$('input[name=amenities]').attr("disabled",false);
	
	changeInputFiledsStatus(true);
	changeSelectOptionsStatus(false);
};


function addPatientInfo(patient){
	$('#name').val(patient.name);
	$('#surname').val(patient.surname);
	$('#dateOfBirth').val(patient.dateOfBirth);
	$('#phone').val(patient.telephone);
	$('#email').val(patient.email);
	$('#address').val(patient.address);
	
	changeInputFiledsStatus(false);
	changeSelectOptionsStatus(true);
	$("#countryInput").val(patient.countryName);
	$("#cityInput").val(patient.cityName);	
}

function changeInputFiledsStatus(hidden){
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
		contentType: "application/json",
		success:function(cities){
			$('#citySelect').empty();
			for (let c of cities) {
				addCities(c);
			}
		},
		error:function(){
			console.log('error getting cities');
		}
	});
}

function addDrug(drug, patientId){
	let div = $('<div class="form-check"></div>')
	let input = $('<input disabled type="checkbox" class="form-check-input" id="' + drug.id + '" name="amenities" value="' + drug.id + '" >')
	let label =  $('<label class="form-check-label" for="' + drug.id + '">' + drug.name + '</label>')
	$.ajax({
		type:"GET", 
		url: "/api/patient/" + patientId + "/allergy/" + drug.id,
		headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
		contentType: "application/json",
		success:function(check){
				if(check == true){
					input.attr('checked', 'checked');
				}
		},
		error:function(){
			console.log('error getting patient');
		}
	});
	div.append(input).append(label)
	$("#checkboxes").append(div)
}
