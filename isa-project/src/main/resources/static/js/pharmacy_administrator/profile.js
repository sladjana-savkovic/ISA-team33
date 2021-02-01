var pharmacyAdminId = 6;
var pharmacyId;
$(document).ready(function () {

	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
			pharmacyId=admin.pharmacyId;
			addAdminInfo(admin);
		
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
				url: "/api/pharmacy-admin",
				data: JSON.stringify({ 
					id: pharmacyAdminId,
					name: $('#name').val(), 
					surname: $('#surname').val(), 
					dateOfBirth: $('#dateOfBirth').val(),
					email: $('#email').val(),
					password: $('#password').val(),
					address: $('#address').val(),
					pharmacyId : pharmacyId,
					telephone : $('#phone').val(),
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
			new_pass1 = $('#new_password').val();
			new_pass2 =  $('#new_password_repeat').val();
			
			if(new_pass1 != new_pass2){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Passwords do not match.'
			    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
			
			$.ajax({
				type:"PUT", 
				url: "/api/pharmacy-admin/" + pharmacyAdminId + "/password/" + new_pass1 ,
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully changed password.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error changing password.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		});
	
	},
		error:function(){
			console.log('error getting pharmacy admin');
		}
	});
});

function enableFields(){
	$('#name').attr("disabled",false);
	$('#surname').attr("disabled",false);
	$('#phone').attr("disabled",false);
	$('#email').attr("disabled",false);
	$('#address').attr("disabled",false);
	$('#country').attr("disabled",false);
	$('#city').attr("disabled",false);
};


function addAdminInfo(admin){
	$('#name').val(admin.name);
	$('#surname').val(admin.surname);
	$('#dateOfBirth').val(admin.dateOfBirth);
	$('#phone').val(admin.telephone);
	$('#email').val(admin.email);
	$('#password').val(admin.password);
	$('#address').val(admin.address);
	
	$.ajax({
		type:"GET", 
		url: "/api/country",
		contentType: "application/json",
		success:function(countries){
			$('#country').empty();
			for (let c of countries) {
				addCountries(c);
			}
			
			$("#country").val(admin.countryId);
			changeCity(admin.countryId);
			$("#city").val(admin.cityId);
		},
		error:function(){
			console.log('error getting admin countries');
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
			console.log('error getting admin cities');
		}
	});
}