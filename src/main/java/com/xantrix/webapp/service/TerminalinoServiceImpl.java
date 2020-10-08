package com.xantrix.webapp.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.repository.TrasmissioniRepository;
import com.xantrix.webapp.utils.Converter;
import com.xantrix.webapp.utils.TerminalinoConverterImpl;
import com.xantrix.webapp.utils.TerminalinoResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TerminalinoServiceImpl implements TerminalinoService {

	protected static final String TERMINALINO_IS_REQUIRED = "Terminalino is required";
	protected Converter<TerminalinoResult, Terminalino> terminalinoConverter;
	@Autowired
	protected TrasmissioniRepository trasmissioniRepository;

	public TerminalinoServiceImpl() {
		this.terminalinoConverter = new TerminalinoConverterImpl();
	}

	@Override
	public TerminalinoResult processTerminalino(Terminalino terminalino) {
		Objects.requireNonNull(terminalino, TERMINALINO_IS_REQUIRED);
		TerminalinoResult result = terminalinoConverter.convert(terminalino);
		log.debug("Deleting data about the terminal {}", result.getNumeroTerminalino());
		boolean deleteDone = trasmissioniRepository.deleteByIdTerminale(result.getNumeroTerminalino());
		log.debug("Delete done for idTerminalino {}: {}", result.getNumeroTerminalino(), deleteDone ? "YES" : "NO");

		trasmissioniRepository.insTrasmissioni(result.getTrasmissioni());

		return result;
	}
}
