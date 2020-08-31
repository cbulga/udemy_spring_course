package com.xantrix.webapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xantrix.webapp.domain.Trasmissioni;

@ExtendWith(MockitoExtension.class)
public class TrasmissioniRepositoryImplTest {

	@InjectMocks
	private TrasmissioniRepositoryImpl sut;
	@Mock
	private JdbcTemplate jdbcTemplate;
	@Captor
	private ArgumentCaptor<Object> jdbcTemplateCaptor;
	private EasyRandom easyRandom;

	@BeforeEach
	public void setUp() {
		easyRandom = new EasyRandom();
//		sut = new TrasmissioniRepositoryImpl();
//		sut.jdbcTemplate = jdbcTemplate;
	}

	@Test
	@DisplayName(value = "insTrasmissioni called with null trasmissioni throws NullPointerException")
	public void insTrasmissioni_NullTrasmissioni_Exception() {
		assertThatThrownBy(() -> sut.insTrasmissioni(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(TrasmissioniRepositoryImpl.TRASMISSIONI_CANNOT_BE_NULL);
	}

	@Test
	@DisplayName(value = "insTrasmissioni called with not null trasmissioni - ok")
	public void insTrasmissioni_NotNullTrasmissioni_Ok() {
		// setup
		List<Trasmissioni> trasmissioni = easyRandom.objects(Trasmissioni.class, 3).collect(Collectors.toList());
		// test
		sut.insTrasmissioni(trasmissioni);
		// assertions
		verify(jdbcTemplate, times(3)).update(eq(TrasmissioniRepositoryImpl.CALL_SP_INS_TRASMISSIONI), jdbcTemplateCaptor.capture());
		assertThat(jdbcTemplateCaptor).isNotNull();
		List<Object> capturedParameters = jdbcTemplateCaptor.getAllValues();
		assertThat(capturedParameters.get(0)).isNotNull().isEqualTo(trasmissioni.get(0).getIdTerminale());
		assertThat(capturedParameters.get(1)).isNotNull().isEqualTo(trasmissioni.get(0).getData());
		assertThat(capturedParameters.get(2)).isNotNull().isEqualTo(trasmissioni.get(0).getBarCode());
		assertThat(capturedParameters.get(3)).isNotNull().isEqualTo(trasmissioni.get(0).getQta());
		assertThat(capturedParameters.get(4)).isNotNull().isEqualTo(trasmissioni.get(1).getIdTerminale());
		assertThat(capturedParameters.get(5)).isNotNull().isEqualTo(trasmissioni.get(1).getData());
		assertThat(capturedParameters.get(6)).isNotNull().isEqualTo(trasmissioni.get(1).getBarCode());
		assertThat(capturedParameters.get(7)).isNotNull().isEqualTo(trasmissioni.get(1).getQta());
		assertThat(capturedParameters.get(8)).isNotNull().isEqualTo(trasmissioni.get(2).getIdTerminale());
		assertThat(capturedParameters.get(9)).isNotNull().isEqualTo(trasmissioni.get(2).getData());
		assertThat(capturedParameters.get(10)).isNotNull().isEqualTo(trasmissioni.get(2).getBarCode());
		assertThat(capturedParameters.get(11)).isNotNull().isEqualTo(trasmissioni.get(2).getQta());
	}
}
