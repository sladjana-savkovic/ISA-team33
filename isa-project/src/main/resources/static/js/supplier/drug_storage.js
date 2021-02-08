checkUserRole("ROLE_SUPPLIER");
var supplierId = getUserIdFromToken();

$(document).ready(function () {

	$.ajax({
		type:"GET", 
		url: "/api/drug-quantity-supplier/" + supplierId + "/supplier",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(quantities){					
			for(i = 0; i < quantities.length; i++) {
				addDrugQuantityRow(quantities[i]);
			}								
		},
		error:function(){
			console.log('error getting drug quantity');
			alert('error getting drug quantity');
		}
	});				
});



function addDrugQuantityRow(drug) {
	
	let row = $('<tr><td>'+ drug.drugName +'</td><td>' + drug.quantity + '</td></tr>');	
	$('#body_table').append(row);	
}





