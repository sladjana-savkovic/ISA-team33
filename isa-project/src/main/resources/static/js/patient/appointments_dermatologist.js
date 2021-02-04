var patientId = 3;
$(document).ready(function(){
	
	getAllAppointments();
	
	
})

const getAllAppointments = () => {
	$("#apartment-cards").empty();
	$.ajax({
		type:"GET",
		url:"/api/appointment/patient/"+patientId+"/dermatologists/scheduled",
		contentType:"application/json",
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
}
const appendAppointment = (apartment) => {
	let startTime = apartment.startTime.split("T")[0] + ", " + apartment.startTime.split("T")[1]+ "h";
	/*let name;let surname;let grade;
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + apartment.idDoctor,
		contentType: "application/json",
		success:function(doctor){
			name = doctor.name;
			surname = doctor.surname;
			grade = doctor.averageGrade;*/
			/* dole
			 <!--	<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Doctor: ${name} ${surname}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Doctor's grade: ${grade}</h3>
			</div>  -->
		*/
			let oneElement = `
		<div class="card bg-info mx-5 my-2">
		<div class="row">
			<div id="${apartment.id}" class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="font-weight-bold text-light">Start time: ${startTime}</h3>
				
			</div>
		
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Price: ${apartment.price}$</h3>
				`
				
			oneElement = oneElement + `
			<button class="btn btn-success btn-light"  id="modalButton" onclick="cancelAppointment('${apartment.id}')">Cancel</button>
			`
		
	oneElement = oneElement + `</div></div></div>`
	$("#apartment-cards").append(oneElement);
	/*	},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	}); */
	
}


const cancelAppointment = apartmentId =>{
	$.ajax({
		type:"PUT",
		url: "/api/appointment/"+ apartmentId+"/cancel",
		success:function(){
			window.location.reload()
		},
		error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
		}
	})
}