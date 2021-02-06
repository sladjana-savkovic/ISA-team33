checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function () {
	
	$('#createApp').submit(function(event){
		event.preventDefault();
		let appDate = $('#appDate').val();
		let appStartTime = $('#appStartTime').val();
		//let appEndTime = $('#appEndTime').val();
		
		let startTime = appDate + "T" + appStartTime + ":00";
		localStorage.setItem("wantedTime",startTime);
		//let endTime = appDate + "T" + appEndTime + ":00";
		
		//var from_start =appStartTime.split(":");
		//var from_end = appEndTime.split(":");
		
		/*var end_hour = parseInt(from_end[0]);
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
		} */
		
		$('#createAppBtn').attr("disabled",true);
		
		
	});
	
});
