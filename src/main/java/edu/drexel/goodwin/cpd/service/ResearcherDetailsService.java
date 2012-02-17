package edu.drexel.goodwin.cpd.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.drexel.goodwin.cpd.domain.Researcher;

@Service
public class ResearcherDetailsService implements UserDetailsService {

	/**
	 * FYI we are using email addresses for usernames
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		return (UserDetails) Researcher.findResearchersByEmailEquals(username).getSingleResult();
	}
	
}
