var pharmacyAdminId = 6;
var pharmacyId;
var orderId;

$(document).ready(function () {
	
	$('#limitDate').prop("min",new Date().toISOString().split("T")[0]);
				
		$.ajax({
			type:"GET", 
			url: "/api/drug",
			contentType: "application/json",
			success:function(drugs){	
				for(i = 0; i < drugs.length; i++){
					addDrugsInCombo(drugs[i]);
				}
			},
			error:function(){
				console.log('error getting drugs');
			}
		});
						
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
		$('#order').submit(function(event){
		event.preventDefault();
		
		let limitDate = $('#limitDate').val();
		
		$.ajax({
				type:"POST", 
				url: "/api/order",
				data: JSON.stringify({ 
					limitDate: limitDate, 
					isFinished: false,
					idPharmacyAdmn: pharmacyAdminId}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully submit order limit date.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error creating order limit date.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
		});
	});
	
	$('#drug_quantity').submit(function(event){
		event.preventDefault();
		
		let drug = $('#drug').val();
		let quantity = $('#quantity').val();
		
		$.ajax({
			type:"GET", 
			url: "/api/order/last",
			contentType: "application/json",
			success:function(id){	
				orderId = id;
				
				$.ajax({
				type:"POST", 
				url: "/api/order/drug-quantity",
				data: JSON.stringify({ 
					idDrug: drug, 
					quantity: quantity,
					idPharmacyOrder: orderId}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully add drug in order.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error adding drug in order.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
		});
				
			},
			error:function(){
				console.log('error getting last order');
			}
		});
		
	});
			
		},
		error:function(){
			console.log('error getting pharmacy administrator');
		}
	});
	
});

function addDrugsInCombo(drug){
	let option = $('<option value="' + drug.id +'">' + drug.name + '</option>');
	$('#drug').append(option);
};