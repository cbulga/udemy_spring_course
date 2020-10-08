package com.xantrix.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trasmissioni implements Serializable {

	private static final long serialVersionUID = 943525769761201456L;
	@Include
	private Integer id;
	private String idTerminale;
	private Date data;
	private String barCode;
	private Integer qta;
	private String descrizione;
}
