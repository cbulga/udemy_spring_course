package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.domain.Movimenti;

public interface MovimentiService {

	public List<Movimenti> selMovimentiByFilter(String codArt, String itemPresentInStock, String orderByField,
			String orderByAscending, String from, String max);

	public int selCountMovimentiByFilter(String codArt, String itemPresentInStock);
}
