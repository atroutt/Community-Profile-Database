package edu.drexel.goodwin.cpd.service;

import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.Skill;

public interface SkillManager {

	void create(Skill skill, BindingResult result);

	void update(Skill skill, BindingResult result);

	void delete(Long id);

}
