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
	

	/*Registrate patient on submit*/
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
		
		if (password.toString().length < 1) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The password invalid.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		if (!$.isNumeric(telephone) || telephone.toString().length > 20) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Phone number must have less than 20 digits.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		
		var newPatient = {
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
		
		if ($("form#registration").hasClass("unsuccessful")) {
			return;
		}
		else {
			$("form#registration").removeClass("unsuccessful");
			
			$.ajax({
				url: "/api/patient",
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(newPatient),
				success: function () {
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful registration!'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});			
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
				'ERROR! ' + jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
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
					'ERROR! ' + jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});

	});
};


function addCountryInComboBox(country) {
	let country_option = $('<option id="' + country.id + '" value="' + country.name + '">' + country.name + '</option>');
	$('select#countries').append(country_option);
};


function addCityInComboBox(city) {
	let city_option = $('<option id="' + city.id + '" value = "' + city.name + '">' + city.name + '</option>');
	$('select#cities').append(city_option);
};

