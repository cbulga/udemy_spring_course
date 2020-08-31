package com.xantrix.webapp.service;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.utils.TerminalinoResult;

/**
 * Service about the {@link Terminalino} management.
 * 
 * @author cristian
 */
public interface TerminalinoService {

	/**
	 * Process the provided {@link Terminalino}, converting its data as {@link Trasmissioni} rows and inserting them into the application database.<br/>
	 * Returns a {@link TerminalinoResult} about the conversion result.
	 * 
	 * @param terminalino {@link Terminalino} to be processed.
	 * @return {@link TerminalinoResult} about the conversion result.
	 */
	public TerminalinoResult processTerminalino(Terminalino terminalino);
}
