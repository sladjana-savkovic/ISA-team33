var drugIngredientsList = new Array(); 

$(document).ready(function () {
		
	getAllIngredients();	
	
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
		let typeOfDrug = $("#type_of_drug option:selected").attr("id");;
		let drugForm = $("#drug_form option:selected").attr("id");;
		let prescription = 0;
		if (document.getElementById('prescription').checked) {
			prescription = 1;
		}
			
		
		if (!$.isNumeric(daily_dose)) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Field daily dose must have digits.'
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}				
		else {						

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
	if (!drugIngredientsList.includes(ingredientId)) {		
		drugIngredientsList.push(ingredientId);		
		$('table#table_ingredients').append('<tr><td>' + $("#ingredients option:selected").val() + ' </td> </tr>');		
	}			
};




