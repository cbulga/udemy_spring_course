package com.xantrix.webapp.dao;

import com.xantrix.webapp.entities.Utenti;

public interface UtentiDao {
	Utenti SelByIdFidelity(String id);

	void Salva(Utenti utente);

	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);

	/**
	 * Returns {@code true} in case exists a {@link Utenti} having the provided {@link Utenti#getUserId()} and the {@link Utenti#getCodFidelity()} different from the provided one, if any; or {@code false} otherwise.
	 * 
	 * @param userId      {@link String} about the {@link Utenti#getUserId()} filter.
	 * @param codFidelity {@link String} about the {@link Utenti#getCodFidelity()} filter.
	 * @return {@code true} in case exists a {@link Utenti} having the provided {@link Utenti#getUserId()} and the {@link Utenti#getCodFidelity()} different from the provided one, if any; or {@code false} otherwise.
	 */
	boolean existsByUserIdAndCodFidelityNot(String userId, String codFidelity);
}
