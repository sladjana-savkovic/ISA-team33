checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();

$(document).ready(function () {
		
	getSubscriptionsByPatient();
		
});



function getSubscriptionsByPatient() {
	$.ajax({
		type:"GET", 
		url: "/api/subscription/"+ patientId + "/patient",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(subscriptions){					
			for(i = 0; i < subscriptions.length; i++) {
				addSubscriptionRow(subscriptions[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error getting subscriptions');
		}
	});		
}


function addSubscriptionRow(subscription) {	
	let button = '<button style="width:130px;" type = "button" class="btn btn-outline-danger float-left"'
            + 'id="' + subscription.subscriptionId + '" onclick="cancel(this.id)"'
            + '> cancel </button >';
	let row = $('<tr><td style="text-align:center; vertical-align: middle;">'+ subscription.pharmacyName +'</td><td>' + button + '</td></tr>');	
	$('#body_table').append(row);	
}


function cancel(subscriptionId) {	
	$.ajax({
		type:"PUT", 
		url: "/api/subscription/"+ subscriptionId + "/cancel",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(){					
			alert('Successful cancellation');
			location.reload();				
		},
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error!');
		}
	});		
}

