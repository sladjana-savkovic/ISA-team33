var pharmacyAdminId = 6;
var pharmacyId;
$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
		pharmacyId = admin.pharmacyId;
	
		$.ajax({
				type:"GET", 
				url: "/api/doctor/" + pharmacyId + "/not-pharmacy",
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
		error:function(){
			console.log('error getting pharmacy administrator');
		}
	});

});



function addDoctorInComboBox(doctor) {
	let derma_option = $('<option id="' + doctor.id + '" value = "' + doctor.name + '">' + doctor.name + ' ' + doctor.surname + '</option>');
	$('select#derma').append(derma_option);
};


