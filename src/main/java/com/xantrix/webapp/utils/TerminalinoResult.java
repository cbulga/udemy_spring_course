package com.xantrix.webapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.xantrix.webapp.domain.Trasmissioni;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TerminalinoResult {

	protected static final String TRASMISSIONI_CANNOT_BE_NULL = "trasmissioni cannot be null";
	@NonNull
	private String numeroTerminalino;
	@NonNull
	private String codicePuntoVendita;
	@Default
	private int totalRowsCount = 0;
	@Default
	private int totalRowsInErrorCount = 0;
	@Default
	private List<String> errors = new ArrayList<>();
	@Default
	private List<Trasmissioni> trasmissioni = new ArrayList<>();
	private Exception occurredException;

	public void addError(String error) {
		this.getErrors().add(error);
	}

	public void addError(String error, Exception ex) {
		this.getErrors().add(error);
		occurredException = ex;
	}

	public void addErrorAndIncrementRowsInErrorCount(String error) {
		addError(error);
		incrementTotalRowsInErrorCount();
	}
	
	public void incrementTotalRowsCount() {
		totalRowsCount++;
	}

	public void incrementTotalRowsInErrorCount() {
		totalRowsInErrorCount++;
	}

	public boolean addTrasmissioni(Trasmissioni trasmissioni) {
		Objects.requireNonNull(trasmissioni, TRASMISSIONI_CANNOT_BE_NULL);
		return this.trasmissioni.add(trasmissioni);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
