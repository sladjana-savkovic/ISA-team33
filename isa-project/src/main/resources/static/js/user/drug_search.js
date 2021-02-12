//ova stranica je dostupna svim korisnicima

var drugMap = {};
var drugList = null;

$(document).ready(function () {
	
	clearLocalStorage();

	getAllDrugs();
	
	
	/*Search on submit*/
	$('form#form_id').submit(function (event) {
		event.preventDefault();	
		searchDrug();
	});
});


function searchDrug() {	
	$('div#div_drugs').empty();	
	let name = $('#name').val();
	let grade = $('#grade').val();
	let type = $("#drug_type option:selected").val();	
	if (grade.length == 0) {
		grade = -1;
	}	
	searchDTO = {
		"name": name,
		"grade": grade,
		"type": type,
		"drugDTOs": drugList
	}		
    $.ajax({
        url: "/api/drug/search",
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(searchDTO),
        success: function (drugs) {
            if (drugs.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No drugs found. </div>';
                $("#loading").hide();
                $("#div_drugs").prepend(alert);
            }
            else {
                for (let i = 0; i < drugs.length; i++) {
                    addDrugRow(drugs[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseText + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });			
}


function getAllDrugs() {		
    $.ajax({
        url: '/api/drug/all',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (drugs) {
			drugList = drugs;
            if (drugs.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No drugs found. </div>';
                $("#loading").hide();
                $("#div_drugs").prepend(alert);
            }
            else {
                for (let i = 0; i < drugs.length; i++) {
                    addDrugRow(drugs[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseText + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });	
}


function addDrugRow(drug) {
		
	drugMap[drug.id] = drug;
	
	button = '<div class="card-footer">'
		+ '<button type = "button" class="btn btn-outline-primary float-left"'
		+ 'id="' + drug.id + '" onclick="showSpecification(this.id)"'
		+ '>Specification</button >'
		+ '<button type = "button" class="btn btn-outline-primary float-left"'
		+ 'id="' + drug.id + '" onclick="showSubstituteDrugs(this.id)"'
		+ '>Substitute</button >'
		+ '<button type = "button" class="btn btn-outline-primary float-left"'
		+ 'id="' + drug.id + '" onclick="showPharmacies(this.id)"'
		+ '>Pharmacies</button >'		
		+ '</div >'; 
			
    let divElement = $(
        '<div class="row">'
        + '<div class="col mb-4">'
        + '<div class="card">'
        + '<div class="card-header bg-info text-white">'
        + '<h5 class="card-title mb-0">' + drug.name + '</h5></div>'
        + '<div class="card-body p-3">'
        + '<label class="text-secondary mb-0">Grade: &nbsp; </label>'
        + '<label>' + drug.grade + '</label><br>'
        + '<label class="text-secondary mb-0">Type of drug: &nbsp; </label>'
        + '<label>' + drug.typeOfDrug + '</label><br>'
        + '</div>' + button + '</div></div></div>'
    );
    $('div#div_drugs').append(divElement);
}


function showSubstituteDrugs(drugId) {
	substituteList = '';				
    $.ajax({
        url: '/api/drug/' + drugId + '/substitute',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (substituteDrugs) {	
            if (substituteDrugs.length == 0) {	
				substituteList = 'No substitute drugs found.';
				let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
					+ '<tr><td><b><strong> ' + substituteList + '</strong></b> </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');
            }
            else {
				substituteList = substituteDrugs[0].name;
                for (let i = 1; i < substituteDrugs.length; i++) {
					substituteList = substituteList + ', ' + substituteDrugs[i].name;
                }
				let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
					+ '<tr><td><b><strong>Substitute drugs: </strong></b>' + substituteList + ' </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');	
            }
        },
        error: function (jqXHR) {
			let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:300px;">'
					+ '<tr><td> Error! ' + jqXHR.responseText + ' </td></tr>'
					+ '</table>';		
				$('#substitute_content').empty();
				$('#substitute_content').append(table);
				$('#centerModalSuccess').modal('show');
        }
    });			
}


function showSpecification(drugId) {	
	ingredientsList = drugMap[drugId].ingredients[0].name;
	for (let i = 1; i < drugMap[drugId].ingredients.length; i++) {		
		ingredientsList = ingredientsList + ', ' + drugMap[drugId].ingredients[i].name;
	}
		
	let table1 = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:450px;">'
		+ '<tr><td><b><strong>Contraindication: </strong></b>' + drugMap[drugId].contraindication + ' </td></tr>'
		+ '<tr><td><b><strong>Daily dose: </strong></b>' + drugMap[drugId].dailyDose + '</td></tr>'
		+ '<tr><td><b><strong>Ingredients: </strong></b>' + ingredientsList + ' </td></tr>'
		+ '</table>';

	$('#report_content').empty();
	$('#report_content').append(table1);
	$('#bottomModalSuccess').modal('show');
};


function showPharmacies(drugId) {
    $.ajax({
        url: '/api/pricelist/' + drugId + '/drug',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (pharmacies) {
            if (pharmacies.length == 0) {
				$('#pharmaciesTable').empty();
				$('#pharmaciesTable').append('<tr><td><b><strong> No pharmacy found </b></strong></td></tr>');
				$('#topModalSuccess').modal('show');
            }
            else {
				$('#pharmaciesTable').empty();
				$('#pharmaciesTable').append('<tr><th> Pharmacy </th><th> Price (din)</th></tr>');
	            for (let i = 0; i < pharmacies.length; i++) {
					pharmacy = '<tr><td>' + pharmacies[i].pharmacyName + '</td><td>' + pharmacies[i].price + '</td></tr>' ;
					$('#pharmaciesTable').append(pharmacy);
                }				
				$('#topModalSuccess').modal('show');
            }
        },
        error: function (jqXHR) {
			$('#pharmaciesTable').empty();
			$('#pharmaciesTable').append('<tr><td> Error! ' + jqXHR.responseText + ' </td></tr>');
			$('#topModalSuccess').modal('show');
        }
    });		
};


function clearLocalStorage(){
	localStorage.removeItem("patientId");
	localStorage.removeItem("pharmacyId");
	localStorage.removeItem("appointmentId");
}

