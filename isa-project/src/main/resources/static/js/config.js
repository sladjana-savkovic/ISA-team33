var appConfig={
    'doctorId' : 1
}

$(document).ready(function () {

	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + doctorId,
		contentType: "application/json",
		success:function(doctor){
			changeNavbar(doctor.typeOfDoctor);
		},
		error:function(){
			console.log('error getting doctor');
		}
	});
	
});

function changeNavbar(typeOfDoctor){
	
	if(typeOfDoctor == "Dermatolog"){ 
		document.body.appendChild(document.createElement('script')).src='../../js/navbars/dermatologist.js';
	}else{
		document.body.appendChild(document.createElement('script')).src='../../js/navbars/pharmacist.js';
		$('#searchPredefinedAppointments').attr('hidden',true);
	}
	
};