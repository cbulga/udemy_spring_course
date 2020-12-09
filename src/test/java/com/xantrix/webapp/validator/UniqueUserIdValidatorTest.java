package com.xantrix.webapp.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xantrix.webapp.dao.UtentiDao;
import com.xantrix.webapp.entities.Utenti;

@ExtendWith(value = MockitoExtension.class)
class UniqueUserIdValidatorTest {

	private EasyRandom easyRandom;
	@Mock
	private UtentiDao utentiDao;
	@InjectMocks
	private UniqueUserIdValidator sut;

	@BeforeEach
	public void setUp() {
		this.easyRandom = new EasyRandom();
	}
	
	@Test
	@DisplayName("isValid - Utenti to validate is null - true is returned")
	void isValid_UtentiToValidateIsNull_Ok() {
		assertThat(sut.isValid(null, null)).isTrue();
	}

	@Test
	@DisplayName("isValid - Utenti.userId to validate is null - true is returned")
	void isValid_UtentiUserIdToValidateIsNull_Ok() {
		assertThat(sut.isValid(Utenti.builder().codFidelity(easyRandom.nextObject(String.class)).build(), null)).isTrue();
	}

	@Test
	@DisplayName("isValid - Utenti.userId to validate has not already been used - true is returned")
	void isValid_UtentiUserIdToValidateHasNotAlreadyBeenUsed_Ok() {
		String userId = easyRandom.nextObject(String.class);
		String codFidelity = easyRandom.nextObject(String.class);
		when(utentiDao.existsByUserIdAndCodFidelityNot(userId, codFidelity)).thenReturn(false);
		assertThat(sut.isValid(Utenti.builder().userId(userId).codFidelity(codFidelity).build(), null)).isTrue();
	}

	@Test
	@DisplayName("isValid - Utenti.userId to validate has already been used - false is returned")
	void isValid_UtentiUserIdToValidateHasAlreadyBeenUsed_Ok() {
		String userId = easyRandom.nextObject(String.class);
		String codFidelity = easyRandom.nextObject(String.class);
		when(utentiDao.existsByUserIdAndCodFidelityNot(userId, codFidelity)).thenReturn(true);
		assertThat(sut.isValid(Utenti.builder().userId(userId).codFidelity(codFidelity).build(), null)).isFalse();
	}
}
