var doctorId = appConfig.doctorId;

document.addEventListener('DOMContentLoaded', function() {
	
	$.ajax({
		type:"GET", 
		url: "/api/appointment/doctor/" + doctorId,
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
		      events : appointments
		      });
		
		    calendar.render();
			
		},
		error:function(){
			console.log('error getting doctor appointments');
		}
	});
	
});
	
