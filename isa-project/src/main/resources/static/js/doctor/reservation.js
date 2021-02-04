var doctorId = appConfig.doctorId;
var reservationId;
$(document).ready(function () {
	
	$('#searchReservations').submit(function(event){
		event.preventDefault();
		
		$('#close_alert').click();
		let reservationId = $('#search_field').val();
		
		$.ajax({
			type:"GET", 
			url: "/api/drug-reservation/" + reservationId + "/doctor/" + doctorId,
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
			contentType: "application/json",
			success:function(patientId){
				
				let message = "You have successfully taken the reserved drug.";
					
				$.ajax({
					url: "/api/email/" + patientId,
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify({ 
						 subject: "Confirmation of receipt of the reservation",
						 message: message}),
					success: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
						+'Successfully confirmed reservation.'
						+ '<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						$('#searchResult').attr("hidden",true);
						$('#confirmReservation').attr("disabled",false);
						return;
					},
					error: function () {
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
						+'Successfully confirmed reservation, but an error occurred while sending an email.'
						+ '<button type="button" id="close_alert" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						$('#searchResult').attr("hidden",true);
						$('#confirmReservation').attr("disabled",false);
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