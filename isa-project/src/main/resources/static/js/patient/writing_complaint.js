checkUserRole("ROLE_PATIENT");
var patientId = getUserIdFromToken();

var subjectId = null;

$(document).ready(function () {	
	
	getDermatologists();


	/* sending */
	$('#add_feedback_form').submit(function (event) {
		$('#loading').show();
		$('#add_feedback_form').find(":submit").prop('disabled', true);
		event.preventDefault();

		var msg = $('#text_area_id').val();
		if (!msg) {
			$('#loading').hide();
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Complaint cannot be empty.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#alert').prepend(alert);
			$('#add_feedback_form').find(":submit").prop('disabled', false);
			return;
		}
		
		let path = '';
		let subjectType = '';
		if ($('#dermatologist').is(":checked")) {
			path = 'doctor';	
			subjectType = 'Dermatologist';		
		}
		else if ($('#pharmacist').is(":checked")) {
			path = 'doctor';
			subjectType = 'Pharmacist';		
		}	
		else if ($('#pharmacy').is(":checked")) {
			path = 'pharmacy';
			subjectType = null;
		}
		else {
			return;
		}
			
		subjectId = $("select#option option:selected").attr("id");
		
		var newComplaint = {
			"content": msg,
			"subjectId": subjectId,
			"patientId": patientId,
			"subjectType": subjectType
		};
		
		$.ajax({
			url: "/api/complaint/add/" + path,
			type: 'POST',
			contentType: 'application/json',
			headers: {
				'Authorization': 'Bearer ' + window.localStorage.getItem('token')
			},
			data: JSON.stringify(newComplaint),
			success: function () {
				$('#text_area_id').val(null);
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully sent!'
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#loading').hide();
				$('#add_feedback_form').find(":submit").prop('disabled', false);
				$('#alert').prepend(alert);
			},
			error: function (jqXHR) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + jqXHR.responseJSON
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#loading').hide();
				$('#add_feedback_form').find(":submit").prop('disabled', false);
				$('#alert').prepend(alert);
			}
		});
	});
});




function chooseAnOption() {
	
	if ($('#dermatologist').is(":checked")) {		
		getDermatologists();
	}		
	
	else if ($('#pharmacist').is(":checked")) {
		$('select#option').empty();
		$.ajax({
			url: "/api/doctor/1/type-of-doctor",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        	},
			type: 'GET',
			dataType: 'json',
			processData: false,
			contentType: false,
			success: function (pharmacists) {				
				$('select#option').empty();
				$('select#option').append('<option value="" disabled selected hidden>Choose option</option>');
				for (let i = 0; i < pharmacists.length; i++) {
					addDoctorInComboBox(pharmacists[i]);
				}
			},
			error: function (jqXHR) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
					'ERROR! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});						
	}		
	
	else if ($('#pharmacy').is(":checked")) {
		$('select#option').empty();
		$.ajax({
			url: "/api/pharmacy",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        	},
			type: 'GET',
			dataType: 'json',
			processData: false,
			contentType: false,
			success: function (pharmacies) {				
				$('select#option').empty();
				$('select#option').append('<option value="" disabled selected hidden>Choose option</option>');
				for (let i = 0; i < pharmacies.length; i++) {
					addPharmacyInComboBox(pharmacies[i]);
				}
			},
			error: function (jqXHR) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
					'ERROR! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});						
	}	
};


function getDermatologists() {
	$.ajax({
		url: "/api/doctor/0/type-of-doctor",
		headers: {
           	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'GET',
		dataType: 'json',
		processData: false,
		contentType: false,
		success: function (dermatologists) {				
			$('select#option').empty();
			$('select#option').append('<option value="" disabled selected hidden>Choose option</option>');
			for (let i = 0; i < dermatologists.length; i++) {
				addDoctorInComboBox(dermatologists[i]);
			}
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
				'ERROR! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});		
}

function addDoctorInComboBox(doctor) {
	let one_option = $('<option id="' + doctor.id + '" value = "' + doctor.name + '">' + doctor.name + ' ' + doctor.surname + '</option>');
	$('select#option').append(one_option);
};

function addPharmacyInComboBox(pharmacy) {
	let one_option = $('<option id="' + pharmacy.id + '" value = "' + pharmacy.name + '">' + pharmacy.name + '</option>');
	$('select#option').append(one_option);
};

