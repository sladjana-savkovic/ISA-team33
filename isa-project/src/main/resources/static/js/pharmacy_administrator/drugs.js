var pharmacyAdminId = 6;
var pharmacyId;
var orderId;
var searchDrugs = [];
var selectedDrugs = [];
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
	
	$('#drug_quantity').submit(function(event){
		event.preventDefault();
		
		let drug_id = $('#drug').val();
		let quantity = $('#quantity').val();
		
		$.ajax({
			type:"GET", 
			url: "/api/drug/" + drug_id,
			contentType: "application/json",
			success:function(drug){	
			let item = drug_id + "-" + quantity;
			for(i=0; i<selectedDrugs.length; i++){
				if(selectedDrugs[i] == item){
				let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Drug with that quantity already exist.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(a);
					return;
				}
			}
				selectedDrugs.push(item);
				let row = $('<tr><td>'+ drug.name +'</td><td>' + quantity + '</td></tr>');	
				$('#check_drugs').append(row);
			},
			error:function(){
				console.log('error getting drugs');
			}
		});
		
		
	});
	
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
	
	$('a#yes').click(function(event){
	
		event.preventDefault();
		
		$.ajax({
			type:"GET", 
			url: "/api/order/last",
			contentType: "application/json",
			success:function(id){	
				orderId = id;
			for(i=0; i<selectedDrugs.length; i++){
				let drug = selectedDrugs[i].split("-")[0];
				let quantity = selectedDrugs[i].split("-")[1];
				$.ajax({
				type:"POST", 
				url: "/api/order/drug-quantity",
				data: JSON.stringify({ 
					idDrug: drug, 
					quantity: quantity,
					idPharmacyOrder: orderId}),
				contentType: "application/json",
				success:function(){
					location.reload();
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
		}
				
			},
			error:function(){
				console.log('error getting last order');
			}
		});
		
	});
	
		$.ajax({
			type:"GET", 
			url: "/api/order/" + pharmacyId + "/pharmacy",
			contentType: "application/json",
			success:function(orders){	
				for(i = 0; i < orders.length; i++){
					addOrder(orders[i], i+1);
				}
			},
			error:function(){
				console.log('error getting drugs');
			}
		});
		
		$.ajax({
			type:"GET", 
			url: "/api/drug-quantity-pharmacy/" + pharmacyId,
			contentType: "application/json",
			success:function(drugs){	
				searchDrugs = drugs;
				for(i = 0; i < drugs.length; i++){
					addDrugInPharmacy(drugs[i]);
				}
			},
			error:function(){
				console.log('error getting drugs from pharmacy');
			}
		});
		
		$('#search').submit(function(event){
		event.preventDefault();
		
		let drug_name = "&";
		let drug_name_val = $('#search_field').val();
		
		if(drug_name_val){
		 	drug_name = drug_name_val;
		}
		
		$.ajax({
				type:"POST", 
				url: "/api/drug-quantity-pharmacy/search/" + drug_name,
				data: JSON.stringify(searchDrugs),
				contentType: "application/json",
				success:function(searchResult){
					$('#body_pharmacy_drugs').empty();
					for (let d of searchResult){
						addDrugInPharmacy(d);
					}
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error searching drugs.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
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

function addDrugInPharmacy(drug){
	let row = $('<tr><td>'+ drug.name +'</td><td>' + drug.producer + '</td><td>' + drug.typeOfDrug + '</td><td>' + drug.typeOfDrugsForm + '</td><td>' + '<button name="deleteButton" type = "button" class="btn btn-danger float-right" id="' + drug.id + '" onclick="deleteDrug(this.id)">Delete</button>' + '</td></tr>');	
	$('#pharmacy_drugs').append(row);
};

function deleteDrug(id){
			$.ajax({
				type:"PUT", 
				url: "/api/drug-quantity-pharmacy/" + id + "/" + pharmacyId + "/delete",
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully delete drug.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error deleting drug.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
};

function addDrugsInCombo(drug){
	let option = $('<option value="' + drug.id +'">' + drug.name + '</option>');
	$('#drug').append(option);
};

function addOrder(order, i){
	
	let order_div = '<div class="card" id="div' + order.id + '"><table id="table' + order.id + '" style="margin-left:50px; margin-right:50px; margin-top:30px; margin-bottom:30px; width:300px;">'
                       + '<tr> <td><h5>Order' + ' ' + i + '</h5></td> </tr>'
						+ '<tr> <td>Limit date:</td><td>' + order.limitDate + '</td><td></td></tr>' 
						+ '<tr> <td><b>Ordered drugs:</b></td> </tr>'
                        + '</table></div>';

	$('#order_content').append(order_div);
	
	$.ajax({
			type:"GET", 
			url: "/api/order/" + order.id + "/drug-quantity",
			contentType: "application/json",
			success:function(drugs){	
				for(i = 0; i < drugs.length; i++){
					addDrug(drugs[i], order.id);
				}
			},
			error:function(){
				console.log('error getting drugs');
			}
	});
	
	$.ajax({
			type:"GET", 
			url: "/api/drug-offer/" + order.id + "/pharmacy-order",
			contentType: "application/json",
			success:function(offers){	
				for(i = 0; i < offers.length; i++){
					addOffer(offers[i], order.id, order.limitDate, order.idPharmacyAdmn, i + 1);
				}
			},
			error:function(){
				console.log('error getting offers');
			}
		});
		
	
};

function addDrug(drug, id){
	
	let order_div = '<tr> <td>' + drug.drugName + '</td><td>' + drug.quantity + '</td> </tr>';
	let t = '#table' + id;
	$(t).append(order_div);
};

function addOffer(offer, id, date, admin, i){
	
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();

	today = yyyy + '-' + mm + '-' + dd;
	
	let offer_div1 =  '<table style="margin-left:50px; margin-right:50px; margin-top:30px; margin-bottom:30px; width:300px;">'
                       + '<tr> <td><h6>Offer' + ' ' + i + '</h6></td> </tr>'
						+ '<tr> <td>Limit date:</td><td>' + offer.limitDate + '</td></tr>' 
						+ '<tr> <td>Total price:</td><td>' + offer.totalPrice + '</td> </tr>' 
                        + '</table></div>';
	
	let offer_div2 =  '<table style="margin-left:50px; margin-right:50px; margin-top:30px; margin-bottom:30px; width:300px;">'
                       + '<tr> <td><h6>Offer' + ' ' + i + '</h6></td> </tr>'
						+ '<tr> <td>Limit date:</td><td>' + offer.limitDate + '</td><td>' + '<button name="acceptButton" type = "button" class="btn btn-success float-right" id="' + offer.id + '" onclick="acceptOffer(this.id)">Accept</button>' + '</td> </tr>' 
						+ '<tr> <td>Total price:</td><td>' + offer.totalPrice + '</td> </tr>' 
                        + '</table></div>';
	
	let t = '#div' + id;
	
	var from_date =date.split("-");
	var from_today = today.split("-");
	var dd = new Date(from_date[0], from_date[1] - 1, from_date[2]);
	var dt = new Date(from_today[0], from_today[1] - 1, from_today[2]);
	
	if(dd < dt && admin == pharmacyAdminId && offer.status=="Waiting"){
		$(t).append(offer_div2);
	}else{
		$(t).append(offer_div1);
	}
};

function acceptOffer(id){
	
			$.ajax({
				type:"PUT", 
				url: "/api/drug-offer/" + id,
				contentType: "application/json",
				success:function(){
					let a = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully accept offer.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(a);
					$.ajax({
						type:"GET", 
						url: "/api/drug-offer/" + id + "/supplier",
						contentType: "application/json",
						success:function(supplier){
							$.ajax({
								url: "/api/email/" + supplier.id,
								type: 'POST',
								contentType: 'application/json',
								data: JSON.stringify({ 
									subject: "Accepting drug offer",
									message: "Your offer has been accepted."}),
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
					
					},
					error:function(){
						console.log('error getting doctor');
					}
					});
					
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error accept offer.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
			
			$.ajax({
			type:"GET", 
			url: "/api/drug-offer/" + id,
			contentType: "application/json",
			success:function(offer){	
				$.ajax({
					type:"GET", 
					url: "/api/drug-offer/" + offer.pharmacyOrderId + "/pharmacy-order",
					contentType: "application/json",
					success:function(offers){	
						for(i = 0; i < offers.length; i++){
							if(offers[i].status != "Accepted"){
								$.ajax({
									type:"PUT", 
									url: "/api/drug-offer/" + offers[i].id + "/reject",
									contentType: "application/json",
									success:function(){
									
									$.ajax({
										type:"GET", 
										url: "/api/drug-offer/" + id + "/supplier",
										contentType: "application/json",
										success:function(supplier){
											$.ajax({
											url: "/api/email/" + supplier.id,
											type: 'POST',
											contentType: 'application/json',
											data: JSON.stringify({ 
											subject: "Rejection drug offer",
											message: "Your offer has been rejected."}),
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
					
					},
					error:function(){
						console.log('error getting supplier');
					}
					});
									
								},
									error:function(){
									let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error reject offer.'
										+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
										$('#div_alert').append(a);
										return;
									}
								});
									}
						}
								$.ajax({
									type:"GET", 
									url: "/api/order/" + offer.pharmacyOrderId  + "/drug-quantity",
									contentType: "application/json",
									success:function(drugQuantities){	
										for(i = 0; i < drugQuantities.length; i++){
											var drug_id = drugQuantities[i].idDrug;
											var quantity = drugQuantities[i].quantity;
											$.ajax({
												type:"PUT", 
												url: "/api/drug-quantity-pharmacy/" + drug_id + "/" + pharmacyId + "/" + quantity + "/increase",
												contentType: "application/json",
												success:function(result){
													if(result == false){
														$.ajax({
															type:"POST", 
															url: "/api/drug-quantity-pharmacy",
															data: JSON.stringify({ 
															drugId: drug_id, 
															pharmacyId: pharmacyId,
															quantity: quantity}),
															contentType: "application/json",
															success:function(){
																let a = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully add new drug.'
																+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
																$('#div_alert').append(a);
																return;
					
															},
															error:function(){
																let a = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error adding new drug.'
																+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
																$('#div_alert').append(a);
																return;
															}
														});
													
												}
											
											},
											error:function(){
											let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error accept offer.'
												+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
												$('#div_alert').append(alert);
													return;
												}
										});
										}
									},
									error:function(){
										console.log('error getting drug quantities');
									}
								});
								
					},
					error:function(){
						console.log('error getting offer');
					}
				});
				
			},
			error:function(){
				console.log('error getting offer');
			}
			});		
			
};
