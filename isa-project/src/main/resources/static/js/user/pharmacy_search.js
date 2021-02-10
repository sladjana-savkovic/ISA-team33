//ova stranica je dostupna svim korisnicima

var pharmacyList = null;

$(document).ready(function () {

	getAllPharmacies();
	
});


function searchPharmacy() {	
	$('div#div_drugs').empty();	
	let name = $('#name').val();
	let gradeMin = $('#gradeMin').val();
	let gradeMax = $('#gradeMax').val();
	let city = $('#city').val();
	let address = $('#address').val();
	let priceMin = $('#priceMin').val();
	let priceMax = $('#priceMax').val();
	searchDTO = {
		"name": name,
		"gradeMin": gradeMin,
		"gradeMax": gradeMax,
		"cityName": city,
		"address": address,
		"priceMin": priceMin,
		"priceMax": priceMax,
		"pharmacyDTOs": pharmacyList
	}		
    $.ajax({
        url: "/api/pharmacy/search",
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(searchDTO),
        success: function (pharmacies) {
           if (pharmacies.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No pharmacies found. </div>';
                $("#loading").hide();
                $("#div_drugs").prepend(alert);
            }
            else {
                for (let i = 0; i < pharmacies.length; i++) {
                    addPharmacyRow(pharmacies[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });			
}


function getAllPharmacies() {		
    $.ajax({
        url: '/api/pharmacy',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (pharmacies) {
			pharmacyList = pharmacies;
            if (pharmacies.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No pharmacies found. </div>';
                $("#loading").hide();
                $("#div_drugs").prepend(alert);
            }
            else {
                for (let i = 0; i < pharmacies.length; i++) {
                    addPharmacyRow(pharmacies[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });	
}


function addPharmacyRow(pharmacy) {
			
    let divElement = $(
        '<div class="row">'
        + '<div class="col mb-4">'
        + '<div class="card">'
        + '<div class="card-header bg-info text-white">'
        + '<h5 class="card-title mb-0">' + pharmacy.name + '</h5></div>'
        + '<div class="card-body p-3">'
        + '<label class="text-secondary mb-0">City: &nbsp; </label>'
        + '<label>' + pharmacy.cityName + '</label><br>'
        + '<label class="text-secondary mb-0">Address: &nbsp; </label>'
        + '<label>' + pharmacy.address + '</label><br>'
 		+ '<label class="text-secondary mb-0">Grade: &nbsp; </label>'
        + '<label>' + pharmacy.averageGrade + '</label><br>'
        + '<label class="text-secondary mb-0">Pharmacist price: &nbsp; </label>'
        + '<label>' + pharmacy.pharmacistPrice + '</label><br>'
        
        + '</div></div></div></div>'
    );
    $('div#div_drugs').append(divElement);
}
