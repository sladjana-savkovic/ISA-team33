checkUserRole("ROLE_PHARMACYADMIN");
var pharmacyAdminId = getUserIdFromToken();
var pharmacyId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$('#startDate').prop("max",new Date().toISOString().split("T")[0]);
	$('#endDate').prop("max",new Date().toISOString().split("T")[0]);
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			let a1 = '<a style="margin-left: 50px;" href="http://localhost:8080/api/report/' + pharmacyId + '/pharmacy" target="_blank">Pharmacy and doctor rating report</a>'
			$('#report_div').append(a1);
			
			let a2 = '<a style="margin-left: 50px;" href="http://localhost:8080/api/report/' + pharmacyId + '/appointment" target="_blank">Graphic view of report about performed appointment</a>'
			$('#report_appointment_div').append(a2);
			
			let a3 = '<a style="margin-left: 50px;" href="http://localhost:8080/api/report/' + pharmacyId + '/drug" target="_blank">Graphic view of report about drug consumption</a>'
			$('#report_drug_div').append(a3);
			
			$('#income').submit(function(event){
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
		
		window.open('http://localhost:8080/api/report/' + pharmacyId + '/income/' + startDate + '/' + endDate,'_blank');
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

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}