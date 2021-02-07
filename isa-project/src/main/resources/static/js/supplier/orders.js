var orderMap = {};
var drugList = null;
supplierId = 8;
currentOrder = null;

$(document).ready(function () {

	//checkUserRole("ROLE_SUPPLIER");
	
	var dtToday = new Date();
	var month = dtToday.getMonth() + 1;
	var day = dtToday.getDate();
	var year = dtToday.getFullYear();
	if (month < 10)
		month = '0' + month.toString();
	if (day < 10)
		day = '0' + day.toString();
	var minDate = year + '-' + month + '-' + day;
	
	document.getElementById("limitDate").min = minDate;

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
		drugsList = drugsList + '<p>' + order.drugQuantityDTOs[i].drugName + ':  ' + order.drugQuantityDTOs[i].quantity + '</p>';
	}
	let oneOrder = $('<div class="row"><div class="col p-4"><div class="card">' 
		+ '<div class="card-header bg-info text-white">' + (i+1) + '. order  (ID = ' + order.id + ')</div>'
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



function makeOffer(orderId) {
	$('#makeOfferModal').modal('show');
	$('#limitDate').val('');
	$('#totalPrice').val('');
	currentOrder = orderId;
}	
	
	
function sendOffer() {
	
	$('#div_alert').empty();		
	let limitDate = $('#limitDate').val();
	let totalPrice = $('#totalPrice').val();
				
	var newOffer = {
		"limitDate": limitDate,
		"totalPrice": totalPrice,
		"orderId": currentOrder,
		"supplierId": supplierId
	}
	
	if (limitDate == "" || totalPrice == "") {
		let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Enter delivery time and total price!'
			+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
		$('#div_alert').append(alert);
		return;
	}
		
	$.ajax({
		url: "/api/drug-offer",
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(newOffer),
		success: function () {
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful!'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			$('#makeOfferModal').modal('hide');
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


