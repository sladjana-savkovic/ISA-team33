checkUserRole("ROLE_DERMATOLOGIST_PHARMACIST");
var doctorId = getUserIdFromToken();

$(document).ready(function () {
	
	clearLocalStorage();
	
	$('#startDate').prop("min",new Date().toISOString().split("T")[0]);
	$('#endDate').prop("min",new Date().toISOString().split("T")[0]);
	
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + doctorId + "/pharmacies",
		headers: {
	        'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	    },
		contentType: "application/json",
		success:function(doctorPharmacies){
			for (let dp of doctorPharmacies) {
				addDoctorPharmacy(dp);
			}
		},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	});
	
	
	$('#create_request').submit(function(event){
		event.preventDefault();
		
		let startDate = $('#startDate').val();
		let endDate = $('#endDate').val();
		
		var from_start =startDate.split("-");
		var from_end = endDate.split("-");
		var ds = new Date(from_start[0], from_start[1] - 1, from_start[2]);
		var de = new Date(from_end[0], from_end[1] - 1, from_end[2]);
		
		if(de < ds){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start date must be less than the end date.'
		    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		$.ajax({
				type:"POST", 
				url: "/api/vacation",
				headers: {
			        'Authorization': 'Bearer ' + window.localStorage.getItem('token')
			    },
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
					window.setTimeout(function(){location.reload();},1000)
					return;
					
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
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

function clearLocalStorage(){
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
	localStorage.removeItem("appointmentId");
}