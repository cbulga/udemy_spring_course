package com.xantrix.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.repository.TrasmissioniRepository;

@Service
public class TrasmissioniServiceImpl implements TrasmissioniService {

	@Autowired
	private TrasmissioniRepository trasmissioniRepository;

	@Override
	public List<Trasmissioni> findTrasmissioniByIdTerminale(String idTerminale) {
		return trasmissioniRepository.findTrasmissioniByIdTerminale(idTerminale);
	}
}
