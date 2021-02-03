try {
  var patientId = window.location.href.split("?")[1].split("&")[0].split("=")[1];
  var pharmacyId = window.location.href.split("?")[1].split("&")[1].split("=")[1];
}
catch(err) {
   window.location.href = "calendar.html";
}

var doctorId = appConfig.doctorId;
free_appointments = [];
$(document).ready(function () {
	
	$('#appDate').prop("min",new Date().toISOString().split("T")[0]);
	
	$.ajax({
		type:"GET", 
		url: "/api/appointment/pharmacy/" + pharmacyId + "/doctor/" + doctorId,
		contentType: "application/json",
		success:function(appointments){
			$('#body_appointments').empty();
			free_appointments = appointments;
			for (let a of appointments){
				addAppointment(a);
			}
		},
		error:function(){
			console.log('error getting appointments');
		}
	});
	
	$(document).on("click", "#appointments tbody tr", function() {
		
		let appointmentId = $(this).attr('id');
		
		$('#scheduleAppModal').modal('toggle');
		$('#scheduleAppModal').modal('show');
		
		$('#schedule').click(function(){
			
			$('#schedule').attr("disabled",true);
			
			$.ajax({
				type:"PUT", 
				url: "/api/appointment/" + appointmentId + "/patient/" + patientId + "/schedule",
				contentType: "application/json",
				success:function(){
					
					let message = "You have a new examination scheduled. See a list of future appointments.";
					
					$.ajax({
						url: "/api/email/" + patientId,
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify({ 
							 subject: "Scheduling new appointment",
							 message: message}),
						success: function () {
							$('#close_btn').click();
							let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully appointment scheduling.'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							window.setTimeout(function(){location.href = "calendar.html"},500)
							return;
						},
						error: function () {
							$('#close_btn').click();
							let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">' 
							+ 'Successfully appointment scheduling, but an error occurred while sending an email'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							window.setTimeout(function(){location.href = "calendar.html"},500)
							return;
						}
					});	
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					$('#schedule').attr("disabled",false);
					return;
				}
			});
		});
    });

	$('#searchApp').submit(function(event){
		event.preventDefault();
		let startTime = $('#searchDate').val();

		$.ajax({
			type:"POST", 
			url: "/api/appointment/search/" + startTime,
			data: JSON.stringify(free_appointments),
			contentType: "application/json",
			success:function(searchResult){
				$('#body_appointments').empty();
				for (let a of searchResult){
					addAppointment(a);
				}
			},
			error:function(){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error searching appointments.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
	
	
	$('#createApp').submit(function(event){
		event.preventDefault();
		let appDate = $('#appDate').val();
		let appStartTime = $('#appStartTime').val();
		let appEndTime = $('#appEndTime').val();
		
		let startTime = appDate + "T" + appStartTime + ":00";
		let endTime = appDate + "T" + appEndTime + ":00";
		
		var from_start =appStartTime.split(":");
		var from_end = appEndTime.split(":");
		
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
			url: "/api/appointment/schedule",
			data: JSON.stringify({ 
				startTime: startTime,
				endTime : endTime,
				idDoctor: doctorId,
				idPharmacy: pharmacyId,
				idPatient: patientId,
				price: 800}),
			contentType: "application/json",
			success:function(){
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully appointment scheduling.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){location.href = "calendar.html"},1000)
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


function addAppointment(a){
	
	let startTime = a.startTime.split("T")[0] + " " + a.startTime.split("T")[1];
	let endTime = a.endTime.split("T")[0] + " " + a.endTime.split("T")[1];
	
	let row = $('<tr id="' + a.id + '"><td>'+ startTime +'</td><td>' + endTime + '</td><td>' + a.price + '</td></tr>');	
	$('#appointments').append(row);
};