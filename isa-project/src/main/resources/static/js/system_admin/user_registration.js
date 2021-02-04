$(document).ready(function () {
	
	var dtToday = new Date();
	var month = dtToday.getMonth() + 1;
	var day = dtToday.getDate();
	var year = dtToday.getFullYear();
	if (month < 10)
		month = '0' + month.toString();
	if (day < 10)
		day = '0' + day.toString();
	var maxDate = year + '-' + month + '-' + day;
	$('#dateOfBirth').attr('max', maxDate);
	
	getAllCountriesFromDatabase();	
	getAllCitiesFromDatabase();	
	getAllPharmacies();
	

	/*Registrate user on submit*/
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let name = $('#name').val();
		let surname = $('#surname').val();
		let telephone = $('#phone').val();
		let countryId = $("#countries option:selected").attr("id");
		let countryName = $("#countries").val();
		let cityZipCode = $("#cities option:selected").attr("id");
		let cityName = $("#cities").val();
		let address = $('#address').val();
		let email = $('#email').val();
		let dateOfBirth = $('#dateOfBirth').val();
		let password = $('#password').val();
		let passwordRepeat = $('#rpt_password').val();
		
		if (password != passwordRepeat) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Password and confirm password don\'t match.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		else if (!$.isNumeric(telephone) || telephone.toString().length > 20) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Phone number must have less than 20 digits.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		
		else {				
			var roleId = $("#role option:selected").attr("id");
			if (parseInt(roleId) == 2) {
				alert("admin_pharmacy")
				/*
				$.ajax({
					url: "/api/patient",
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(newUser),
					success: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful pharmacy admin registration!'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					},
					error: function (jqXHR) {
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
							 'Unsuccessful pharmacy admin registration! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
				});		
				*/							
			}
			
			else if (parseInt(roleId) == 1) {
				
				var newDermatologist = {
					"name": name,
					"surname": surname,
					"phoneNumber": telephone,
					"dateOfBirth": dateOfBirth,
					"countryId": parseInt(countryId),
					"countryName": countryName,
					"cityId": parseInt(cityZipCode),
					"cityName": cityName,
					"address": address,
					"email": email,
					"password": password,
				};
				
				$.ajax({
					url: "/api/doctor/add/dermatologist",
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(newDermatologist),
					success: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful dermatologist registration!'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					},
					error: function (jqXHR) {
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
							 'Unsuccessful dermatologist registration! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
				});				
			}
			
			else if (parseInt(roleId) == 3) {
																
				var newSystemAdmin = {
					"name": name,
					"surname": surname,
					"telephone": telephone,
					"dateOfBirth": dateOfBirth,
					"countryId": parseInt(countryId),
					"countryName": countryName,
					"cityId": parseInt(cityZipCode),
					"cityName": cityName,
					"address": address,
					"email": email,
					"password": password,
				};
					
				$.ajax({
					url: "/api/system-admin/add",
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(newSystemAdmin),
					success: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful system admin registration!'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					},
					error: function (jqXHR) {
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
							 'Unsuccessful system admin registration! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
				});												
			}
			
			else if (parseInt(roleId) == 4) {
								
				var newSupplier = {
					"name": name,
					"surname": surname,
					"telephone": telephone,
					"dateOfBirth": dateOfBirth,
					"countryId": parseInt(countryId),
					"countryName": countryName,
					"cityId": parseInt(cityZipCode),
					"cityName": cityName,
					"address": address,
					"email": email,
					"password": password,
				};
				
				$.ajax({
					url: "/api/supplier/add",
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(newSupplier),
					success: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful supplier registration!'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					},
					error: function (jqXHR) {
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
							 'Unsuccessful supplier registration! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
				});								
			}	
		}
	});
});



function getAllCountriesFromDatabase() {	
	$.ajax({
		url: "/api/country",
		type: 'GET',
		contentType: "application/json",
		success: function (countries) {			
			for (let i = 0; i < countries.length; i++) {
				addCountryInComboBox(countries[i]);
			}
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
				'ERROR! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});	
};


function getAllPharmacies() {
	$('select#role').on('change', function () {

		var id = $(this).children(":selected").attr("id");
		
		if (parseInt(id) == 2) {
			$.ajax({
				url: "/api/pharmacy",
				type: 'GET',
				dataType: 'json',
				processData: false,
				contentType: false,
				success: function (pharmacies) {
					
					$('div#divPharmacy').empty();
					$('div#divInfo').append('<div id="divPharmacy" class="form-row"><div class="form-group col"> ' + 
                                    '<label for="pharmacy" class="text-secondary">Pharmacy *</label> ' +                                
                                    '<select id="pharmacy" class="custom-select" required> ' +
                                    '</select>' + 
                                    '</div></div>');									
				
					$('select#pharmacy').append('<option value="" disabled selected hidden>Choose pharmacy</option>');
					for (let i = 0; i < pharmacies.length; i++) {
						addPharmacyInComboBox(pharmacies[i]);
					}
				},
				error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						'Error getting pharmacies! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		}
		else {
			$('div#divPharmacy').empty();
		}	
	});	
};


function getAllCitiesFromDatabase() {	
	$('select#countries').on('change', function () {

		var id = $(this).children(":selected").attr("id");

		$.ajax({
			url: "/api/city/country/" + id,
			type: 'GET',
			dataType: 'json',
			processData: false,
			contentType: false,
			success: function (cities) {
				
				$('select#cities').empty();
				$('select#cities').append('<option value="" disabled selected hidden>Choose city</option>');
				for (let i = 0; i < cities.length; i++) {
					addCityInComboBox(cities[i]);
				}
			},
			error: function (jqXHR) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
					'ERROR! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
};


function addPharmacyInComboBox(pharmacy) {
	let pharmacy_option = $('<option id="' + pharmacy.id + '" value="' + pharmacy.name + '">' + pharmacy.name + '</option>');
	$('select#pharmacy').append(pharmacy_option);
};


function addCountryInComboBox(country) {
	let country_option = $('<option id="' + country.id + '" value="' + country.name + '">' + country.name + '</option>');
	$('select#countries').append(country_option);
};


function addCityInComboBox(city) {
	let city_option = $('<option id="' + city.id + '" value = "' + city.name + '">' + city.name + '</option>');
	$('select#cities').append(city_option);
};

