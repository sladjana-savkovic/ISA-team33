var doctorId = 1;
$(document).ready(function () {
	
	$('#startDate').prop("min",new Date().toISOString().split("T")[0]);
	$('#endDate').prop("min",new Date().toISOString().split("T")[0]);
	
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + doctorId + "/pharmacies",
		contentType: "application/json",
		success:function(doctorPharmacies){
			for (let dp of doctorPharmacies) {
				addDoctorPharmacy(dp);
			}
		},
		error:function(){
			console.log('error getting doctor pharmacies');
		}
	});
	
	$('#create_request').submit(function(event){
		event.preventDefault();
		
		let startDate = $('#startDate').val();
		let endDate = $('#endDate').val();
		
		$.ajax({
				type:"POST", 
				url: "/api/vacation",
				data: JSON.stringify({ 
					doctorId: doctorId, 
					startDate: startDate, 
					endDate: endDate,
					pharmacyId: $('#doctorPharmacies').val()}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully created vacation request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error during creating vacation request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
	});
	
});

function addDoctorPharmacy(doctorPharmacy){
	let option = $('<option value="' + doctorPharmacy.pharmacyId +'">' + doctorPharmacy.pharmacyName + '</option>');
	$('#doctorPharmacies').append(option);
};