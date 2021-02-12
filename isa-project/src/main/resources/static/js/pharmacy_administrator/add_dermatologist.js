checkUserRole("ROLE_PHARMACYADMIN");
var pharmacyAdminId = getUserIdFromToken();
var pharmacyId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(admin){
		pharmacyId = admin.pharmacyId;
	
		$.ajax({
				type:"GET", 
				url: "/api/doctor/" + pharmacyId + "/not-pharmacy",
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(dd){
					
					for(i = 0; i < dd.length; i++){
						if(dd[i].typeOfDoctor == 'Dermatologist'){
							addDoctorInComboBox(dd[i]);
						}
					}			
					
				},
				error:function(){
					console.log('error getting doctors');
				}
				});
	
	
	$('form#add').submit(function (event) {

		event.preventDefault();
		
		let doctorStr = $('#derma option:selected').attr("id");
		let doctor = parseInt(doctorStr);
		
		$.ajax({
				type:"PUT", 
				url: "/api/doctor/" + doctor + "/pharmacy/" + pharmacyId + "/add-dermatologist",
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(){
					location.reload()
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully added new dermatologist to the pharmacy.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error adding new dermatologist.'
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



function addDoctorInComboBox(doctor) {
	let derma_option = $('<option id="' + doctor.id + '" value = "' + doctor.name + '">' + doctor.name + ' ' + doctor.surname + '</option>');
	$('select#derma').append(derma_option);
};

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}
