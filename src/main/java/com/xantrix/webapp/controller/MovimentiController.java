package com.xantrix.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xantrix.webapp.domain.Movimenti;
import com.xantrix.webapp.service.MovimentiService;

@Controller
@RequestMapping("/movimenti")
public class MovimentiController {

	@Autowired
	private MovimentiService movimentiService;

	// http://localhost:8080/alphashop/movimenti/cerca/pasta?itemPresentInStock=true&orderByField=DESART&orderByAscending=true&from=0&max=10
	@RequestMapping(value = "/cerca/{filter}", method = RequestMethod.GET)
	public String getMovimentiByFilter(@PathVariable("filter") String filter,
			@RequestParam("itemPresentInStock") String itemPresentInStock,
			@RequestParam("orderByField") String orderByField,
			@RequestParam("orderByAscending") String orderByAscending, @RequestParam("from") String from,
			@RequestParam("max") String max, Model model) {
		List<Movimenti> result = movimentiService.selMovimentiByFilter(filter, itemPresentInStock, orderByField,
				orderByAscending, from, max);
		int count = movimentiService.selCountMovimentiByFilter(filter, itemPresentInStock);

		model.addAttribute("movimenti", result);
		model.addAttribute("movimentiCount", count);
		model.addAttribute("Titolo", "Ricerca Movimenti");

		return "movimenti";
	}
}
