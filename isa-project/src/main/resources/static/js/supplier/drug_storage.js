checkUserRole("ROLE_SUPPLIER");
var supplierId = getUserIdFromToken();

$(document).ready(function () {
		
	getDrugQuantityBySupplier();

	getAllDrugs();	
	
	/*Update drug quantity on submit*/
	$('form#updateDrugQuantity').submit(function (event) {	
	
		event.preventDefault();		
		let drugId = $("#drug option:selected").attr("id");
		let quantity = $('#quantity').val();
		
		var newDrugQuantity = {
			"drugId": drugId,
			"supplierId": supplierId,
			"quantity": quantity 
		}
		/*
		$.ajax({
			url: "/api/patient",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(newDrugQuantity),
			success: function () {
				location.reload();
				return;
			},
			error: function (jqXHR) {
				console.log(jqXHR);
				alert('Error update drug quantity!');
				return;
			}
		});			*/		
	});			
});



function getDrugQuantityBySupplier() {
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
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error getting drug quantity');
		}
	});		
}


function addDrugQuantityRow(drug) {	
	let row = $('<tr><td>'+ drug.drugName +'</td><td>' + drug.quantity + '</td></tr>');	
	$('#body_table').append(row);	
}


function getAllDrugs() {		
    $.ajax({
        url: '/api/drug',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (drugs) {
            for (let i = 0; i < drugs.length; i++) {
            	$("select#drug").append('<option id="' + drug[i].id + '">' + drugs[i].name + '</option>');            
            }
        },
        error: function (jqXHR) {
			console.log(jqXHR);
			alert("Error getting drugs!");
        }
    });	
}
	
