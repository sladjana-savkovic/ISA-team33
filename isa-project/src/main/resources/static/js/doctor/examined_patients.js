var doctorId = appConfig.doctorId;
var examinedPatients = [];
$(document).ready(function () {

	$.ajax({
		type:"GET", 
		url: "/api/examination-report/doctor/" + doctorId,
		contentType: "application/json",
		success:function(patients){
			$('#body_patients').empty();
			examinedPatients = patients;
			for (let p of patients){
				addPatient(p);
			}
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
			url: "/api/examination-report/search/" + name + "/" + surname,
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
	
});

function addPatient(patient){
	 let row = $('<tr><td style="vertical-align: middle;">'+ patient.name +'</td><td style="vertical-align: middle;">' + patient.surname + '</td>'
			+ '<td style="vertical-align: middle;">' + patient.dateOfLastExamination + '</td>'
			+ '<td><button class="btn btn-info" type="button" id="' + patient.id +'" onclick="patientDetail(this.id)">Details</button></td></tr>');	
	$('#patients').append(row);
}

function patientDetail(patientId){
	
	for(let p of examinedPatients){
		if(p["id"] == patientId){
			$('#pNameSurname').text(p.name + " " + p.surname);
			$('#pBirth').text(p.dateOfBirth);
			$('#pAddress').text(p.address);
			$('#pAllergies').text(p.allergies);
			$('#patientInfo').modal('toggle');
			$('#patientInfo').modal('show');
		}
	}
};

function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("patients");
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
}

