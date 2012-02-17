package edu.drexel.goodwin.cpd.service.impl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.Skill;
import edu.drexel.goodwin.cpd.service.SkillManager;

@Service
@Transactional
public class SkillManagerImpl implements SkillManager {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void create(Skill skill, BindingResult result) {
		for (ConstraintViolation<Skill> constraint : Validation.buildDefaultValidatorFactory().getValidator().validate(skill)) {
			result.rejectValue(constraint.getPropertyPath().toString(), "skill.error." + constraint.getPropertyPath(), constraint.getMessage());
		}
		if (!result.hasErrors()) {
			skill.persist();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void update(Skill skill, BindingResult result) {
		for (ConstraintViolation<Skill> constraint : Validation.buildDefaultValidatorFactory().getValidator().validate(skill)) {
			result.rejectValue(constraint.getPropertyPath().toString(), "skill.error." + constraint.getPropertyPath(), constraint.getMessage());
		}
		if (!result.hasErrors()) {
			skill.merge();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void delete(Long id) {
		// TODO make this just flag as deleted
		Skill.findSkill(id).remove();   
	}

}
