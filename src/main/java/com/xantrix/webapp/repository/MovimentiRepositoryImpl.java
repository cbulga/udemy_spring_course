package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.domain.Movimenti;

@Repository
public class MovimentiRepositoryImpl implements MovimentiRepository {

	private static final String SEL_MOVIMENTI_BY_FILTER_SQL = "CALL Sp_SelMov2('%s', %b, '%s', %b, %d, %d);";
	private static final String SEL_COUNT_MOVIMENTI_BY_FILTER_SQL = "CALL Sp_SelCountMov2('%s', %b);";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Movimenti> selMovimentiByFilter(String codArt, boolean itemPresentInStock, String orderByField,
			boolean orderByAscending, int from, int max) {
		return jdbcTemplate.query(String.format(SEL_MOVIMENTI_BY_FILTER_SQL, codArt, itemPresentInStock, orderByField,
				orderByAscending, from, max), new MovimentiMapper(from));
	}

	@Override
	public int selCountMovimentiByFilter(String codArt, boolean itemPresentInStock) {
		return jdbcTemplate.queryForObject(String.format(SEL_COUNT_MOVIMENTI_BY_FILTER_SQL, codArt, itemPresentInStock),
				Integer.class);
	}
}
