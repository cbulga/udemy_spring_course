package com.xantrix.webapp.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.utils.TerminalinoConverterImpl.TerminalinoRowStatus;

@ExtendWith(MockitoExtension.class)
public class TerminalinoConverterImplTest {

	private static final Date NOW = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	private static final String ALL_RIGHT_FILE = "terminalino/42995002.txt";
	private static final String WRONG_LINE_LENGTH_FILE = "terminalino/42995003_wrong_line_length.txt";
	private static final String KO_STATUS_FILE = "terminalino/42995004_ko_status.txt";
	private static final String WRONG_QUANTITY_FILE = "terminalino/42995005_wrong_quantity.txt";
	private static final String WRONG_STATUS_FILE = "terminalino/42995006_wrong_status.txt";
	private TerminalinoConverterImpl sut;
	@Mock
	private MultipartFile multipartFile;
	@Mock
	private Resource resource;

	@BeforeEach
	public void setUp() {
		sut = new TerminalinoConverterImpl();
	}

	@Test
	@DisplayName(value = "convert with null Terminalino throws NullPointerException")
	public void convert_NullTerminalino_Exception() {
		assertThatThrownBy(() -> sut.convert(null))
				.hasMessage(TerminalinoConverterImpl.TERMINALINO_IS_REQUIRED);
	}

	@Test
	@DisplayName(value = "convert with Terminalino having null data file throws NullPointerException")
	public void convert_TerminalinoHavingNullDataFile_Exception() {
		assertThatThrownBy(() -> sut.convert(new Terminalino()))
				.hasMessage(TerminalinoConverterImpl.TERMINALINO_DATA_FILE_IS_REQUIRED);
	}

	@Test
	@DisplayName(value = "convert with data file having one row with wrong length")
	public void convert_RowWithWrongLength_Ok() throws IOException {
		// setup
		Terminalino terminalino = Terminalino.builder()
			.dataFile(new MockMultipartFile(WRONG_LINE_LENGTH_FILE, "42995003_wrong_line_length", null, this.getClass().getClassLoader().getResourceAsStream(WRONG_LINE_LENGTH_FILE)))
			.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("003")
				.trasmissioni(Arrays.asList(
						Trasmissioni.builder()
								.barCode("80061939000000")
								.data(NOW)
								.qta(5)
								.idTerminale("42995")
								.build()
						))
				.totalRowsInErrorCount(1)
				.totalRowsCount(2)
				.errors(Arrays.asList(String.format(TerminalinoConverterImpl.THE_ROW_DOES_NOT_HAVE_THE_RIGHT_LENGTH, 1, TerminalinoConverterImpl.ROW_LENGTH, 48)))
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "convert with data file having one row with KO status")
	public void convert_RowWithKoStatus_Ok() throws IOException {
		// setup
		Terminalino terminalino = Terminalino.builder()
				.dataFile(new MockMultipartFile(KO_STATUS_FILE, "42995004_ko_status", null, this.getClass().getClassLoader().getResourceAsStream(KO_STATUS_FILE)))
				.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("004")
				.trasmissioni(Arrays.asList(
						Trasmissioni.builder()
								.barCode("80061939000000")
								.data(NOW)
								.qta(5)
								.idTerminale("42995")
								.build()
						))
				.totalRowsInErrorCount(1)
				.totalRowsCount(2)
				.errors(Arrays.asList(String.format(TerminalinoConverterImpl.THE_ROW_IS_NOT_OK, 1, TerminalinoRowStatus.KO)))
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "convert with data file having one row with wrong status")
	public void convert_RowWithWrongStatus_Ok() throws IOException {
		// setup
		Terminalino terminalino = Terminalino.builder()
				.dataFile(new MockMultipartFile(WRONG_STATUS_FILE, "42995006_wrong_status", null, this.getClass().getClassLoader().getResourceAsStream(WRONG_STATUS_FILE)))
				.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("006")
				.trasmissioni(Arrays.asList(
						Trasmissioni.builder()
						.barCode("80061939000000")
						.data(NOW)
						.qta(5)
						.idTerminale("42995")
						.build()
						))
				.totalRowsInErrorCount(1)
				.totalRowsCount(2)
				.errors(Arrays.asList(String.format(TerminalinoConverterImpl.THE_ROW_HAS_A_UNEXPECTED_ROW_STATUS_VALUE, 2, "XX")))
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "convert with data file having one row with wrong quantity")
	public void convert_RowWithWrongQuantity_Ok() throws IOException {
		// setup
		Terminalino terminalino = Terminalino.builder()
				.dataFile(new MockMultipartFile(WRONG_QUANTITY_FILE, "42995005_wrong_quantity", null, this.getClass().getClassLoader().getResourceAsStream(WRONG_QUANTITY_FILE)))
				.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("005")
				.trasmissioni(Arrays.asList(
						Trasmissioni.builder()
								.barCode("80061939000000")
								.data(NOW)
								.qta(5)
								.idTerminale("42995")
								.build()
						))
				.totalRowsInErrorCount(1)
				.totalRowsCount(2)
				.errors(Arrays.asList(String.format(TerminalinoConverterImpl.THE_ROW_HAS_NOT_A_CORRECT_QUANTITY, 1, "0000XXX")))
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "convert with data file throwing a IOException")
	public void convert_MultipartFileThrowsException_Ok() throws IOException {
		// setup
		IOException occurredException = new IOException();
		when(multipartFile.getResource()).thenReturn(resource);
		when(resource.getFilename()).thenReturn("42995006");
		when(multipartFile.getInputStream()).thenThrow(occurredException);
		Terminalino terminalino = Terminalino.builder()
				.dataFile(multipartFile)
				.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("006")
				.errors(Arrays.asList(TerminalinoConverterImpl.ERRORS_OCCURRED_DURING_THE_TERMINALINO_FILE_ELABORATION))
				.occurredException(occurredException)
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}

	@Test
	@DisplayName(value = "convert with correct file")
	public void convert_Ok() throws IOException {
		// setup
		Terminalino terminalino = Terminalino.builder()
				.dataFile(new MockMultipartFile(ALL_RIGHT_FILE, "42995002", null, this.getClass().getClassLoader().getResourceAsStream(ALL_RIGHT_FILE)))
				.build();
		TerminalinoResult expected = TerminalinoResult.builder()
				.numeroTerminalino("42995")
				.codicePuntoVendita("002")
				.trasmissioni(Arrays.asList(
						Trasmissioni.builder()
								.barCode("8005840004760")
								.data(NOW)
								.qta(2)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8006040249913")
								.data(NOW)
								.qta(1)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("800619390000")
								.data(NOW)
								.qta(5)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8006363015219")
								.data(NOW)
								.qta(1)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8006450013111")
								.data(NOW)
								.qta(10)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8006793100000")
								.data(NOW)
								.qta(3)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8006890609578")
								.data(NOW)
								.qta(2)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8007750085372")
								.data(NOW)
								.qta(12)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8008250112698")
								.data(NOW)
								.qta(5)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8008460027300")
								.data(NOW)
								.qta(22)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8008970003214")
								.data(NOW)
								.qta(1)
								.idTerminale("42995")
								.build(),
						Trasmissioni.builder()
								.barCode("8009470000307")
								.data(NOW)
								.qta(1)
								.idTerminale("42995")
								.build()
						))
				.totalRowsInErrorCount(0)
				.totalRowsCount(12)
				.build();
		// test
		TerminalinoResult actual = sut.convert(terminalino);
		// assertions
		assertThat(actual)
				.isNotNull()
				.isEqualTo(expected);
	}
}
