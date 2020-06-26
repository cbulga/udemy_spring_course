package com.xantrix.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.domain.Ingredienti;
import com.xantrix.webapp.repository.IngredientiRepository;

@Service
public class IngredientiServiceImpl implements IngredientiService {

	@Autowired
	private IngredientiRepository ingredientiRepository;

	@Override
	public List<Ingredienti> findIngredientiByCodArt(String codArt) {
		return ingredientiRepository.findIngredientiByCodArt(codArt);
	}

	@Override
	public void insIngrediente(Ingredienti ingrediente) {
		ingredientiRepository.insIngrediente(ingrediente);
	}
}
