//ova stranica je dostupna svim korisnicima

var drugMap = {};
var drugList = null;

$(document).ready(function () {

	getAllDrugs();

});


function searchDrug() {
	
	let name = $('#name').val();
	let grade = $('#grade').val();
	let type = $("#drug_type option:selected").val();
	
	alert(name + grade + type);
	
    $.ajax({
        url: "/api/drug/search/" + name + "/" + grade + "/" + "Antiseptic",
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(drugList),
        success: function (drugs) {
			$('div#div_drugs').empty();
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
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });			
}





function getAllDrugs() {		
    $.ajax({
        url: '/api/drug',
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
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
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
        + '<label class="text-secondary mb-0">Grade:</label><br>'
        + '<label>' + drug.grade + '</label><br>'
        + '<label class="text-secondary mb-0">Type of drug:</label><br>'
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
            substituteList = 'Error! ' + jqXHR.responseJSON;
			let table = '<table style="margin-left:20px; margin-right:20px; margin-top:25px; margin-bottom:25px; width:300px;">'
					+ '<tr><td>' + substituteList + ' </td></tr>'
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
/*
    $.ajax({
        url: '/api/drug/',
        type: 'GET',
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (pharmacies) {
            if (pharmacies.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> No pharmacies found. </div>';
                $("#loading").hide();
                $("#div_drugs").prepend(alert);
            }
            else {
                for (let i = 0; i < pharmacies.length; i++) {
                    addPharmacyRow(pharmacies[i]);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseJSON + '</div>';
            $("#loading").hide();
            $("#div_drugs").prepend(alert);
        }
    });		
			
	let table = '<table style="margin-left:40px; margin-right:40px; margin-top:30px; margin-bottom:30px; width:300px;">'
		+ '<tr><td><b><strong>Pharmacies: </b></strong></td><td>' + pharmaciesList + '</td></tr>'
		+ '</table>';

	$('#pharmacies_content').empty();
	$('#pharmacies_content').append(table);
	$('#topModalSuccess').modal('show');
*/
};








