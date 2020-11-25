package com.xantrix.webapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.repository.TrasmissioniRepository;
import com.xantrix.webapp.utils.Converter;
import com.xantrix.webapp.utils.TerminalinoResult;

@ExtendWith(MockitoExtension.class)
public class TerminalinoServiceImplTest {

	private TerminalinoServiceImpl sut;
	@Mock
	private Converter<TerminalinoResult, Terminalino> terminalinoConverter;
	@Mock
	private TrasmissioniRepository trasmissioniRepository;
	private EasyRandom easyRandom;

	@BeforeEach
	public void setUp() {
		easyRandom = new EasyRandom();
		sut = new TerminalinoServiceImpl();
		sut.terminalinoConverter = terminalinoConverter;
		sut.trasmissioniRepository = trasmissioniRepository;
	}

	@Test
	@DisplayName(value = "processTerminalino with null terminalino throws a NullPointerException")
	void processTerminalino_NullTerminalino_Exception() {
		assertThatThrownBy(() -> sut.processTerminalino(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(TerminalinoServiceImpl.TERMINALINO_IS_REQUIRED);
	}

	@Test
	@DisplayName(value = "processTerminalino with not null terminalino - ok")
	void processTerminalino_NotNullTerminalino_Ok() {
		// setup
		Terminalino terminalino = new Terminalino();
		List<Trasmissioni> trasmissioni = easyRandom.objects(Trasmissioni.class, 3).collect(Collectors.toList());
		TerminalinoResult expected = TerminalinoResult.builder()
				.codicePuntoVendita(easyRandom.nextObject(String.class))
				.numeroTerminalino(easyRandom.nextObject(String.class))
				.trasmissioni(trasmissioni)
				.build();
		when(terminalinoConverter.convert(terminalino)).thenReturn(expected);
		// test
		TerminalinoResult actual = sut.processTerminalino(terminalino);
		// assertions
		verify(terminalinoConverter).convert(terminalino);
		verify(trasmissioniRepository).insTrasmissioni(trasmissioni);
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "processTerminalino with not null terminalino, but terminalino result has errors, so only valid records are stored to database - ok")
	void processTerminalino_NotNullTerminalinoButTerminalinoResultHasErrors_Ok() {
		// setup
		Terminalino terminalino = new Terminalino();
		List<Trasmissioni> trasmissioni = easyRandom.objects(Trasmissioni.class, 3).collect(Collectors.toList());
		TerminalinoResult expected = TerminalinoResult.builder()
				.codicePuntoVendita(easyRandom.nextObject(String.class))
				.numeroTerminalino(easyRandom.nextObject(String.class))
				.trasmissioni(trasmissioni)
				.errors(easyRandom.objects(String.class, 2).collect(Collectors.toList()))
				.build();
		when(terminalinoConverter.convert(terminalino)).thenReturn(expected);
		// test
		TerminalinoResult actual = sut.processTerminalino(terminalino);
		// assertions
		verify(terminalinoConverter).convert(terminalino);
		verify(trasmissioniRepository).insTrasmissioni(ArgumentMatchers.<List<Trasmissioni>>any());
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}
}
