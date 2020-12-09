package com.xantrix.webapp.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.xantrix.webapp.entities.Utenti;

@Repository
public class UtentiDaoImpl extends AbstractDao<Utenti, Integer> implements UtentiDao {

	@Override
	public Utenti SelByIdFidelity(String idFidelity) {
		Utenti retVal = new Utenti();

		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Utenti> queryDefinition = queryBuilder.createQuery(Utenti.class);

			Root<Utenti> recordset = queryDefinition.from(Utenti.class);

			queryDefinition.select(recordset).where(queryBuilder.equal(recordset.get("codFidelity"), idFidelity));

			retVal = entityManager.createQuery(queryDefinition).getSingleResult();

			return retVal;
		} catch (Exception ex) {
			return retVal;
		}
	}

	@Override
	public void Salva(Utenti utente) {
		super.Inserisci(utente);
	}

	@Override
	public void Aggiorna(Utenti utente) {
		super.Aggiorna(utente);
	}

	@Override
	public void Elimina(Utenti utente) {
		super.Elimina(utente);
	}

	@Override
	public boolean existsByUserIdAndCodFidelityNot(String userId, String codFidelity) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Utenti> query = criteriaBuilder.createQuery(Utenti.class);
		Root<Utenti> root = query.from(Utenti.class);
		Predicate whereClause = criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), userId),
				criteriaBuilder.notEqual(root.get("codFidelity"), codFidelity));
		query.select(root.get("codFidelity")).where(whereClause);

		List<Utenti> resultList = entityManager.createQuery(query).getResultList();
		return resultList != null && !resultList.isEmpty();
	}
}
