checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();

var pharmacyId = null;

$(document).ready(function () {
	
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy",
		headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(pharmacies){	
			for(i = 0; i < pharmacies.length; i++){
				addPharmacyToCombo(pharmacies[i]);
			}
		},
		error:function(){
		console.log('error getting pharmacies');
		}
	});
	
	
	$('#pharmacy_selct').submit(function(event){
		event.preventDefault();
		
	pharmacyId = $("#pharmacies option:selected").attr("id");
			
			$.ajax({
				type:"GET", 
				url: "/api/pharmacy/" + pharmacyId,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(pharmacy){
					addPharmacyInfo(pharmacy);
					$('#body_doctors').empty();
					for(i = 0; i < pharmacy.doctors.length; i++){
						addDoctor(pharmacy.doctors[i]);
					}
					$('#body_appointments').empty();
					for(i = 0; i < pharmacy.appointments.length; i++){
						addAppointment(pharmacy.appointments[i]);
					}
					
					$.ajax({
						type:"GET", 
						url: "/api/drug/" + pharmacyId + "/pharmacy",
						headers: {
            				'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       					},
						contentType: "application/json",
						success:function(drugs){	
							$('#body_drugs').empty();
							for(i = 0; i < drugs.length; i++){
								addDrug(drugs[i]);
							}
						},
						error:function(){
							console.log('error getting drugs');
						}
					});
					
				},
				error:function(){
					console.log('error getting pharmacy');
				}
			});
	});		
		
	
});

function addPharmacyInfo(pharmacy){
	document.getElementById('name').innerHTML = pharmacy.name;
	document.getElementById('grade').innerHTML = pharmacy.averageGrade;
	document.getElementById('address').innerHTML = pharmacy.address + ", " + pharmacy.cityName + ", " + pharmacy.countryName;

};

function addDoctor(doctor){
	 let row = $('<tr><td>'+ doctor.name +'</td><td>' + doctor.surname + '</td><td>' + doctor.typeOfDoctor + '</td></tr>');	
	$('#doctors').append(row);
};

function addDrug(drug){
	 let row = $('<tr><td>'+ drug.name +'</td><td>' + drug.producer + '</td><td>' + drug.typeOfDrug + '</td><td>'  + drug.typeOfDrugsForm + '</td></tr>');	
	$('#drugs').append(row);
};

function addAppointment(appointment){
	 let row = $('<tr><td>'+ appointment.startTime.split('T')[0] + " " + appointment.startTime.split('T')[1] +'</td><td>' + appointment.typeOfDoctor + " " + appointment.doctorSurname + '</td><td>' + appointment.price + '</td></tr>');		
	$('#appointments').append(row);
};

function addPharmacyToCombo(pharmacy) {
	let pharmacy_option = $('<option id="' + pharmacy.id + '" value="' + pharmacy.name + '">' + pharmacy.name + '</option>');
	$('select#pharmacies').append(pharmacy_option);
};


/* Subscribe to the pharmacy actions */
function subscribe() {
	alert(patientId + " " + pharmacyId);
	dto = {
		"patientId": patientId,
		"pharmacyId": pharmacyId
	}	
	$.ajax({
		type:"POST", 
		url: "/api/subscription/add",
		headers: {
			'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		},
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(dto),
		success:function(jqXHR){	
			alert(jqXHR);
		},
		error:function(jqXHR){
			console.log('error');
			alert("Error! " + jqXHR);
		}
	});	
}

