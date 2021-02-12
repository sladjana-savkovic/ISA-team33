checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();
var doctorPharmacies = '';
var forFilter = [];
$(document).ready(function(){
	
	$.ajax({
				type:"GET", 
				url: "/api/doctor/all",
				headers: {
            			'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(doctors){
					forFilter = doctors;
					for(i = 0; i < doctors.length; i++){
						for(j = 0; j < doctors[i].pharmacies.length; j++){
							doctorPharmacies += doctors[i].pharmacies[j].name + ", ";
						}
						addDoctor(doctors[i]);
					}
					
					$('#search').submit(function(event){
						event.preventDefault();
		
						let name = "&";
						let surname = "&";
						let name_surname = $('#search_field').val().split(' ');
		
						if(name_surname[0]){
							 name = name_surname[0];
						}
						if(name_surname[1]){
							 surname = name_surname[1];
						}

						$.ajax({
							type:"POST", 
							url: "/api/doctor/search/" + name + "/" + surname,
							headers: {
            					'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        					},
							data: JSON.stringify(doctors),
							contentType: "application/json",
							success:function(searchResult){
								forFilter = searchResult;
								$('#body_medicals').empty();
								for (let d of searchResult){
									for(j = 0; j < d.pharmacies.length; j++){
										doctorPharmacies += d.pharmacies[j].name + ", ";
									}
									addDoctor(d);
								}
						},
						error:function(xhr){
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
						});
					});
					
					$('#filter').submit(function(event){
						event.preventDefault();
		
						let type = $('#types option:selected').val();
						let grade = $('#grades option:selected').val();
						let grade_int = parseInt(grade);

						$.ajax({
							type:"POST", 
							url: "/api/doctor/filter/" + type + "/" + grade_int,
							headers: {
            					'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        					},
							data: JSON.stringify(forFilter),
							contentType: "application/json",
							success:function(filterResult){
								$('#body_medicals').empty();
								for (let d of filterResult){
									for(j = 0; j < d.pharmacies.length; j++){
										doctorPharmacies += d.pharmacies[j].name + ", ";
									}
									addDoctor(d);
								}
						},
						error:function(xhr){
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
						});
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

function addDoctor(doctor){
	 let row = $('<tr><td>'+ doctor.name +'</td><td>' + doctor.surname + '</td><td>' + doctor.typeOfDoctor + '</td><td>' + doctor.grade + '</td><td>' + doctorPharmacies + '</td></tr>');	
	$('#medicals').append(row);
	doctorPharmacies='';
};
