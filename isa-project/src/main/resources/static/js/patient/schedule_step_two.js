checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function(){
	let startTime = localStorage.getItem('wantedTime');
	$.ajax({
			type:"POST", 
			url: "/api/pharmacy/available/PRICE_ASC",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        }, 
			data: JSON.stringify({ 
				startTime: startTime,
				}),
			contentType: "application/json",
			success:function(pharmacies){
				if(pharmacies.length > 0)
					pharmacies.forEach(a => appendPharmacy(a));
				else
					$('#div_alert').append("There are no available pharmacies or patient already has an appointment at this time.");
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#createAppBtn').attr("disabled",false);
				return;
			}
		});
	
	
	$('form#search').submit(function(event){
		event.preventDefault()
		

		let sorting = $('#sort').val();
		$("#apartment-cards").empty();
		$.ajax({
			type:"POST", 
			url: "/api/pharmacy/available/"+sorting,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        }, 
			data: JSON.stringify({ 
				startTime: startTime,
				}),
			contentType: "application/json",
			success:function(pharmacies){
				if(pharmacies.length > 0)
					pharmacies.forEach(a => appendPharmacy(a));
				else
					$('#div_alert').append("There are no available pharmacies or patient already has an appointment at this time.");
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#createAppBtn').attr("disabled",false);
				return;
			}
		});
	})
	
})


const appendPharmacy = (pharmacy) => {
			let oneElement = `
		<div class="card bg-info mx-5 my-2">
		<div class="row">
			<div id="${pharmacy.id}" class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="font-weight-bold text-light">Name: ${pharmacy.name}</h3>
				
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Place: ${pharmacy.cityName}, ${pharmacy.address}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Grade: ${pharmacy.averageGrade}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Price: ${pharmacy.pharmacistPrice}din</h3>
				`
				
			oneElement = oneElement + `
			<button class="btn btn-success btn-light"  id="modalButton" onclick="choosePharmacy('${pharmacy.id}')">Choose</button>
			`
		
	oneElement = oneElement + `</div></div></div>`
	$("#apartment-cards").append(oneElement);
		
}


const choosePharmacy = pharmacyId =>{
	localStorage.setItem("wantedPharmacyId",pharmacyId);
	let startTime = localStorage.getItem('wantedTime');
	$.ajax({
			type:"POST", 
			url: "/api/doctor/available/PRICE_ASC",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        }, 
			data: JSON.stringify({ 
				startTime: startTime,
				idPharmacy : pharmacyId
				}),
			contentType: "application/json",
			success:function(doctors){
				 localStorage.setItem("doctors",JSON.stringify(doctors));
				location.href='http://localhost:8080/html/patient/schedule_step_three.html';
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


