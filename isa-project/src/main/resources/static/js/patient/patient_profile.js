var patientId = 3;

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
		url:"/api/drug",
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
		
		var amenities = []
		var $boxes = $('input[name=amenities]:checked');
		$boxes.each(function(){
			amenities.push({"id":$(this).val()})
		})
		
		
		$('#edit_profile').submit(function(event){
			event.preventDefault();
			
			$.ajax({
				type:"PUT", 
				url: "/api/patient",
				data: JSON.stringify({ 
					id:patientId,
					name: $('#name').val(), 
					surname: $('#surname').val(), 
					telephone: $('#phone').val(),
					dateOfBirth: $('#dateOfBirth').val(),
					email: $('#email').val(),
					password: $('#password').val(),
					address: $('#address').val(),
					allergies: amenities,
					cityId: $("#citySelect option:selected").val()}),
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
});

function enableFields(){
	$('#name').attr("disabled",false);
	$('#surname').attr("disabled",false);
	$('#dateOfBirth').attr("disabled",false);
	$('#phone').attr("disabled",false);
	//$('#email').attr("disabled",false);
	$('#password').attr("disabled",false);
	$('#address').attr("disabled",false);
	$('#country').attr("disabled",false);
	$('#city').attr("disabled",false);
	
	changeInputFiledsStatus(true);
	changeSelectOptionsStatus(false);
};


function addPatientInfo(patient){
	$('#name').val(patient.name);
	$('#surname').val(patient.surname);
	$('#dateOfBirth').val(patient.dateOfBirth);
	$('#phone').val(patient.telephone);
	$('#email').val(patient.email);
	$('#password').val(patient.password);
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
	let input = $('<input type="checkbox" class="form-check-input" id="' + drug.id + '" name="amenities" value="' + drug.id + '" >')
	let label =  $('<label class="form-check-label" for="' + drug.id + '">' + drug.name + '</label>')
	$.ajax({
		type:"GET", 
		url: "/api/patient/" + patientId + "/allergy/" + drug.id,
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
