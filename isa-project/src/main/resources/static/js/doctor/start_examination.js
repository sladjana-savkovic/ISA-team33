var doctorId = 1;
var appointmentId = 5;
var appointment = null;
$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/appointment/" + appointmentId,
		contentType: "application/json",
		success:function(appointment_for_examination){
			appointment = appointment_for_examination;
			
			$.ajax({
				type:"GET", 
				url: "/api/drug/pharmacy/" + appointment.pharmacyId,
				contentType: "application/json",
				success:function(drugs){
					for (let d of drugs) {
						addDrug(d);
					}
				},
				error:function(){
					console.log('error getting drugs in pharmacy');
				}
			});
		},
		error:function(){
			console.log('error getting doctor appointment');
		}
	});
	
	
	$('#confirm').click(function(){
		
		if($('#start').is(':checked')){
			$('#select_option').attr('hidden',true);
			$('#examination_report').attr('hidden',false);
			fillInBasicInfo();
		}
		else if($('#end').is(':checked')){
			
			$.ajax({
				type:"PUT", 
				url: "/api/appointment/" + appointment.appointmentId + "/unperformed",
				contentType: "application/json",
				success:function(){
					$.ajax({
						type:"PUT", 
						url: "/api/patient/" + appointment.patientId + "/increase-penalty",
						contentType: "application/json",
						success:function(){
							let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
								+'Successfully changed appointment status and increased penalties for patient.'
								+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							location.href = "calendar.html";
							return;
						},
						error:function(){
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error increasing penalties for patient.'
								+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
					});
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error changing appointment status to unperformed .'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		}
	
	});
});


function fillInBasicInfo(){
	$('#patientName').text(appointment.patientName);
			$('#patientSurname').text(appointment.patientSurname);
			$('#doctorName').text(appointment.doctorName);
			$('#doctorSurname').text(appointment.doctorSurname);
			$('#startTime').text(appointment.startTime.split("T")[0] + " " + appointment.startTime.split("T")[1]);
			$('#endTime').text(appointment.endTime.split("T")[0] + " " + appointment.endTime.split("T")[1]);
			$('#pharmacyName').text(appointment.pharmacyName);
			$('#price').text(appointment.price);
};

function addDrug(drug){
	let option = $('<option value="' + drug.id +'">' + drug.name + '</option>');
	$('#drugs').append(option);
};