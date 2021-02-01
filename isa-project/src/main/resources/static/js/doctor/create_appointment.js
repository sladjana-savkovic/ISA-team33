var patientId = window.location.href.split("?")[1].split("&")[0].split("=")[1];
var pharmacyId = window.location.href.split("?")[1].split("&")[1].split("=")[1];
var doctorId = appConfig.doctorId;
free_appointments = [];
$(document).ready(function () {
	
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
			
			$.ajax({
				type:"PUT", 
				url: "/api/appointment/" + appointmentId + "/patient/" + patientId + "/schedule",
				contentType: "application/json",
				success:function(){
					$('#close_btn').click();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully appointment scheduling.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					window.setTimeout(function(){location.reload()},1000)
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
	
	
	
});


function addAppointment(a){
	
	let startTime = a.startTime.split("T")[0] + " " + a.startTime.split("T")[1];
	let endTime = a.endTime.split("T")[0] + " " + a.endTime.split("T")[1];
	
	let row = $('<tr id="' + a.id + '"><td>'+ startTime +'</td><td>' + endTime + '</td><td>' + a.price + '</td></tr>');	
	$('#appointments').append(row);
};