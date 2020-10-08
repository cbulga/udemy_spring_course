package com.xantrix.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xantrix.webapp.domain.Trasmissioni;

public class TrasmissioniMapper implements RowMapper<Trasmissioni> {
	public Trasmissioni mapRow(ResultSet row, int rowNum) throws SQLException {
		return Trasmissioni.builder()
				.barCode(row.getString("BARCODE"))
				.data(row.getDate("DATA"))
				.descrizione(row.getString("DESCRIZIONE"))
				.idTerminale(row.getString("IDTERMINALE"))
				.qta(row.getInt("QTA"))
				.build();
	}
}
