var doctorId = 1;
var examinedPatients = [];
$(document).ready(function () {

	$.ajax({
		type:"GET", 
		url: "/api/examination-report/doctor/" + doctorId,
		contentType: "application/json",
		success:function(patients){
			$('#body_patients').empty();
			examinedPatients = patients;
			for (let p of patients){
				addPatient(p);
			}
		},
		error:function(){
			console.log('error getting examined patients');
		}
	});
	
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
				url: "/api/examination-report/search/" + name + "/" + surname,
				data: JSON.stringify(examinedPatients),
				contentType: "application/json",
				success:function(searchResult){
					$('#body_patients').empty();
					for (let p of searchResult){
						addPatient(p);
					}
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error during searching patients.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
		});
	});
});

function addPatient(patient){
	 let row = $('<tr><td>'+ patient.name +'</td><td>' + patient.surname + '</td><td>' + patient.dateOfLastExamination + '</td></tr>');	
	$('#patients').append(row);
}