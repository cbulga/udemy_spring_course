package com.xantrix.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xantrix.webapp.domain.Articoli;
import com.xantrix.webapp.domain.Ingredienti;

public class IngredientiMapper implements RowMapper<Ingredienti> {

	protected static final String INFO = "INFO";
	protected static final String CODART = "CODART";
	protected static final ArticoliMapper articoliMapper = new ArticoliMapper();

	@Override
	public Ingredienti mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ingredienti ingredienti = Ingredienti.builder().codArt(rs.getString(CODART)).info(rs.getString(INFO)).build();
		Articoli articoli = articoliMapper.mapRow(rs, rowNum);
		ingredienti.setArticoli(articoli);
		return ingredienti;
	}
}
