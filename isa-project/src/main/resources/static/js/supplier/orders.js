var orderMap = {};
var drugList = null;

$(document).ready(function () {

	//checkUserRole("ROLE_SUPPLIER");

	$.ajax({
		url: "/api/order/all",
		type: "GET",
//		headers: {
//			'Authorization': 'Bearer ' + window.localStorage.getItem('token')
//		},
		dataType: 'json',
		processData: false,
		contentType: false,
		success: function (data) {
			if (data.length == 0) {
				let alert = $('<div class="alert alert-info m-4" role="alert">There is no order.</div >')
				$('#loading').remove();
				$('div#view_orders').append(alert);
			}
			else {
				for (let i = 0; i < data.length; i++) {
					addOrderTable(data[i], i);
				}
				$('#loading').remove();
			}
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert"> Error! '
				+ jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#loading').remove();
			$('div#view_orders').prepend(alert);
		}
	});
});


function addOrderTable(order, i) {	
	orderMap[order.id] = order
	drugsList = '';
	for (let i = 0; i < order.drugQuantityDTOs.length; i++) {
		drugsList = drugsList + '<p>' + order.drugQuantityDTOs[i].drugName + ' ' + order.drugQuantityDTOs[i].quantity + '</p>';
	}
	let oneOrder = $('<div class="row"><div class="col p-4"><div class="card">' 
		+ '<div class="card-header bg-info text-white">' + (i+1) + '. order </div>'
		+ '<div class="card-body"><p> Limit date: &nbsp;' + order.limitDate + ' </p></br>'
		+ '<p> Quantity: </p>'
		+ drugsList
		+ '<footer class="blockquote-footer text-info"><cite> Pharmacy: &nbsp;'
		+ order.pharmacyName + '</cite>'
		+ '<button type="button" name="make_offer" class="btn btn-success float-right" id="'
		+ order.id
		+ '" onclick="makeOffer(this.id)">Make offer</button>'
		+ '</footer ></blockquote ></div >'
		+ '<div name="alert_container" class="card-footer bg-transpartent border-top-0" id="a' + order.id + '">'
		+ '</div ></div ></div >');
	$('div#view_orders').append(oneOrder);
}

//*********************************************************************************************************** 

function makeOffer(orderId) {
	$('#makeOfferModal').modal('show');
	
	/* on submit: */
}





function addOffer(orderId) {
	let loading = $('<div class="alert alert-info m-1" role="alert">Publishing...</div >')
	$('#' + orderId).prop("disabled", true);
	$('#a' + orderId).prepend(loading);

	$.ajax({
		type: "POST",
		url: "/api/offer/add",
		contentType: 'application/json',
		//headers: {
		//	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		//},
		success: function () {
	        let alert = $('<div name="alert_msg" class="alert alert-success m-1" role="alert">Order ....</div >')
			$('#' + orderId).remove();
			$('#a' + orderId).empty();
			$('#a' + orderId).prepend(alert);
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error! ' + jqXHR.responseJSON
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#a' + orderId).empty();
			$('#' + orderId).prop("disabled", false);
			$('#a' + orderId).prepend(alert);
		}
	});
}