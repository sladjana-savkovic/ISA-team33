checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function(){
	var doctors = JSON.parse(localStorage.getItem('doctors'));
	doctors.forEach(a => appendDoctor(a));
	/*
	
	$('form#search').submit(function(event){
		event.preventDefault()
		

		let sorting = $('#sort').val();
		$("#apartment-cards").empty();
		$.ajax({
			type:"GET",
			url:"/api/appointment/pharmacy/"+pharmacyId+"/created/"+sorting,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(appointments){
				appointments.forEach(a => appendAppointment(a));
			},
			error: function (jqXHR) {
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
							 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
			}
		})
	})
	*/
})


const appendDoctor = (doctor) => {
			let oneElement = `
		<div class="card bg-info mx-5 my-2">
		<div class="row">
			<div id="${doctor.id}" class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="font-weight-bold text-light">Doctor: ${doctor.name} ${doctor.surname}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Grade: ${doctor.averageGrade}</h3>
		
				`
				
			oneElement = oneElement + `
			<button class="btn btn-success btn-light"  id="modalButton" onclick="schedule('${doctor.id}')">Schedule</button>
			`
		
	oneElement = oneElement + `</div></div></div>`
	$("#apartment-cards").append(oneElement);
		
}


const schedule = doctorId =>{
	let startTime = localStorage.getItem('wantedTime');
	let pharmacyId = localStorage.getItem('wantedPharmacyId');
	let endTime = new Date(startTime);
	endTime.setMinutes(endTime.getMinutes()+90);
	let newEnd = endTime.toISOString().split(".");
	endTime = newEnd[0];
	$.ajax({
			type:"POST", 
			url: "/api/appointment/schedule",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        }, 
			data: JSON.stringify({ 
				startTime: startTime,
				idPharmacy : pharmacyId,
				idDoctor : doctorId,
				endTime : endTime,
				idPatient : patientId
				}),
			contentType: "application/json",
			success:function(){
				 localStorage.removeItem("wantedTime");
				 localStorage.removeItem("wantedPharmacyId");
				 localStorage.removeItem("doctors");
				 localStorage.removeItem("pharmacies");
				 $('#'+doctorId).parent().parent().remove();
				 location.href='http://localhost:8080/html/patient/schedule_step_one.html';
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#createAppBtn').attr("disabled",false);
				return;
			}
		});
}
