checkUserRole("ROLE_SYSTEMADMIN");
var systemAdminId = getUserIdFromToken();

var complaintMap = {};

$(document).ready(function () {
		
	getComplaints();
		
});



function getComplaints() {
	$.ajax({
		type:"GET", 
		url: "/api/complaint/all",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(complaints){					
			for(i = 0; i < complaints.length; i++) {
				addComplaintRow(complaints[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error getting complaints');
		}
	});		
}


function addComplaintRow(complaint) {
	
	complaintMap[complaint.complaintId] = complaint;
	
	let buttonContent = '<button style="width:140px;" type = "button" class="btn btn-outline-primary float-left"'
            + 'id="' + complaint.complaintId + '" onclick="showContent(this.id)"'
            + '> Content </button >';

	let buttonReply = '<button style="width:130px;" type = "button" class="btn btn-outline-primary float-left"'
            + 'id="' + complaint.complaintId + '" onclick="replay(this.id)"'
            + '> Reply </button >';

	let row = $('<tr><td style="text-align:center; vertical-align: middle;">'+ complaint.patientNameAndSurname +'</td>'
				+ '<td style="text-align:center; vertical-align: middle;">' + complaint.patientEmail + '</td>'				
				+ '<td style="text-align:center; vertical-align: middle;">' + complaint.subjectType + ' ' + complaint.subjectName + '</td>'
				+ '<td style="text-align:center; vertical-align: middle;">' + buttonContent + '</td>'
				+ '<td style="text-align:center; vertical-align: middle;">' + buttonReply + '</td>'
				+ '</tr>');	
				
	$('#body_table').append(row);	
}



function showContent(complaintId) {
	$('#content').empty();
	$('#content').append('<p style="margin-left: 10px; margin-top: 5px">' + complaintMap[complaintId].content + '</p>');	
	$('#complaintModal').modal('show');	
}



function replay(complaintId) {	
/*	$.ajax({
		type:"POST", 
		url: "/api/complaint/"+ complaintId + "/replay",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(){					
			alert('Successful');
			location.reload();				
		},
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error! ' + jqXHR.responseText);
		}
	});		*/
}

