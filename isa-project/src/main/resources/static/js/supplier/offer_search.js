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
        url: '/api/drug-offer/all/' + supplierID + '/supplier',
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
                    addOfferRow(offers[i], i);
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


function addOfferRow(offer, i) {
		
	offerMap[offer.id] = offer;
				
    let divElement = $(
        '<div class="row">'
        + '<div class="col mb-4">'
        + '<div class="card">'
        + '<div class="card-header bg-info text-white">'
        + '<h5 class="card-title mb-0"> Offer (ID = ' + offer.drugOfferDTO.id + ')</h5></div>'
        + '<div class="card-body p-3">'
        + '<label class="text-secondary mb-0">Total price: &nbsp; </label>'
        + '<label>' + offer.drugOfferDTO.totalPrice + '</label><br>'
        + '<label class="text-secondary mb-0">Status: &nbsp; </label>'
        + '<label>' + offer.drugOfferDTO.status + '</label><br>'
        + '<label class="text-secondary mb-0">Delivery time: &nbsp; </label>'
        + '<label>' + offer.drugOfferDTO.limitDate + '</label><br><br>'

		+ ' <blockquote class="blockquote mb-0"><p><b>Order</b><br> Limit date: &nbsp; '
	    + offer.pharmacyOrderDTO.limitDate + '</p>' 

        + '</div> </div> </div> </div>'

    );
    $('div#div_offers').append(divElement);
}


