var pharmacyAdminId = 6;
var pharmacyId;
$(document).ready(function () {
	
	$('#startDate').prop("min",new Date().toISOString().split("T")[0]);
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			$.ajax({
				type:"GET", 
				url: "/api/doctor/" + pharmacyId + "/pharmacy",
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
				
		$('#free').submit(function(event){
		event.preventDefault();
		
		let doctorStr = $('#doctors option:selected').attr("id");
		let doctor = parseInt(doctorStr);
		let date = $('#startDate').val();
		let startTime = $('#startTime').val();
		let endTime = $('#endTime').val();
		let priceStr = $('#price').val();
		let price = parseFloat(priceStr);
		
		var startDate = '';
		startDate = date + "T" + startTime + ":00";
		var endDate = '';
		endDate = date + "T" + endTime + ":00";
		
		var from_start =startTime.split(":");
		var from_end = endTime.split(":");
		
		var end_hour = parseInt(from_end[0]);
		var start_hour = parseInt(from_start[0]);
		var start_min = parseInt(from_start[1]);
		var end_min = parseInt(from_end[1]);
		
			if(end_hour < start_hour){
				let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start time must be less than the end time.'
			    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(a);
				return;
			}else if(start_hour == end_hour && end_min < start_min){
				let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start time must be less than the end time.'
			    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(a);
				return;
			}
		
			$.ajax({
				type:"POST", 
				url: "/api/appointment/create",
				data: JSON.stringify({ 
					startTime: startDate, 
					endTime: endDate,
					idDoctor: doctor,
					price: price,
					idPharmacy: pharmacyId}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully created free appointment.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error creating free appointment.'
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
	let doctor_option = $('<option id="' + doctor.id + '" value = "' + doctor.name + '">' + doctor.name + ' ' + doctor.surname + '</option>');
	$('select#doctors').append(doctor_option);
};

