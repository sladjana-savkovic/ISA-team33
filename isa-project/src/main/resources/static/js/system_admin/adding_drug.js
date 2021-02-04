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






//************************************** */
function getAllPharmacy() {
	$('select#role').on('change', function () {

		var id = $(this).children(":selected").attr("id");
		
		if (parseInt(id) == 2) {
			$.ajax({
				url: "/api/pharmacy",
				type: 'GET',
				dataType: 'json',
				processData: false,
				contentType: false,
				success: function (pharmacies) {
					
					$('div#divPharmacy').empty();
					$('div#divInfo').append('<div id="divPharmacy" class="form-row"><div class="form-group col"> ' + 
                                    '<label for="pharmacy" class="text-secondary">Pharmacy *</label> ' +                                
                                    '<select id="pharmacy" class="custom-select" required> ' +
                                    '</select>' + 
                                    '</div></div>');									
				
					$('select#pharmacy').append('<option value="" disabled selected hidden>Choose pharmacy</option>');
					for (let i = 0; i < pharmacies.length; i++) {
						addPharmacyInComboBox(pharmacies[i]);
					}
				},
				error: function (jqXHR) {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' +
						'Error getting pharmacies! ' + jqXHR.responseJSON + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				}
			});
		}
		else {
			$('div#divPharmacy').empty();
		}	
	});	
};


function addPharmacyInComboBox(pharmacy) {
	let pharmacy_option = $('<option id="' + pharmacy.id + '" value="' + pharmacy.name + '">' + pharmacy.name + '</option>');
	$('select#pharmacy').append(pharmacy_option);
};

