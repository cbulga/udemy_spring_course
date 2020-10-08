package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.domain.Trasmissioni;

/**
 * Service providing useful operations on {@link Trasmissioni}.
 * 
 * @author cristian
 */
public interface TrasmissioniService {

	/**
	 * Returns a {@link List}&lt;Trasmissioni&gt; applying the provided filter.
	 * 
	 * @param idTerminale {@link String} about the {@link Trasmissioni#getIdTerminale()} filter.
	 * @return a {@link List}&lt;Trasmissioni&gt; applying the provided filter.
	 */
	public List<Trasmissioni> findTrasmissioniByIdTerminale(String idTerminale);
}
