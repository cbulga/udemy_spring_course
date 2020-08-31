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
}
