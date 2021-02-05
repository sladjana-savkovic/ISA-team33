$(document).ready(function () {
		
	getAllCountriesFromDatabase();	
	getAllCitiesFromDatabase();
	
	/*Registrate pharmacy on submit*/
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let name = $('#name').val();
		let countryId = $("#countries option:selected").attr("id");
		let countryName = $("#countries").val();
		let cityZipCode = $("#cities option:selected").attr("id");
		let cityName = $("#cities").val();
		let address = $('#address').val();
		
		var newPharmacy = {
			"name": name,
			"countryId": parseInt(countryId),
			"countryName": countryName,
			"cityId": parseInt(cityZipCode),
			"cityName": cityName,
			"address": address,
		};
		

		$.ajax({
			url: "/api/pharmacy",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(newPharmacy),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful registration!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			},
			error: function (jqXHR) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
					'Unsuccessful registration! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});			
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
				'Error getting country! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
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
					'Error getting cities! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
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

