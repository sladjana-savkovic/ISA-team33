var pharmacyAdminId = 6;
var pharmacyId;
$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			let a1 = '<a style="margin-left: 50px;" href="http://localhost:8080/api/doctor/' + pharmacyId + '/report" target="_blank">Report about pharmacy</a>'
			$('#report_div').append(a1);
			
			let a2 = '<a style="margin-left: 50px;" href="http://localhost:8080/api/report/' + pharmacyId + '/appointment" target="_blank">Report about performed appointment</a>'
			$('#report_appointment_div').append(a2);
		},
		error:function(){
			console.log('error getting pharmacy administrator');
		}
	});
	
});
