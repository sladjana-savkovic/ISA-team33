var doctorId = 1;
$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + doctorId,
		contentType: "application/json",
		success:function(doctor){
			addDoctorInfo(doctor);
		},
		error:function(){
			console.log('error getting doctor');
		}
	});
	
	$("#country" ).change(function() {
	  	changeCity($('#country').val());
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
				data: JSON.stringify({ 
					id:doctorId,
					name: $('#name').val(), 
					surname: $('#surname').val(), 
					dateOfBirth: $('#dateOfBirth').val(),
					email: $('#email').val(),
					password: $('#password').val(),
					address: $('#address').val(),
					cityId: $('#city').val()}),
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed profile informations.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error during changing profile informations.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		});
	});
});

function enableFields(){
	$('#name').attr("disabled",false);
	$('#surname').attr("disabled",false);
	$('#dateOfBirth').attr("disabled",false);
	$('#phone').attr("disabled",false);
	$('#email').attr("disabled",false);
	$('#password').attr("disabled",false);
	$('#address').attr("disabled",false);
	$('#country').attr("disabled",false);
	$('#city').attr("disabled",false);
};


function addDoctorInfo(doctor){
	$('#name').val(doctor.name);
	$('#surname').val(doctor.surname);
	$('#dateOfBirth').val(doctor.dateOfBirth);
	$('#phone').val("061758620"); //ispraviti nakon dodavanja telefona
	$('#email').val(doctor.email);
	$('#password').val(doctor.password);
	$('#address').val(doctor.address);
	
	$.ajax({
		type:"GET", 
		url: "/api/country",
		contentType: "application/json",
		success:function(countries){
			$('#country').empty();
			for (let c of countries) {
				addCountries(c);
			}
			
			$("#country").val(doctor.countryId);
			changeCity(doctor.countryId);
			$("#city").val(doctor.cityId);
		},
		error:function(){
			console.log('error getting doctor countries');
		}
	});
	
}

function addCountries(country){
	let option = $('<option value="' + country.id +'">' + country.name + '</option>');
	$('#country').append(option);
}

function addCities(city){
	let option = $('<option value="' + city.id +'">' + city.name + '</option>');
	$('#city').append(option);
}

function changeCity(countryId){
	$.ajax({
		type:"GET", 
		url: "/api/city/country/" + countryId,
		contentType: "application/json",
		success:function(cities){
			$('#city').empty();
			for (let c of cities) {
				addCities(c);
			}
		},
		error:function(){
			console.log('error getting doctor cities');
		}
	});
}