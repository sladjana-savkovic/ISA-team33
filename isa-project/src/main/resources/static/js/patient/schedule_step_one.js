checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
$(document).ready(function () {
	
	$('#createApp').submit(function(event){
		event.preventDefault();
		let appDate = $('#appDate').val();
		let appStartTime = $('#appStartTime').val();
		
		let startTime = appDate + "T" + appStartTime + ":00";
		localStorage.setItem("wantedTime",startTime);
		
		$('#createAppBtn').attr("disabled",true);
		
		
	});
	
});
