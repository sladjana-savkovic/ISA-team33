package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;
import rs.ac.uns.ftn.isaproject.service.pharmacy.IngredientService;

@RestController
@RequestMapping(value = "api/ingredient")
public class IngredientController {

	private IngredientService ingredientService;
	
	@Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;		
	}	
	
	@GetMapping()
	public ResponseEntity<Collection<Ingredient>> getAllIngredients(){
		try {
			Collection<Ingredient> ingredients = ingredientService.getAllIngredients();
			return new ResponseEntity<Collection<Ingredient>>(ingredients, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
		
}
