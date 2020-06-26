package com.xantrix.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xantrix.webapp.domain.Movimenti;
import com.xantrix.webapp.repository.MovimentiRepository;

@Service
@Transactional
public class MovimentiServiceImpl implements MovimentiService {

	@Autowired
	private MovimentiRepository movimentiRepository;

	@Override
	public List<Movimenti> selMovimentiByFilter(String codArt, String itemPresentInStock, String orderByField,
			String orderByAscending, String from, String max) {
		int fromValue = Integer.parseInt(from);
		int maxValue = Integer.parseInt(max);
		return movimentiRepository.selMovimentiByFilter(codArt, Boolean.valueOf(itemPresentInStock), orderByField,
				Boolean.valueOf(orderByAscending), fromValue, maxValue);
	}

	@Override
	public int selCountMovimentiByFilter(String codArt, String itemPresentInStock) {
		return movimentiRepository.selCountMovimentiByFilter(codArt, Boolean.valueOf(itemPresentInStock));
	}
}
