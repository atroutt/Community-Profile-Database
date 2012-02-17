package edu.drexel.goodwin.cpd.service.impl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.service.InterestManager;

@Service
@Transactional
public class InterestManagerImpl implements InterestManager {
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void create(Interest interest, BindingResult result) {
		for (ConstraintViolation<Interest> constraint : Validation.buildDefaultValidatorFactory().getValidator().validate(interest)) {
			result.rejectValue(constraint.getPropertyPath().toString(), "interest.error." + constraint.getPropertyPath(), constraint.getMessage());
		}
		if (!result.hasErrors()) {
			interest.persist();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void update(Interest interest, BindingResult result) {
		for (ConstraintViolation<Interest> constraint : Validation.buildDefaultValidatorFactory().getValidator().validate(interest)) {
			result.rejectValue(constraint.getPropertyPath().toString(), "interest.error." + constraint.getPropertyPath(), constraint.getMessage());
		}
		if (!result.hasErrors()) {
			interest.merge();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void delete(Long id) {
		// TODO make this just flag as deleted
		Interest.findInterest(id).remove();   
	}
}
