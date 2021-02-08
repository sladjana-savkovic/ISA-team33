checkUserRole("ROLE_PHARMACYADMIN");
var pharmacyAdminId = getUserIdFromToken();
var pharmacyId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			$.ajax({
				type:"GET", 
				url: "/api/working-time/" + pharmacyId + "/pharmacy",
				headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       			 },
				contentType: "application/json",
				success:function(working_times){
					
					for(i = 0; i < working_times.length; i++){
						addWorkingTime(working_times[i]);
					}			
					
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}	
				});
				
				$.ajax({
				type:"GET", 
				url: "/api/doctor/" + pharmacyId + "/pharmacy/without/working-time",
				headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(dd){
					
					for(i = 0; i < dd.length; i++){
						addDoctorInComboBox(dd[i]);
					}			
					
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
				});
				
				$('#working_time').submit(function(event){
					event.preventDefault();
		
				let doctorId = $('#doctors option:selected').val();
				let start = $('#startTime').val();
				let end = $('#endTime').val();
				
		
				if (end <= start) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start time must be less than the end time.'
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
				
				$.ajax({
				type:"POST", 
				url: "/api/working-time",
				headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				data: JSON.stringify({ 
					doctorId: doctorId, 
					pharmacyId: pharmacyId, 
					startTime: start,
					endTime: end}),
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully created working-time.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert_action').append(alert);
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
			
		},
		error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
		}
	});
	
});



function addWorkingTime(workingTime){
	 let row = $('<tr><td>'+ workingTime.doctorName +'</td><td>' + workingTime.doctorSurname + '</td><td>' + workingTime.typeOfDoctor + '</td><td>'  + workingTime.startTime + '</td><td>'  + workingTime.endTime  + '</td></tr>');	
	$('#times').append(row);
};

function addDoctorInComboBox(doctor) {
	let doctor_option = $('<option id="' + doctor.id + '" value = "' + doctor.id + '">' + doctor.name + ' ' + doctor.surname + ' '+ doctor.typeOfDoctor + '</option>');
	$('select#doctors').append(doctor_option);
};

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}
