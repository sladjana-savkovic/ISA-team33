var doctorId = appConfig.doctorId;
var appointmentId = window.location.href.split("?")[1];
var appointment = null;
var therapies = [];
var drugs = [];
$(document).ready(function () {
		
	$.ajax({
		type:"GET", 
		url: "/api/appointment/" + appointmentId,
		contentType: "application/json",
		success:function(appointment_for_examination){
			appointment = appointment_for_examination;
			
			$.ajax({
				type:"GET", 
				url: "/api/drug-quantity-pharmacy/" + appointment.pharmacyId + "/available",
				contentType: "application/json",
				success:function(drugs){
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
			
			$.ajax({
				type:"PUT", 
				url: "/api/appointment/" + appointment.appointmentId + "/unperformed",
				contentType: "application/json",
				success:function(){
					$.ajax({
						type:"PUT", 
						url: "/api/patient/" + appointment.patientId + "/increase-penalty",
						contentType: "application/json",
						success:function(){
							let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">'
								+'Successfully changed appointment status and increased penalties for patient.'
								+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							location.href = "calendar.html";
							return;
						},
						error:function(){
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error increasing penalties for patient.'
								+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
					});
				},
				error:function(){
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error changing appointment status to unperformed .'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
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
								
				let ingredients = "";
				for(let i of d.ingredients){
					ingredients += i.name + "\n";
				}
				
				$('#dIngredients').text(ingredients);
			}
		}
		
	});
	
	$('#prescribe').click(function(){
		
		let drugId = $("#drugs option:selected").val();
				
		//Prije propisivanja lijeka potrebno je provjeriti da li je pacijent alergican na njega
		$.ajax({
			type:"GET", 
			url: "/api/patient/" + appointment.patientId + "/allergy/" + drugId,
			contentType: "application/json",
			success:function(hasAllergy){				
				if(hasAllergy){
					$('#noAllergy').attr("hidden",true);
					$('#save_changes').attr("hidden",true);
					$('#hasAllergy').attr("hidden",false);
				}else{
					$('#noAllergy').attr("hidden",false);
					$('#save_changes').attr("hidden",false);
					$('#hasAllergy').attr("hidden",true);
					$('#duration').val("");
				}
			},
			error:function(){
				console.log('error checking allergy');
			}
		});
	});
	
	
	$('#prescribeSubstitute').click(function(){
		
		let substituteDrugId = $("#substituteDrugs option:selected").val();
		
		//Prije propisivanja zamjenskog lijeka potrebno je provjeriti da li je pacijent alergican na njega
		$.ajax({
			type:"GET", 
			url: "/api/patient/" + appointment.patientId + "/allergy/" + substituteDrugId,
			contentType: "application/json",
			success:function(hasAllergy){				
				if(hasAllergy){
					$('#allergySubstitute').attr("hidden",false);
				}else{
					$('#allergySubstitute').attr("hidden",true);
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
			
			therapies.push({"drugId": drugId,"drugName" : drugName, "duration": duration});
			reloadTherapies();
	});
	
	$('#save_prescription_substitute').submit(function(event){
			event.preventDefault();
			
			let substituteDrugId = $("#substituteDrugs option:selected").val();
			let substituteDrugName = $("#substituteDrugs option:selected").text();
			
			$('#btn_close_substitute').click();
			let duration = $('#durationSubstitute').val();
			
			therapies.push({"drugId": substituteDrugId,"drugName" : substituteDrugName, "duration": duration});
			reloadTherapies();
	});
	
	
	$("#drugs" ).change(function() {
	  	$('#div_prescribe').attr("hidden",true);
	});
	
	$('#submit_report').submit(function(event){
		event.preventDefault();
				
		$.ajax({
			type:"POST", 
			url: "/api/examination-report",
			data: JSON.stringify({ 
				appointmentId: appointmentId, 
				diagnosis: $('#diagnosis').val()}),
			contentType: "application/json",
			success:function(examinationReport){
				
				let examinationReportId = examinationReport.id;
				let therapyDTOs = [];
				for(let t of therapies){
					therapyDTOs.push({"drugId":t.drugId, "duration":t.duration, "examinationReportId":examinationReportId});
				}
				
				$.ajax({
					type:"POST", 
					url: "/api/therapy/pharmacy/" + appointment.pharmacyId,
					data: JSON.stringify(therapyDTOs),
					contentType: "application/json",
					success:function(){
						disableFields();
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully saving examination report.'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
						
					},
					error:function(){
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error saving therapies.'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					}
				});
			},
			error:function(){
				console.log("Error saving examination report");
				return;
			}
		});
	});	
	
	
	$('#newApp').click(function(){
		window.location.href = "create_appointment.html?patientId=" + appointment.patientId + "&pharmacyId=" + appointment.pharmacyId;
	});
	
	
	$('#finish').click(function(){
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
		let row = $('<tr><td>'+ t.drugName +'</td><td>' + t.duration + '</td></tr>');	
		$('#therapies').append(row);
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
				"producer":drug.producer, "contraindication":drug.contraindication, "dailyDose":drug.dailyDose, "ingredients":drug.ingredients});
				
	let option = $('<option value="' + drug.id +'">' + drug.name + '</option>');
	$('#drugs').append(option);
};

function addSubstituteDrug(drug){
	let option = $('<option value="' + drug.id +'">' + drug.name + '</option>');
	$('#substituteDrugs').append(option);
}