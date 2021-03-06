package com.xantrix.webapp.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Ingredienti implements Serializable {

	private static final long serialVersionUID = 6152228720599813406L;
	@Include
	private String codArt;
	private String info;
	private Articoli articoli;
}
