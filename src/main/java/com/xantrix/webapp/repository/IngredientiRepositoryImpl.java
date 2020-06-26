package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.domain.Ingredienti;

@Repository
public class IngredientiRepositoryImpl implements IngredientiRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ingredienti> findIngredientiByCodArt(String codArt) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT i.*, a.* FROM ingredienti i INNER JOIN articoli a on a.CODART = i.CODART WHERE i.CODART = ?");
		return jdbcTemplate.query(sql.toString(), new Object[] { codArt }, new IngredientiMapper());
	}

	@Override
	public void insIngrediente(Ingredienti ingrediente) {
		StringBuilder sql = new StringBuilder();
		sql.append("CALL Sp_InsIngredienti('").append(ingrediente.getCodArt()).append("', '")
				.append(ingrediente.getInfo()).append("');");
		jdbcTemplate.update(sql.toString());
	}
}
