package com.xantrix.webapp.repository;

import java.util.List;

import com.xantrix.webapp.domain.Trasmissioni;

public interface TrasmissioniRepository {

	/**
	 * Adds the provided {@link List}&lt;Tramissioni&gt; into the application database.
	 * 
	 * @param trasmissioni {@link List}&lt;Tramissioni&gt; to insert into the application database.
	 */
	public void insTrasmissioni(List<Trasmissioni> trasmissioni);

	/**
	 * Returns a {@link List}&lt;Trasmissioni&gt; applying the provided filter.
	 * 
	 * @param idTerminale {@link String} about the {@link Trasmissioni#getIdTerminale()} filter.
	 * @return a {@link List}&lt;Trasmissioni&gt; applying the provided filter.
	 */
	public List<Trasmissioni> findTrasmissioniByIdTerminale(String idTerminale);

	/**
	 * Deletes the {@link Trasmissioni} identified by the provided {@link Trasmissioni#getIdTerminale()}.<br/>
	 * Returns {@code true} in case of success; {@code false} otherwise.
	 * 
	 * @param idTerminale {@link String} about the {@link Trasmissioni#getIdTerminale()} filter.
	 * @return {@code true} in case of success; {@code false} otherwise.
	 */
	public boolean deleteByIdTerminale(String idTerminale);
}
