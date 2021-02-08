$(document).ready(function () {	
	
	
	if(!isEnabledFromToken()){
		$('#changePassModal').modal('toggle');
		$('#changePassModal').modal('show');
	}

	
	
});