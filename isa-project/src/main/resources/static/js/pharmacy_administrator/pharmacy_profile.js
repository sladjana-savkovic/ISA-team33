var pharmacyAdminId = 6;
var pharmacyId;
$(document).ready(function () {
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			$.ajax({
				type:"GET", 
				url: "/api/pharmacy/" + pharmacyId,
				contentType: "application/json",
				success:function(pharmacy){
					addPharmacyInfo(pharmacy);
					
					for(i = 0; i < pharmacy.doctors.length; i++){
						addDoctor(pharmacy.doctors[i]);
					}
					
					for(i = 0; i < pharmacy.appointments.length; i++){
						addAppointment(pharmacy.appointments[i]);
					}
					
					$.ajax({
						type:"GET", 
						url: "/api/drug/" + pharmacyId + "/pharmacy",
						contentType: "application/json",
						success:function(drugs){	
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
			
		},
		error:function(){
			console.log('error getting pharmacy administrator');
		}
	});
	
});

function addPharmacyInfo(pharmacy){
	$('#name').val(pharmacy.name);
	$('#grade').val(pharmacy.averageGrade);
	$('#address').val(pharmacy.address + ", " + pharmacy.cityName + ", " + pharmacy.countryName);	
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