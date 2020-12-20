package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "CLIENTI")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Clienti implements Serializable {
	private static final long serialVersionUID = -6994272792307526864L;

	@Id
	@Column(name = "CODFIDELITY")
	@NotNull(message = "{NotNull.Clienti.codFidelity.validation}")
	@Size(min = 8, max = 20, message = "{Size.Clienti.codFidelity.validation}")
	private String codFidelity;

	@Column(name = "NOME")
	@Size(min = 2, max = 50, message = "{Size.Clienti.nome.validation}")
	private String nome;

	@Column(name = "COGNOME")
	@Size(min = 2, max = 60, message = "{Size.Clienti.cognome.validation}")
	private String cognome;

	@Column(name = "INDIRIZZO")
	@Size(min = 10, max = 80, message = "{Size.Clienti.indirizzo.validation}")
	private String indirizzo;

	@Column(name = "COMUNE")
	@Size(min = 2, max = 50, message = "{Size.Clienti.comune.validation}")
	private String comune;

	@Column(name = "CAP")
	private String cap;

	@Column(name = "PROV")
	private String prov;

	@Column(name = "TELEFONO")
	private String telefono;

	@Column(name = "MAIL")
	private String mail;

	@Column(name = "STATO")
	private String stato;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATACREAZ")
	private Date dataCreaz;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Cards card;

	@OneToOne(mappedBy = "clienti", cascade = CascadeType.ALL, orphanRemoval = true)
	private Utenti utenti;
}
