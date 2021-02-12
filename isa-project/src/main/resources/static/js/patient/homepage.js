checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();

$(document).ready(function () {
	
	 const appTable= $('#appointments').DataTable();
	 const phTable= $('#pharmacies').DataTable();
	 
	 		$.ajax({
				type:"GET", 
				url: "/api/pharmacy",
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(appointments){
					
					for(i = 0; i < appointments.length; i++){
						addPharmacy(appointments[i],phTable);
					}
				},
				error:function(){
					console.log('error getting pharmacies');
				}
			});
			
			$.ajax({
				type:"GET", 
				url: "/api/appointment/patient/finished/" + patientId,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(appointments){
					
					for(i = 0; i < appointments.length; i++){
						addAppointment(appointments[i],appTable);
					}
				},
				error:function(){
					console.log('error getting appointments');
				}
			});
			
	});		
		

function addAppointment(appointment,appTable){
	appTable.row.add([ appointment.startTime.split('T')[0] + " " + appointment.startTime.split('T')[1],appointment.typeOfDoctor,"dr."+ appointment.doctorSurname,appointment.pharmacyName,appointment.price]);
	appTable.draw();

};
function addPharmacy(pharmacy,appTable){
	appTable.row.add([ pharmacy.name,pharmacy.averageGrade,pharmacy.address,pharmacy.cityName,pharmacy.pharmacistPrice]);
	appTable.draw();

};
