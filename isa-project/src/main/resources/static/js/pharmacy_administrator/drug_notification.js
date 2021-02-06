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
						url: "/api/notification/" + pharmacyId + "/pharmacy",
						headers: {
            			'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        				},
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
			console.log('error getting pharmacy administrator');
		}
	});
	
});


function addDrug(drug){
	 let row = $('<tr><td>'+ drug.date.split('T')[0] + " " + drug.date.split('T')[1] + '</td><td>' + drug.drugName + '</td><td>' + drug.typeOfDrug + '</td><td>'  + drug.typeOfDrugForm + '</td></tr>');	
	$('#drugs').append(row);
};

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}