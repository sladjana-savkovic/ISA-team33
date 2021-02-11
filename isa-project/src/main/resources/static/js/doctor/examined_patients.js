checkUserRole("ROLE_DERMATOLOGIST_PHARMACIST");
var doctorId = getUserIdFromToken();
var doctorRole = getRoleFromToken();
var examinedPatients = [];
var futurePatients = [];
var sortingType = "asc";
$(document).ready(function () {
	
	clearLocalStorage();

	$.ajax({
		type:"GET", 
		url: "/api/patient/doctor/" + doctorId + "/examined",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(patients){
			$('#body_patients').empty();
			examinedPatients = patients;
			for (let p of patients){
				addPatient(p);
			}
			loadUnexaminedPatients();
		},
		error:function(){
			console.log('error getting examined patients');
		}
	});
	
	
	$('#search').submit(function(event){
		event.preventDefault();
		
		let name = "&";
		let surname = "&";
		let name_surname = $('#search_field').val().split(' ');
		
		if(name_surname[0]){
		 name = name_surname[0];
		}
		if(name_surname[1]){
		 surname = name_surname[1];
		}

		$.ajax({
			type:"POST", 
			url: "/api/patient/search/" + name + "/" + surname,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			data: JSON.stringify(examinedPatients),
			contentType: "application/json",
			success:function(searchResult){
				$('#body_patients').empty();
				for (let p of searchResult){
					addPatient(p);
				}
			},
			error:function(){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error searching patients.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
	
	
	$('#searchFuture').submit(function(event){
		event.preventDefault();
		
		let name = "&";
		let surname = "&";
		let name_surname = $('#search_field_future').val().split(' ');
		
		if(name_surname[0]){
		 name = name_surname[0];
		}
		if(name_surname[1]){
		 surname = name_surname[1];
		}

		$.ajax({
			type:"POST", 
			url: "/api/patient/search/" + name + "/" + surname,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			data: JSON.stringify(futurePatients),
			contentType: "application/json",
			success:function(searchResult){
				$('#body_patients_future').empty();
				for (let p of searchResult){
					addFuturePatient(p);
				}
			},
			error:function(){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error searching patients.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});
	});
	
});

function addPatient(patient){
	 let row = $('<tr><td style="vertical-align: middle;">'+ patient.name +'</td><td style="vertical-align: middle;">' + patient.surname + '</td>'
			+ '<td><button class="btn btn-info" type="button" id="' + patient.id +'" onclick="patientInformation(this.id,0)" style="margin-left:30px;">Information</button></td>'
			+ '<td><button class="btn btn-info" type="button" id="' + patient.id +'" onclick="patientAppointments(this.id)">Appointments</button></td></tr>');	
	$('#patients').append(row);
}

function addFuturePatient(patient){
	 let row = $('<tr><td style="vertical-align: middle;">'+ patient.name +'</td><td style="vertical-align: middle;">' + patient.surname + '</td>'
			+ '<td><button class="btn btn-info" type="button" id="' + patient.id +'" onclick="patientInformation(this.id,1)" style="margin-left:30px;">Information</button></td>'
			+ '<td><button class="btn btn-info" type="button" id="' + patient.id +'" onclick="patientAppointments(this.id)">Appointments</button></td></tr>');	
	$('#patientsFuture').append(row);
}


function patientAppointments(patientId){
	$.ajax({
		type:"GET", 
		url: "/api/appointment/patient/" + patientId + "/doctor/" + doctorId, 
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(appointments){
			$('#body_pAppointments').empty();
			for (let a of appointments){
				addAppointment(a);
			}
			$('#patientAppointments').modal('toggle');
			$('#patientAppointments').modal('show');
		},
		error:function(){
			console.log('error getting examined patients');
		}
	});
}

function patientInformation(patientId, type){
	
	$('#patientInformation').modal('toggle');
	$('#patientInformation').modal('show');
	
	if(type == 0){
		for(let p of examinedPatients){
			if(p["id"] == patientId){
				$('#pNameSurname').text(p.name + " " + p.surname);
				$('#pBirth').text(p.dateOfBirth);
				$('#pAddress').text(p.address + ", " + p.cityName + ", " + p.countryName);
				$('#pPhone').text(p.telephone);
				if(p.allergies.length > 0){
					let allergies = [];
					for(let a of p.allergies){
						allergies.push(a.name);
					}
					$('#pAllergies').text(allergies);
				}
				else{
				 	$('#pAllergies').text("No allergies");
				}
			}
		}
	}else{
		for(let p of futurePatients){
			if(p["id"] == patientId){
				$('#pNameSurname').text(p.name + " " + p.surname);
				$('#pBirth').text(p.dateOfBirth);
				$('#pAddress').text(p.address + ", " + p.cityName + ", " + p.countryName);
				$('#pPhone').text(p.telephone);
				if(p.allergies.length > 0){
					let allergies = [];
					for(let a of p.allergies){
						allergies.push(a.name);
					}
					$('#pAllergies').text(allergies);
				}
				else{
				 	$('#pAllergies').text("No allergies");
				}
			}
		}
	}
	
	//izvjestaji sa pregleda svih doktora koji su iste vrste kao ulogovani doktor
	if(getRoleFromToken() == "ROLE_DERMATOLOGIST"){
		$('#tableCaption').text("Examination reports at dermatologists");
	}else{
		$('#tableCaption').text("Examination reports at pharmacists");
	}
	$.ajax({
		type:"GET", 
		url: "/api/examination-report/patient/" + patientId + "/doctor/" + doctorId, 
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(reports){
			if(reports.length == 0){
				$('#pExaminations').attr("hidden",true);
			}
			else{
				$('#pExaminations').attr("hidden",false);
				$('#body_pExaminations').empty();
				for (let r of reports){
					addReport(r);
				}
			}
		},
		error:function(){
			console.log('error getting examined patients');
		}
	});
	
};

function addAppointment(a){
	let row = $('<tr><td style="vertical-align: middle;">'+ a.startTime.split('T')[0] + " " + a.startTime.split('T')[1] + '</td>'
			+ '<td style="vertical-align: middle;">'+ a.endTime.split('T')[0] + " " + a.endTime.split('T')[1] + '</td>'
			+ '<td style="vertical-align: middle;">' + a.pharmacyName + '</td>'
			+ '<td style="vertical-align: middle;">' + a.price + '</td>'
			+ '<td><button class="btn btn-info" type="button" id="' + a.id +'" onclick="performAppointment(this.id)">Perform</button></td></tr>');	
	$('#pAppointments').append(row);
};

function performAppointment(appointmentId){
	window.location.href = "start_examination.html"
	localStorage.setItem("appointmentId", appointmentId);
};

function addReport(report){
	let row = $('<tr><td style="vertical-align: middle;">'+ report.dateTime.split('T')[0] + " " + report.dateTime.split('T')[1] + '</td>'
			+ '<td style="vertical-align: middle;">' + report.doctor + '</td>'
			+ '<td style="vertical-align: middle;">' + report.diagnosis + '</td>'
			+ '<td style="vertical-align: middle;">' + report.therapies + '</td></tr>');	
	$('#pExaminations').append(row);
};

//Sortiranje pacijenata po imenu ili prezimenu
function sortTable(n, typeOfPatients) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  if(typeOfPatients == 0)
  	table = document.getElementById("patients");
  else
	table = document.getElementById("patientsFuture");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
};

//Sortiranje pacijenata po datumu poslednjeg pregleda
function sortDates(arg){
	data = [];
	if(arg == 0){
		data = examinedPatients;
	}
	else{
		data = futurePatients;
	}
	
	$.ajax({
		type:"POST", 
		url: "/api/examination-report/sort/date/" + sortingType,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		data: JSON.stringify(data),
		contentType: "application/json",
		success:function(sortResult){
			if(arg == 0){
				$('#body_patients').empty();
				for (let p of sortResult){
					addPatient(p);
				}
			}else{
				$('#body_patients_future').empty();
				for (let p of sortResult){
					addFuturePatient(p);
				}
			}
			
			if(sortingType == "asc"){
				sortingType = "desc";
			}
			else{
				sortingType = "asc";
			}
		},
		error:function(){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error searching patients.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});
};

$(".thHover").hover(function(){
	$(this).attr('title', 'Click to sort items');
});

function clearLocalStorage(){
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
	localStorage.removeItem("appointmentId");
}

function loadUnexaminedPatients(){
	$.ajax({
		type:"GET", 
		url: "/api/patient/doctor/" + doctorId + "/unexamined",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(patients){
			$('#body_patients_future').empty();
			futurePatients = patients;
			for (let p of patients){
				addFuturePatient(p);
			}
		},
		error:function(){
			console.log('error getting unexamined patients');
		}
	});
};