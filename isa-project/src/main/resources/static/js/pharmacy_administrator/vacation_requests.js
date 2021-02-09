checkUserRole("ROLE_PHARMACYADMIN");
var pharmacyAdminId = getUserIdFromToken();
var pharmacyId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			$.ajax({
				type:"GET", 
				url: "/api/vacation/pharmacy/" + pharmacyId,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(vacations){
					
					for(i = 0; i < vacations.length; i++){
						addVacationRequest(vacations[i]);
					}
					
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
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

function addVacationRequest(vacation){
	 let row = $('<tr><td>'+ vacation.doctorSurname +'</td><td>' + vacation.typeOfDoctor + '</td><td>' + vacation.startDate + '</td><td>' + vacation.endDate + '</td><td>' + '<button name="rejectButton" type = "button" class="btn btn-success float-right" id="' + vacation.id + '" onclick="acceptRequest(this.id)">Accept</button>' + '</td><td>' + '<button name="rejectButton" type = "button" class="btn btn-danger float-right" data-toggle="modal" data-target="#modalConfirmReject" id="' + vacation.id + '" onclick="rejectRequest(this.id)">Reject</button>' + '</td></tr>');	
	$('#vacations').append(row);
};

function rejectRequest(id){
			
		$('a#submit_reject').click(function(event){
		
		event.preventDefault();
		
		let reason = $('#text_reason').val();
			
			$.ajax({
				type:"PUT", 
				url: "/api/vacation/" + id + "/reason/" + reason,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(){
					let a = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully reject vacation request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(a);
					
					$.ajax({
						type:"GET", 
						url: "/api/vacation/" + id + "/doctor",
						headers: {
            				'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        				},
						contentType: "application/json",
						success:function(doctor){
							
							$.ajax({
								url: "/api/email/" + doctor.id,
								type: 'POST',
								contentType: 'application/json',
								data: JSON.stringify({ 
									subject: "Rejection of vacation requests",
									message: reason}),
								success: function () {
								location.reload();	
							},
							error:function(){
								let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + 'Successfully reject request, but an error occurred while sending an email.'
								+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
								$('#div_alert').append(alert);
								return;
							}
							});	
					
					},
					error:function(xhr){
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
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
};

function acceptRequest(id){
	
			$.ajax({
				type:"PUT", 
				url: "/api/vacation/" + id,
				headers: {
            			'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(){
					
					let a = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully accept vacation request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(a);
					
					$.ajax({
						type:"GET", 
						url: "/api/vacation/" + id + "/doctor",
						headers: {
            				'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        				},
						contentType: "application/json",
						success:function(doctor){
							$.ajax({
								url: "/api/email/" + doctor.id,
								type: 'POST',
								contentType: 'application/json',
								data: JSON.stringify({ 
									subject: "Accepting vacation requests",
									message: "Your vacation request has been accepted."}),
								success: function () {
								location.reload();	
							},
							error:function(){
								let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + 'Successfully accept request, but an error occurred while sending an email.'
								+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
								$('#div_alert').append(alert);
								return;
							}
							});	
					
					},
					error:function(xhr){
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
					});
				
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
					
};

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}