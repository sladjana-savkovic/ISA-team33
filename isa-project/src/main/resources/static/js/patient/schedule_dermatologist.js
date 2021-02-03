var patientId = 3;
var pharmacyId = 1;
$(document).ready(function(){
	
	getAllAppointments();
	
	/*$('form#search').submit(function(event){
		event.preventDefault()
		

		let sorting = $('#sort').val()
		$("#apartment-cards").empty();
		var user = JSON.parse(localStorage.getItem('user'));
		if(user.role!=0){
			$('#users').hide()
		}
		console.log(user)
			$.ajax({
				type:"GET",
				url: "/api/patient",
				contentType:"application/json",
				success:function(apartments){
					apartments.forEach(a => appendApartment(user,a))
				},
				error:function(error){
					alert(error)
				}
			})
		})
		
	*/	
})

const getAllAppointments = () => {
	$("#apartment-cards").empty();
	$.ajax({
		type:"GET",
		url:"/api/appointment/pharmacy/"+pharmacyId+"/created",
		contentType:"application/json",
		success:function(appointments){
			appointments.forEach(a => appendAppointment(a));
		},
		error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
		}
	})
}
const appendAppointment = (apartment) => {
	let startTime = apartment.startTime.split("T")[0] + ", " + apartment.startTime.split("T")[1]+ "h";
	let name;let surname;let grade;
	$.ajax({
		type:"GET", 
		url: "/api/doctor/" + apartment.idDoctor,
		contentType: "application/json",
		success:function(doctor){
			name = doctor.name;
			surname = doctor.surname;
			grade = doctor.averageGrade;
			let oneElement = `
		<div class="card bg-info mx-5 my-2">
		<div class="row">
			<div id="${apartment.id}" class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="font-weight-bold text-light">Start time: ${startTime}</h3>
				
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Doctor: ${name} ${surname}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Doctors grade: ${grade}</h3>
			</div>
			<div class="col d-flex flex-column justify-content-center align-items-center">
				<h3 class="text-light">Price: ${apartment.price}$</h3>
				`
				
			oneElement = oneElement + `
			<button class="btn btn-success btn-light"  id="modalButton" onclick="scheduleAppointment('${apartment.id}')">Schedule</button>
			`
		
	oneElement = oneElement + `</div></div></div>`
	$("#apartment-cards").append(oneElement);
		},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	});
	
}


const scheduleAppointment = apartmentId =>{
	$.ajax({
		type:"PUT",
		url: "/api/appointment/"+ apartmentId+"/patient/"+patientId+"/schedule",
		success:function(){
			window.location.reload()
		},
		error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'ERROR! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
		}
	})
}



const search = url => {
	$("#apartment-cards").empty();
	var user = JSON.parse(localStorage.getItem('user'));
	if(user.role!=0){
		$('#users').hide()
	}
	console.log(user)
	$.ajax({
		type:"GET",
		url: url,
		contentType:"application/json",
		success:function(apartments){
			apartments.forEach(a => appendApartment(user,a))
		},
		error:function(error){
			alert(error)
		}
	})
}

const submitReservation = event => {
	event.preventDefault();
	const apartmentId = $("#apartment-id").val();
	const startDate = $("#start-date").val();
	const nights = $("#nights").val();
	const message = $("#message").val();
	
	
	$.ajax({
			type:"POST",
			url:"rest/reservation",
			contentType: "application/json",
			data: JSON.stringify({
				apartmentId: apartmentId,
				startDate: Date.parse(startDate),
				nights: +nights,
				message: message
			}),
			success: function(reservation){
				alert("Reservation successfully created")
				window.location.href="homepage.html"
			},
			error: function(error){
				console.log(error)
			}
			
		})
	
}

