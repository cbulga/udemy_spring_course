package com.xantrix.webapp.repository;

import java.util.List;

import com.xantrix.webapp.domain.Movimenti;

public interface MovimentiRepository {

	public int selCountMovimentiByFilter(String codArt, boolean itemPresentInStock);

	public List<Movimenti> selMovimentiByFilter(String codArt, boolean itemPresentInStock, String orderByField,
			boolean orderByAscending, int from, int max);
}
