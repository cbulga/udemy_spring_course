package com.xantrix.webapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xantrix.webapp.dao.UtentiDao;
import com.xantrix.webapp.entities.Utenti;

/**
 * Custom validator to check that the provided {@link Utenti#getUserId()} is unique.
 * 
 * @author cristian
 */
@Component
public class UniqueUserIdValidator implements ConstraintValidator<UniqueUserId, Utenti> {

	private final UtentiDao utentiDao;

	@Autowired
	public UniqueUserIdValidator(UtentiDao utentiDao) {
		this.utentiDao = utentiDao;
	}

	@Override
	public boolean isValid(Utenti utentiToValidate, ConstraintValidatorContext context) {
		if (utentiToValidate != null && StringUtils.isNotBlank(utentiToValidate.getUserId())) {
			return !utentiDao.existsByUserIdAndCodFidelityNot(utentiToValidate.getUserId(), utentiToValidate.getCodFidelity());
		}

		return true;
	}
}
