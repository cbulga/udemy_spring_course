package com.xantrix.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xantrix.webapp.domain.Movimenti;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovimentiMapper implements RowMapper<Movimenti> {

	private int startingRowNumber;

	public MovimentiMapper(int startingRowNumber) {
		this.startingRowNumber = startingRowNumber;
	}

	@Override
	public Movimenti mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (rs.getRow() > 0) {
			return Movimenti.builder().rowNumber(startingRowNumber + rowNum + 1).acquistato(rs.getFloat("ACQUISTATO"))
					.codiceArticolo(rs.getString("CODART")).descrizioneArticolo(rs.getString("DESART"))
					.disponibili(rs.getFloat("DISPONIBILI")).incidenzaScaduti(rs.getFloat("INCIDENZA_SCADUTI"))
					.prezzoArticolo(rs.getDouble("PRZACQ")).reso(rs.getFloat("RESO")).scaduti(rs.getFloat("SCADUTI"))
					.uscite(rs.getFloat("USCITE")).venduto(rs.getFloat("VENDUTO")).build();
		} else {
			return null;
		}
	}
}
