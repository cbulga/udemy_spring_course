package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.domain.Ingredienti;

public interface IngredientiService {

	public List<Ingredienti> findIngredientiByCodArt(String codArt);

	public void insIngrediente(Ingredienti ingrediente);
}
