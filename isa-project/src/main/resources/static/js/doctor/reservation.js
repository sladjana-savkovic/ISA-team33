checkUserRole("ROLE_PHARMACIST");
var doctorId = getUserIdFromToken();
var reservationId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$('#searchReservations').submit(function(event){
		event.preventDefault();
		
		$('#close_alert').click();
		let reservationId = $('#search_field').val();
		
		if(!Number.isInteger(parseInt(reservationId))){
			$('#searchResult').attr("hidden",true);
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Wrong reservation number format.'
			+'<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		$.ajax({
			type:"GET", 
			url: "/api/drug-reservation/" + reservationId + "/doctor/" + doctorId,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(reservation){
				$('#search_field').val('');
				
				if(reservation != null){
					$('#searchResult').attr("hidden",false);
					addReservationInfo(reservation);
				}else{
					$('#searchResult').attr("hidden",true);
				}
			},
			error:function(xhr){
				$('#searchResult').attr("hidden",true);
				$('#search_field').val('');
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
	
	$('#confirmReservation').click(function(){
		
		$('#confirmReservation').attr("disabled",true);
		
		$.ajax({
			type:"PUT", 
			url: "/api/drug-reservation/" + reservationId,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(patientId){
				
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
				+'Successfully confirmed reservation.'
				+ '<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					$('#searchResult').attr("hidden",true);
					$('#confirmReservation').attr("disabled",false);
				},1000)
				
				let message = "You have successfully taken the reserved drug (reservation identification number =  " + reservationId + ").";
					
				$.ajax({
					url: "/api/email/" + patientId,
					type: 'POST',
					headers: {
			            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
			        },
					contentType: 'application/json',
					data: JSON.stringify({ 
						 subject: "Confirmation of receipt of the reservation",
						 message: message}),
					success: function () {
						console.log("Successfully sent an email.");
						return;
					},
					error: function () {
						console.log("Unsuccessfully sent an email.");
						return;
					}
				});	
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#confirmReservation').attr("disabled",false);
				return;
			}
		});
		
	});
	
});

function addReservationInfo(reservation){
	let status = "Not issued";
	if(reservation.isDone){
		status = "Issued";
	}
	
	$('#patient').text(reservation.patientName + ' ' + reservation.patientSurname);
	$('#pharmacy').text(reservation.pharmacyName);
	$('#drug').text(reservation.drugName);
	$('#dateLimit').text(reservation.dateLimit.split('T')[0] + ' ' + reservation.dateLimit.split('T')[1]);
	$('#status').text(status);
	
	reservationId = reservation.id;
};

function clearLocalStorage(){
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
	localStorage.removeItem("appointmentId");
}