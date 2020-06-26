package com.xantrix.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xantrix.webapp.domain.Ingredienti;
import com.xantrix.webapp.service.IngredientiService;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController {

	@Autowired
	private IngredientiService ingredientiService;

	@GetMapping(value = "/cerca/{filter}")
	public String getIngredientiByFilter(@PathVariable(name = "filter") String filter, Model model) {
		List<Ingredienti> ingredienti = ingredientiService.findIngredientiByCodArt(filter);
		model.addAttribute("Ingredienti", ingredienti);
		model.addAttribute("size", ingredienti.size());
		return "ingredienti";
	}

	@GetMapping(value = "/aggiungi")
	public String insIngredienti(Model model) {
		Ingredienti ingredienti = Ingredienti.builder().build();

//		model.addAttribute("Titolo", "Inserimento Nuovo Ingrediente");
		model.addAttribute("newIngrediente", ingredienti);

		return "insIngredienti";
	}

	@PostMapping(value = "/aggiungi")
	public String manageInsIngredienti(@ModelAttribute("newIngrediente") Ingredienti ingrediente,
			BindingResult result) {
		if (result.getSuppressedFields().length > 0)
			throw new RuntimeException("ERRORE: Tentativo di eseguire il binding dei seguenti campi NON consentiti: "
					+ StringUtils.arrayToCommaDelimitedString(result.getSuppressedFields()));
		else {
			ingredientiService.insIngrediente(ingrediente);
		}

		return "redirect:/ingredienti/cerca/" + ingrediente.getCodArt();
	}

	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("codArt", "info");
	}
}
