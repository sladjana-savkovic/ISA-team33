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
	$('#reply').empty();
	
	let textarea = '<div><textarea class="form-control rounded" id="text_area_id" rows="8" placeholder="Write the answer here"></textarea></div>';
	
	let button = '<button style="width:130px;" type = "button" class="btn btn-success float-right"'
            + 'id="' + complaintId + '" onclick="sendEmail(this.id)"'
            + '> Send </button >';
	
	$('#reply').append(textarea);
	$('#reply').append('<br><p class="text-warning"> The message cannot be empty. </p>');
	$('#reply').append(button);
	$('#replyModal').modal('show');
}	
	
	
function sendEmail(complaintId) {		
	
	let message = $('textarea#text_area_id').val();
	
	if (!message) {
		return;
	}
	else {
		var addNotificationDTO = {
			"email": complaintMap[complaintId].patientEmail,
			"name": complaintMap[complaintId].patientNameAndSurname,
			"subject": "Response to the complaint",
			"message": message
		}	
		
		let path = complaintMap[complaintId].subjectType;
				
		$.ajax({
			type:"POST", 
			url: "/api/complaint/"+ complaintId + "/" + path + "/reply",
			headers: {
        	    'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        	},
			contentType: "application/json",
			data: JSON.stringify(addNotificationDTO),
			success:function(){					
				alert('Successful');
				location.reload();				
			},
			error:function(jqXHR){
				console.log(jqXHR);
				alert('Error! ' + jqXHR.responseText);
			}
		});			
	}
}

