var pharmacyAdminId = 6;
var pharmacyId;

$(document).ready(function () {
	
	$('#startDateAction').prop("min",new Date().toISOString().split("T")[0]);
	$('#endDateAction').prop("min",new Date().toISOString().split("T")[0]);
	$('#startDatePrice').prop("min",new Date().toISOString().split("T")[0]);
	$('#endDatePrice').prop("min",new Date().toISOString().split("T")[0]);
	
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
					
					$.ajax({
						type:"GET", 
						url: "/api/drug/" + pharmacyId + "/pharmacy",
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
					
				},
				error:function(){
					console.log('error getting pharmacy');
				}
				});
				
		$('#action').submit(function(event){
		event.preventDefault();
		
		let name = $('#name').val();
		let description = $('#description').val();
		let startDateAction = $('#startDateAction').val();
		let endDateAction = $('#endDateAction').val();
		
		var from_start =startDateAction.split("-");
		var from_end = endDateAction.split("-");
		var ds = new Date(from_start[0], from_start[1] - 1, from_start[2]);
		var de = new Date(from_end[0], from_end[1] - 1, from_end[2]);
		
		if(de < ds){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start date must be less than the end date.'
			    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert_action').append(alert);
				return;
		}
		
		$.ajax({
				type:"POST", 
				url: "/api/action",
				data: JSON.stringify({ 
					name: name, 
					description: description, 
					startDate: startDateAction,
					endDate: endDateAction,
					pharmacyId: pharmacyId}),
				contentType: "application/json",
				success:function(){
					let a = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully created pharmacy action.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert_action').append(a);
				
					$.ajax({
						type:"GET", 
						url: "/api/subscription/" + pharmacyId + "/pharmacy-patients",
						contentType: "application/json",
						success:function(emails){
							for(i = 0; i < emails.length; i++){
								
								$.ajax({
									url: "/api/email",
									type: 'POST',
									contentType: 'application/json',
									data: JSON.stringify({ 
									email: emails[i], 
									name: "", 
									subject: name,
									message: description}),
									success: function () {
									location.reload();	
									},
									error: function (jqXHR) {
										let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 			'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
										$('#div_alert').append(a);
								return;
							}
							});	
								
							}
						},
						error:function(){
						console.log('error getting patients email');
					}
					});
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error creating pharmacy action.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert_action').append(alert);
					return;
				}
		});
	});
	
	$('#pricelist').submit(function(event){
		event.preventDefault();
		
		let drug = $('#drug').val();
		let price = $('#price').val();
		let startDatePrice = $('#startDatePrice').val();
		let endDatePrice = $('#endDatePrice').val();
		
		var from_start_price =startDatePrice.split("-");
		var from_end_price = endDatePrice.split("-");
		var ds_price = new Date(from_start_price[0], from_start_price[1] - 1, from_start_price[2]);
		var de_price = new Date(from_end_price[0], from_end_price[1] - 1, from_end_price[2]);
		
		if(de_price < ds_price){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">The start date must be less than the end date.'
			    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert_action').append(alert);
				return;
		}
		
		$.ajax({
				type:"POST", 
				url: "/api/pricelist",
				data: JSON.stringify({ 
					price: price, 
					idPharmacy: pharmacyId, 
					startDate: startDatePrice,
					endDate: endDatePrice,
					idDrug: drug}),
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully created pricelist.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert_action').append(alert);
					location.reload();
					return;
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error creating pricelist.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert_action').append(alert);
					return;
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