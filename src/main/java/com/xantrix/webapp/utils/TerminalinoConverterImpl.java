package com.xantrix.webapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.domain.Trasmissioni;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link TerminalinoConverter} useful to create a {@link List}&lt;Trasmissioni&gt; using the provided {@link Terminalino}.
 * 
 * @author cristian
 */
@Slf4j
public class TerminalinoConverterImpl implements Converter<TerminalinoResult, Terminalino> {

	protected static final String THE_ROW_HAS_A_UNEXPECTED_ROW_STATUS_VALUE = "Error: the row %s has a unexpected status value: %s";
	protected static final String ERRORS_OCCURRED_DURING_THE_TERMINALINO_FILE_ELABORATION = "Errors occurred during the terminalino file elaboration";
	protected static final String THE_ROW_HAS_NOT_A_CORRECT_QUANTITY = "Error: the row %s has not a correct quantity (%s)";
	protected static final String THE_ROW_IS_NOT_OK = "Error: the row %s is not OK: %s";
	protected static final String THE_ROW_DOES_NOT_HAVE_THE_RIGHT_LENGTH = "Error: the row %s does not have the right length of %s chars: %s";
	protected static final String TERMINALINO_IS_REQUIRED = "terminalino is required";
	protected static final String TERMINALINO_DATA_FILE_IS_REQUIRED = "terminalino data file is required";
	protected static int ROW_LENGTH = 26;

	@Override
	public TerminalinoResult convert(Terminalino terminalino) {
		Objects.requireNonNull(terminalino, TERMINALINO_IS_REQUIRED);
		Objects.requireNonNull(terminalino.getDataFile(), TERMINALINO_DATA_FILE_IS_REQUIRED);
		String fileName = terminalino.getDataFile().getResource().getFilename();
		Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		String numeroTerminalino = fileName.substring(0, 5);
		String codicePuntoVendita = fileName.substring(5, 8);
		log.debug("Starting '{}' elaboration", fileName);
		TerminalinoResult result = new TerminalinoResult(numeroTerminalino, codicePuntoVendita);
		try (InputStream inputStream = terminalino.getDataFile().getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
			while (reader.ready()) {
				String line = reader.readLine();
				if (line.trim().length() == 0) {
					continue;
				}
				result.incrementTotalRowsCount();
				if (line.length() != ROW_LENGTH) {
					result.addErrorAndIncrementRowsInErrorCount(String.format(THE_ROW_DOES_NOT_HAVE_THE_RIGHT_LENGTH, result.getTotalRowsCount(), ROW_LENGTH, line.length()));
					continue;
				}

				if (!validateRowStatus(line.substring(0, 2), result))
					continue;

				Integer qta = 0;
				try {
					qta = Integer.parseInt(line.substring(15, 22));
				} catch (NumberFormatException ex) {
					result.addErrorAndIncrementRowsInErrorCount(String.format(THE_ROW_HAS_NOT_A_CORRECT_QUANTITY, result.getTotalRowsCount(), line.substring(15, 22)));
					continue;
				}

				result.addTrasmissioni(Trasmissioni.builder()
						.idTerminale(numeroTerminalino)
						.codicePuntoVendita(codicePuntoVendita)
						.barCode(line.substring(2, 15))
						.qta(qta)
						.data(now)
						.build());
			}
		} catch (IOException ex) {
			String error = ERRORS_OCCURRED_DURING_THE_TERMINALINO_FILE_ELABORATION;
			log.error(error, ex);
			result.addError(error, ex);
		}
		log.debug("'{}' elaboration completed: {}", fileName, result);
		return result;
	}

	/**
	 * Validates the provided {@code rowStatus} and returns {@code true} in case of correct row status value; {@code false} otherwise.
	 * 
	 * @param rowStatus         {@link String} about the row status to validate.
	 * @param terminalinoResult {@link TerminalinoResult} to update in case of incorrect validation result.
	 * @return {@code true} in case of correct row status value; {@code false} otherwise.
	 */
	private boolean validateRowStatus(String rowStatus, TerminalinoResult terminalinoResult) {
		try {
			if (!TerminalinoRowStatus.OK.equals(TerminalinoRowStatus.fromValue(rowStatus))) {
				terminalinoResult.addErrorAndIncrementRowsInErrorCount(String.format(THE_ROW_IS_NOT_OK, terminalinoResult.getTotalRowsCount(), rowStatus));
				return false;
			}
		} catch (IllegalArgumentException ex) {
			terminalinoResult.addErrorAndIncrementRowsInErrorCount(String.format(THE_ROW_HAS_A_UNEXPECTED_ROW_STATUS_VALUE, terminalinoResult.getTotalRowsCount(), rowStatus));
			return false;
		}
		return true;
	}

	@AllArgsConstructor
	public enum TerminalinoRowStatus {

		OK("OK"), KO("KO");
		private String value;

		public static TerminalinoRowStatus fromValue(String value) {
			return Arrays.stream(TerminalinoRowStatus.values())
					.filter(s -> s.value.equalsIgnoreCase(value))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(String.format("Unexpected value '%s'", value)));
		}
	}
}
