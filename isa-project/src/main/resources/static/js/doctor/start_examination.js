try {
	var appointmentId = localStorage.getItem("appointmentId");
	
	if(appointmentId == null){
		window.location.href = "calendar.html";
	}
}
catch(err) {
   clearLocalStorage();
   window.location.href = "calendar.html";
}

checkUserRole("ROLE_DERMATOLOGIST_PHARMACIST");
var doctorId = getUserIdFromToken();
var appointment = null;
var therapies = [];
var therapyId = 1;
var drugs = [];
$(document).ready(function () {
		
	$.ajax({
		type:"GET", 
		url: "/api/appointment/" + appointmentId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(appointment_for_examination){
			appointment = appointment_for_examination;
			
			$.ajax({
				type:"GET", 
				url: "/api/drug-quantity-pharmacy/" + appointment.pharmacyId + "/available",
				headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		        },
				contentType: "application/json",
				success:function(drugs){
					$('#drugs').empty();
					for (let d of drugs) {
						addDrug(d);
					}
				},
				error:function(){
					console.log('error getting drugs in pharmacy');
				}
			});
		},
		error:function(){
			console.log('error getting doctor appointment');
		}
	});
	
	
	$('#confirm').click(function(){
		
		if($('#start').is(':checked')){
			$('#select_option').attr('hidden',true);
			$('#examination_report').attr('hidden',false);
			fillInBasicInfo();
		}
		else if($('#end').is(':checked')){
			
			$('#confirm').attr("disabled",true);
			
			$.ajax({
				type:"PUT", 
				url: "/api/appointment/" + appointment.appointmentId + "/patient/" + appointment.patientId + "/unperformed",
				headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		        },
				contentType: "application/json",
				success:function(){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
					+'Successfully changed appointment status and increased penalties for patient.'
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					clearLocalStorage();
					window.setTimeout(function(){location.href = "calendar.html"},1000);
					return;
				},
				error:function(xhr){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					$('#confirm').attr("disabled",false);
					return;
				}
			});
		}
	
	});
	
	$('#check').click(function(){
		
		let drugId = $("#drugs option:selected").val();
				
		$.ajax({
			type:"GET", 
			url: "/api/drug-quantity-pharmacy/" + drugId + "/" + appointment.pharmacyId + "/availability",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(availability){

				if(availability){
					$('#div_prescribe').attr("hidden",false);
				}else{
					//U slucaju da lijek nije dostupan izbaciti dijalog i ponuditi zamjenske lijekove
					$('#drugAvailabilityModal').modal('toggle');
					$('#drugAvailabilityModal').modal('show');
					
					$.ajax({
						type:"GET", 
						url: "/api/drug/" + drugId + "/substitute",
						contentType: "application/json",
						success:function(substituteDrugs){		
							
							if(substituteDrugs.length == 0){
								$('#subDrugs').attr("hidden",true);
								$('#subDrugsDuration').attr("hidden",true);
								$('#prescribeSubstitute').attr("hidden",true);
								$('#substituteText').text("The requested drug isn't available and has no substitute drugs.");
							}else{
								$('#subDrugs').attr("hidden",false);
								$('#subDrugsDuration').attr("hidden",false);
								$('#prescribeSubstitute').attr("hidden",false);
								$('#substituteText').text("The requested drug isn't available, choose one of the substitutes.");
								
								$('#substituteDrugs').empty();
								for(let d of substituteDrugs){
								addSubstituteDrug(d);
							}
						}
									
						},
						error:function(){
							console.log('error getting substitute drugs');
						}
					});
				}
			},
			error:function(){
				console.log('error checking drug availability');
			}
		});
		
	});
	
	
	$('#specification').click(function(){
		
		let drugId = $("#drugs option:selected").val();
		
		for(let d of drugs){
			if(d["drugId"] == drugId){
				$('#dName').text(d["drugName"]);
				$('#dType').text(d["typeOfDrug"]);
				$('#dForm').text(d["typeOfDrugsForm"]);
				$('#dProducer').text(d["producer"]);
				$('#dContra').text(d["contraindication"]);
				$('#dDose').text(d["dailyDose"]);
								
				let ingredientNames = [];
				for(let i of d.ingredients){
					ingredientNames.push(i.name);
				}
				
				$('#dIngredients').text(ingredientNames);
				$('#dSubstitute').text(d["substitute"]);
			}
		}
		
	});
	
	$('#prescribe').click(function(){
		
		let drugId = $("#drugs option:selected").val();
				
		//Prije propisivanja lijeka potrebno je provjeriti da li je pacijent alergican na njega
		$.ajax({
			type:"GET", 
			url: "/api/patient/" + appointment.patientId + "/allergy/" + drugId,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(hasAllergy){				
				if(hasAllergy){
					$('#noAllergy').attr("hidden",true);
					$('#save_changes').attr("hidden",true);
					$('#hasAllergy').attr("hidden",false);
					return;
				}else{
					$('#noAllergy').attr("hidden",false);
					$('#save_changes').attr("hidden",false);
					$('#hasAllergy').attr("hidden",true);
					$('#duration').val("");
					return;
				}
			},
			error:function(){
				console.log('error checking allergy');
			}
		});
	});
	
	
	$('#prescribeSubstitute').click(function(){
		
		let substituteDrugId = $("#substituteDrugs option:selected").val();
		
		if(!$('#durationSubstitute').val()){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Enter duration of therapy.'
			+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		//Prije propisivanja zamjenskog lijeka potrebno je provjeriti da li je pacijent alergican na njega
		$.ajax({
			type:"GET", 
			url: "/api/patient/" + appointment.patientId + "/allergy/" + substituteDrugId,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			success:function(hasAllergy){				
				if(hasAllergy){
					$('#allergySubstitute').attr("hidden",false);
					return;
				}else{
					$('#allergySubstitute').attr("hidden",true);
					$('#durationSubstitute').val('');
				}
			},
			error:function(){
				console.log('error checking allergy');
			}
		});
		
	});
	
	
	$('#save_prescription').submit(function(event){
		event.preventDefault();
		
		let drugId = $("#drugs option:selected").val();
		let drugName = $("#drugs option:selected").text();
		
		$('#btn_close').click();
		let duration = $('#duration').val();
		
		therapies.push({"therapyId":therapyId, "drugId": drugId,"drugName" : drugName, "duration": duration});
		therapyId = therapyId + 1;
		reloadTherapies();
	});
	
	$('#save_prescription_substitute').submit(function(event){
		event.preventDefault();
		
		let substituteDrugId = $("#substituteDrugs option:selected").val();
		let substituteDrugName = $("#substituteDrugs option:selected").text();
		
		$('#btn_close_substitute').click();
		let duration = $('#durationSubstitute').val();
		
		therapies.push({"therapyId":therapyId, "drugId": substituteDrugId,"drugName" : substituteDrugName, "duration": duration});
		therapyId = therapyId + 1;
		reloadTherapies();
	});
	
	
	$("#drugs" ).change(function() {
	  	$('#div_prescribe').attr("hidden",true);
	});
	
	$('#submit_report').submit(function(event){
		event.preventDefault();
		
		let therapyDTOs = [];
		for(let t of therapies){
			therapyDTOs.push({"drugId":t.drugId, "duration":t.duration});
		}
		
		//Saljem zahtjev da provjerim da li su svi prepisani lijekovi jos uvijek dostupni i ako jesu dodajem izvjestaj
		$.ajax({
			type:"POST", 
			url: "/api/examination-report",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			data: JSON.stringify({ 
				appointmentId: appointmentId, 
				diagnosis: $('#diagnosis').val(),
				pharmacyId : appointment.pharmacyId,
				therapyDTOs: therapyDTOs}),
			contentType: "application/json",
			success:function(data,textStatus, xhr){
				if(xhr.status == 200){
					let missingDrugs = [];
					for(let drug of data){
						missingDrugs.push(drug.drugName);
					}
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">'
					+'Drug(s) ' + missingDrugs + ' are no longer available. Please remove them from therapy. '
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
				else{
					disableFields();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully saving examination report.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			},
			error:function(xhr){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});	
	
	
	$('#newApp').click(function(){
		clearLocalStorage();
		window.location.href = "create_appointment.html"
		localStorage.setItem("patientId", appointment.patientId);
		localStorage.setItem("pharmacyId", appointment.pharmacyId);
	});
	
	
	$('#finish').click(function(){
		clearLocalStorage();
		window.location.href = "calendar.html";
	});
	
});


function disableFields(){
	$('#diagnosis').attr("disabled",true);
	$('#drugs').attr("disabled",true);
	$('#check').attr("disabled",true);
	$('#prescribe').attr("disabled",true);
	$('#specification').attr("disabled",true);
	$('#create').attr("disabled",true);
	$('.removeTherapyBtn').attr("hidden",true);
	
	$('#collapseTwo').removeClass();
	$('#collapseTwo').addClass("collapse");
	$('#collapseOne').removeClass();
	$('#collapseOne').addClass("collapse");
	
	$('#label_drugs').attr("hidden",true);
	$('#drugs').attr("hidden",true);
	$('#check').attr("hidden",true);
	$('#prescribe').attr("hidden",true);
	$('#specification').attr("hidden",true);
	$('#create').attr("hidden",true);
	
	$('#create_Appointment').attr("hidden",false);
}

function reloadTherapies(){
	$('#body_therapies').empty();
	for(let t of therapies){
		let row = $('<tr><td style="vertical-align: middle;">'+ t.drugName +'</td><td style="vertical-align: middle;">' + t.duration + '</td>'
		+ '<td><button class="btn btn-danger removeTherapyBtn" type="button" id="' + t.therapyId +'" onclick="removeTherapy(this.id)">Remove</button></td></tr>');	
		$('#therapies').append(row);
	}
}

function removeTherapy(therapyId){
	let index = -1;
	for(let i=0; i<therapies.length; i++){
		if (therapies[i].therapyId == therapyId){
			index = i;
			break;
		}
	}
	if(index != -1){
		therapies.splice(index, 1);
		reloadTherapies();
	}
}

function fillInBasicInfo(){
	$('#patientName').text(appointment.patientName);
	$('#patientSurname').text(appointment.patientSurname);
	$('#doctorName').text(appointment.doctorName);
	$('#doctorSurname').text(appointment.doctorSurname);
	$('#startTime').text(appointment.startTime.split("T")[0] + " " + appointment.startTime.split("T")[1]);
	$('#endTime').text(appointment.endTime.split("T")[0] + " " + appointment.endTime.split("T")[1]);
	$('#pharmacyName').text(appointment.pharmacyName);
	$('#price').text(appointment.price);
};

function addDrug(drug){
	drugs.push({"drugId":drug.id, "drugName":drug.name, "typeOfDrug":drug.typeOfDrug, "typeOfDrugsForm":drug.typeOfDrugsForm,
				"producer":drug.producer, "contraindication":drug.contraindication, "dailyDose":drug.dailyDose, "ingredients":drug.ingredients,
				"substitute": drug.substituteDrugs});
				
	let option = $('<option data-tokens="' + drug.name + '" value="' + drug.id +'">' + drug.name + '</option>');
	$('#drugs').append(option);
};

function addSubstituteDrug(drug){
	let option = $('<option data-tokens="' + drug.name + '" value="' + drug.id +'">' + drug.name + '</option>');
	$('#substituteDrugs').append(option);
}


function clearLocalStorage(){
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
	localStorage.removeItem("appointmentId");
}
