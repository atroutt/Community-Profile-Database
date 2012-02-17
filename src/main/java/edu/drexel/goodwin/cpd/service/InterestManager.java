package edu.drexel.goodwin.cpd.service;

import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.Interest;

public interface InterestManager {
	
	void create(Interest interest, BindingResult result);

	void update(Interest interest, BindingResult result);

	void delete(Long id);
}
