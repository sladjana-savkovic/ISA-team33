//ova stranica je dostupna dobavljacu

supplierID = 8;
offerMap = {};
offersList = null;

$(document).ready(function () {

	getAllOffersBySupplier();
	
});


function searchOffer() {	
	$('div#div_offers').empty();	
	let status = $("#offer_status option:selected").val();	

	searchDTO = {
		"status": status,
		"drugDTOs": offersList
	}		
    $.ajax({
        //url: "/api/drug/search",
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(searchDTO),
        success: function (offers) {
            if (offers.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No offers found. </div>';
                $("#loading").hide();
                $("#div_offers").prepend(alert);
            }
            else {
                for (let i = 0; i < offers.length; i++) {
                    addOfferRow(offers[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_offers").prepend(alert);
        }
    });			
}


function getAllOffersBySupplier() {		
    $.ajax({
        //url: '/api/drug',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (offers) {
			offersList = offers;
            if (offers.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No offers found. </div>';
                $("#loading").hide();
                $("#div_offers").prepend(alert);
            }
            else {
                for (let i = 0; i < offers.length; i++) {
                    addOfferRow(offers[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_offers").prepend(alert);
        }
    });	
}


function addOfferRow(offer) {
		
	offerMap[offer.id] = offer;
	
	button = '<div class="card-footer">'
		+ '<button type = "button" class="btn btn-outline-primary float-left"'
		+ 'id="' + offer.id + '" onclick="showSpecification(this.id)"'
		+ '>Specification</button >'
		+ '<button type = "button" class="btn btn-outline-primary float-left"'
		+ 'id="' + offer.id + '" onclick="showSubstituteDrugs(this.id)"'
		+ '>Substitute</button >'		
		+ '</div >'; 
			
    let divElement = $(
        '<div class="row">'
        + '<div class="col mb-4">'
        + '<div class="card">'
        + '<div class="card-header bg-info text-white">'
        + '<h5 class="card-title mb-0">' + offer.name + '</h5></div>'
        + '<div class="card-body p-3">'
        + '<label class="text-secondary mb-0">Grade: &nbsp; </label>'
        + '<label>' + offer.grade + '</label><br>'
        + '<label class="text-secondary mb-0">Type of drug: &nbsp; </label>'
        + '<label>' + offer.typeOfDrug + '</label><br>'
        + '</div>' + button + '</div></div></div>'
    );
    $('div#div_drugs').append(divElement);
}


function showSubstituteDrugs(offerId) {
	substituteList = '';				
    $.ajax({
        url: '/api/drug/' + offerId + '/substitute',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (substituteDrugs) {	
            if (substituteDrugs.length == 0) {	
				substituteList = 'No substitute drugs found.';
				let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
					+ '<tr><td><b><strong> ' + substituteList + '</strong></b> </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');
            }
            else {
				substituteList = substituteDrugs[0].name;
                for (let i = 1; i < substituteDrugs.length; i++) {
					substituteList = substituteList + ', ' + substituteDrugs[i].name;
                }
				let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
					+ '<tr><td><b><strong>Substitute drugs: </strong></b>' + substituteList + ' </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');	
            }
        },
        error: function (jqXHR) {
			let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:300px;">'
					+ '<tr><td> Error! ' + jqXHR.responseJSON + ' </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');
        }
    });			
}


function showSpecification(drugId) {	
	ingredientsList = offerMap[drugId].ingredients[0].name;
	for (let i = 1; i < offerMap[drugId].ingredients.length; i++) {		
		ingredientsList = ingredientsList + ', ' + offerMap[drugId].ingredients[i].name;
	}
		
	let table1 = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
		+ '<tr><td><b><strong>Contraindication: </strong></b>' + offerMap[drugId].contraindication + ' </td></tr>'
		+ '<tr><td><b><strong>Daily dose: </strong></b>' + offerMap[drugId].dailyDose + '</td></tr>'
		+ '<tr><td><b><strong>Ingredients: </strong></b>' + ingredientsList + ' </td></tr>'
		+ '</table>';

	$('#report_content').empty();
	$('#report_content').append(table1);
	$('#bottomModalSuccess').modal('show');
};



