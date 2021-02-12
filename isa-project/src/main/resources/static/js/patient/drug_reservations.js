checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function(){
	
	getAllReservations();
	
	
})

const getAllReservations = () => {
	$("#apartment-cards").empty();
	$.ajax({
		type:"GET",
		url:"/api/drug-reservation/patient",
		headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
		contentType: "application/json",
		success:function(drugs){
			drugs.forEach(a => appendDrug(a));
		},
		error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
		}
	})
}
const appendDrug = (drug) => {
	let dateLimit = drug.dateLimit.split("T")[0] + ", " + drug.dateLimit.split("T")[1]+ "h";

		let oneElement = `
		<div class="card bg-info mx-5 my-2">
		<div class="row">
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Drug name: ${drug.drugName}</h3>
			</div>
			<div id="${drug.id}" class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="font-weight-bold text-light" style="padding-top:10px;">Limit date: ${dateLimit}</h3>
				
			</div>

			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Pharmacy name: ${drug.pharmacyName}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light"> </h3>
				`
			let endTime = new Date(drug.dateLimit); 
			let d = new Date();
			let s = new Date(); 
			s.setDate(endTime.getDate()); s.setTime(endTime.getTime());
			s.setHours(endTime.getHours()-24);
			if(d < s){
				oneElement = oneElement + `
				<button class="btn btn-success btn-light"  id="modalButton" onclick="cancelReservation('${drug.id}')">Cancel</button>
				`
			} 
		
		oneElement = oneElement + `</div></div></div>`
		$("#apartment-cards").append(oneElement);
	
}


const cancelReservation = reservationId =>{
	$.ajax({
		type:"PUT",
		url: "/api/drug-reservation/"+ reservationId+"/cancel",
		headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
		contentType: "application/json",
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