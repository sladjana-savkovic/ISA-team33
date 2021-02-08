//ova stranica je dostupna dobavljacu

supplierID = 8;
offersList = null;

$(document).ready(function () {

	getAllOffersBySupplier();
	
});


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
                    addOfferCard(offers[i]);
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


function addOfferCard(offer) {
	
	let drugQuantityList = offer.orderAndQuantityDTO.drugQuantityDTOs;
	let drugQuantityListText = '';
		
	for (let i = 0; i < drugQuantityList.length; i++) {
		let element = drugQuantityList[i].drugName + ': ' + drugQuantityList[i].quantity + '<br>';
		drugQuantityListText = drugQuantityListText +  element;
	}
							
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
        + '<label>' + offer.drugOfferDTO.limitDate + '</label><br><br><br>'

		+ ' <blockquote class="blockquote mb-0 text-muted"><b>Order:</b><br><p> Limit date: &nbsp; '
	    + offer.orderAndQuantityDTO.limitDate + '</p>' 
		+ '<p>  Quantity: <br>' + drugQuantityListText + '</p>'		
		+ '<footer class="blockquote-footer text-muted"><cite> Pharmacy: &nbsp;'
		+ offer.orderAndQuantityDTO.pharmacyName + '</cite></footer>'		
        + '</div> </div> </div> </div>'
    );
    $('div#div_offers').append(divElement);
}


function searchOffer() {	
	$('div#div_offers').empty();	
	let status = $("#offer_status option:selected").val();	

	searchDTO = {
		"status": status,
		"offerDTOs": offersList
	}		
    $.ajax({
        url: "/api/drug-offer/search",
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
                    addOfferCard(offers[i]);
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

