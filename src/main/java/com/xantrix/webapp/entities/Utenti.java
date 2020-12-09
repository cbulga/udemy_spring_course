package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.xantrix.webapp.validator.UniqueUserId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "UTENTI")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@UniqueUserId
public class Utenti implements Serializable {
	private static final long serialVersionUID = 8473057964112587082L;

	@Id
	@Column(name = "CODFIDELITY")
	@Include
	private String codFidelity;

	@Column(name = "USERID")
	@NotNull(message = "{NotNull.Utenti.userId.validation}")
	private String userId;

	@Column(name = "PASSWORD")
	private String pwd;

	@Column(name = "ABILITATO")
	private String abilitato;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Clienti clienti;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "utente", orphanRemoval = true)
	@Builder.Default
	private Set<Profili> profili = new HashSet<>();

	public Utenti(String codFidelity) {
		this.codFidelity = codFidelity;
	}
}
