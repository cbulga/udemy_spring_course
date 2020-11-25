package com.xantrix.webapp.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.domain.Trasmissioni;

@Repository
public class TrasmissioniRepositoryImpl implements TrasmissioniRepository {

	private static final String CALL_SP_SEL_DATI_TRASMISSIONI = "CALL Sp_SelDatiTrasm ('%s')";
	protected static final String CALL_SP_INS_TRASMISSIONI = "CALL Sp_InsTrasmissioni(?, ?, ?, ?, ?)";
	protected static final String CALL_SP_DEL_TRASMISSIONI = "CALL Sp_DelTrasmissioni(?)";
	protected static final String TRASMISSIONI_CANNOT_BE_NULL = "trasmissioni cannot be null";
	protected static final String ID_TERMINALE_CANNOT_BE_NULL = "idTerminale cannot be null";
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Override
	public void insTrasmissioni(List<Trasmissioni> trasmissioni) {
		Objects.requireNonNull(trasmissioni, TRASMISSIONI_CANNOT_BE_NULL);
		trasmissioni.stream().forEach(t -> 
			jdbcTemplate.update(CALL_SP_INS_TRASMISSIONI,
					t.getIdTerminale(),
					t.getCodicePuntoVendita(),
					t.getData(),
					t.getBarCode(),
					t.getQta()));
	}

	@Override
	public List<Trasmissioni> findTrasmissioniByIdTerminale(String idTerminale) {
		Objects.requireNonNull(idTerminale, ID_TERMINALE_CANNOT_BE_NULL);
		String sql = String.format(CALL_SP_SEL_DATI_TRASMISSIONI, idTerminale);
		return jdbcTemplate.query(sql, new TrasmissioniMapper());
	}

	@Override
	public boolean deleteByIdTerminale(String idTerminale) {
		Objects.requireNonNull(idTerminale, ID_TERMINALE_CANNOT_BE_NULL);
		return jdbcTemplate.update(CALL_SP_DEL_TRASMISSIONI, idTerminale) > 0;
	}
}
