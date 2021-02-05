checkUserRole("ROLE_DERMATOLOGIST_PHARMACIST");
var doctorId = getUserIdFromToken();

document.addEventListener('DOMContentLoaded', function() {
	
	clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/appointment/doctor/" + doctorId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(appointments){
						
			var calendarEl = document.getElementById('calendar');
			
		    var calendar = new FullCalendar.Calendar(calendarEl, {
			      headerToolbar: {
			        left: 'prev,next today',
			        center: 'title',
			        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
			      },
			      initialDate: '2021-02-01',
			      navLinks: true, // can click day/week names to navigate views
			      nowIndicator: true,
			      weekNumbers: true,
			      weekNumberCalculation: 'ISO',
			      selectable: true,
			      dayMaxEvents: true, // allow "more" link when too many events
			      events : appointments,
				  eventClick: function(info) {
						info.jsEvent.preventDefault(); // don't let the browser navigate
	
						window.location.href = "start_examination.html"
						localStorage.setItem("appointmentId", info.event.url);
				  }
		      });
		
		    calendar.render();
			
		},
		error:function(xhr){
			console.log(xhr.responseText);
		}
	});
	
});

function clearLocalStorage(){
	localStorage.removeItem("appointmentId");
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
}
	
