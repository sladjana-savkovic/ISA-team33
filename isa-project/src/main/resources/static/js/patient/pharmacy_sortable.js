checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function(){
	
	
	$(function() {
  $("#myTable").tablesorter();
});
$(function() {
  $("#myTable").tablesorter({ sortList: [[0,0], [1,0]] });
});
	/*$.ajax({
			type:"POST", 
			url: "/api/pharmacy",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        }, 
			contentType: "application/json",
			success:function(pharmacies){
				pharmacies.forEach(a => appendPharmacy(a));
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#createAppBtn').attr("disabled",false);
				return;
			}
		});
	*/
	
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
