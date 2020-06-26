package com.xantrix.webapp.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movimenti implements Serializable {

	private static final long serialVersionUID = 3525891929055923120L;
	private int rowNumber;
	private String codiceArticolo;
	private String descrizioneArticolo;
	private Double prezzoArticolo;
	private float acquistato;
	private Float reso;
	private Float venduto;
	private Float uscite;
	private Float scaduti;
	private Float disponibili;
	private Float incidenzaScaduti;
}
