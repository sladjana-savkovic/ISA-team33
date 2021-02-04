var drugIngredientsList = new Array(); 
var substituteDrugList = new Array(); 

$(document).ready(function () {
		
	getAllIngredients();
	getAllDrugs();	
	
	/*Add drog on submit*/
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let name = $('#name').val();
		let code = $('#code').val();
		let contraindication = $('#contraindication').val();
		let notes = $('#notes').val();
		let producer = $('#producer').val();
		let daily_dose = $('#daily_dose').val();
		let typeOfDrug = $("#type_of_drug option:selected").val();
		let drugForm = $("#drug_form option:selected").val();
		let prescription = false;
		if (document.getElementById('prescription').checked) {
			prescription = true;
		}
			
		if (!$.isNumeric(daily_dose)) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Field daily dose must have digits.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		if (drugIngredientsList.length == 0) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Ingredients list is empty!'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}						
		else {																										
			var newDrug = {
				"name": name,
				"code": code,
				"contraindication": contraindication,
				"notes": notes,
				"producer": producer,
				"typeOfDrug": typeOfDrug,
				"drugForm": drugForm,
				"dailyDose": parseInt(daily_dose),
				"prescription": prescription,
				"substituteDrugList": substituteDrugList,
				"drugIngredientsList": drugIngredientsList
			};				
			
			$.ajax({
				url: "/api/drug",
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(newDrug),
				success: function () {
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful!'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						 'Unsuccessful! ' +jqXHR.responseText + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});												
					
		}
	});
});




function getAllIngredients() {
	$.ajax({
		url: "/api/ingredient",
		type: 'GET',
		contentType: "application/json",
		success: function (ingredients) {			
			for (let i = 0; i < ingredients.length; i++) {
				addIngredientInComboBox(ingredients[i]);
			}
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
				'Error getting ingredients! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});		
}

function addIngredientInComboBox(ingredient) {
	let ingredient_option = $('<option id="' + ingredient.id + '" value="' + ingredient.name + '">' + ingredient.name + '</option>');
	$('select#ingredients').append(ingredient_option);
};

function chooseIngredient() {	
	let ingredientId = $("#ingredients option:selected").attr("id");	
	if (!drugIngredientsList.includes(parseInt(ingredientId))){		
		drugIngredientsList.push(parseInt(ingredientId));		
		$('table#table_ingredients').append('<tr><td>' + $("#ingredients option:selected").val() + ' </td> </tr>');		
	}			
};



function getAllDrugs() {
	$.ajax({
		url: "/api/drug",
		type: 'GET',
		contentType: "application/json",
		success: function (drugs) {			
			for (let i = 0; i < drugs.length; i++) {
				addDrugsInComboBox(drugs[i]);
			}
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
				'Error getting drugs! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});		
}

function addDrugsInComboBox(drug) {
	let drug_option = $('<option id="' + drug.id + '" value="' + drug.name + '">' + drug.name + '</option>');
	$('select#substitute_drugs').append(drug_option);
};

function chooseSubstituteDrugs() {
	let drugId = $("#substitute_drugs option:selected").attr("id");	
	if (!substituteDrugList.includes(parseInt(drugId))) {		
		substituteDrugList.push(parseInt(drugId));		
		$('table#table_substitute_drugs').append('<tr><td>' + $("#substitute_drugs option:selected").val() + ' </td> </tr>');		
	}		
}

