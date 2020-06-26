package com.xantrix.webapp.repository;

import java.util.List;

import com.xantrix.webapp.domain.Ingredienti;

public interface IngredientiRepository {

	public List<Ingredienti> findIngredientiByCodArt(String codArt);

	public void insIngrediente(Ingredienti ingrediente);
}
