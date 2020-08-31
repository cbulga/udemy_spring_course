package com.xantrix.webapp.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.domain.Trasmissioni;

@Repository
public class TrasmissioniRepositoryImpl implements TrasmissioniRepository {

	protected static final String CALL_SP_INS_TRASMISSIONI = "CALL Sp_InsTrasmissioni(?, ?, ?, ?)";
	protected static final String TRASMISSIONI_CANNOT_BE_NULL = "trasmissioni cannot be null";
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Override
	public void insTrasmissioni(List<Trasmissioni> trasmissioni) {
		Objects.requireNonNull(trasmissioni, TRASMISSIONI_CANNOT_BE_NULL);
		trasmissioni.stream().forEach(t -> 
			jdbcTemplate.update(CALL_SP_INS_TRASMISSIONI,
					t.getIdTerminale(),
					t.getData(),
					t.getBarCode(),
					t.getQta()));
	}
}
