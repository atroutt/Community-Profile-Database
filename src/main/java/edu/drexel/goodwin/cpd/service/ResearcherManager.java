package edu.drexel.goodwin.cpd.service;

import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.ProfilePicture;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.dto.ForgotPasswordRequest;
import edu.drexel.goodwin.cpd.dto.ResearcherDto;

public interface ResearcherManager {
	
	public Researcher getResearcherForEditing(Long id);
	
	public ResearcherDto getResearcherDtoForEditing(Long id);

	public void deleteResearcher(Long id);

	public void update(ResearcherDto researcher, BindingResult result);

	public void create(ResearcherDto researcher, BindingResult result);

	public void updatePassword(ResearcherDto researcher, BindingResult result);

	public void resetPassword(ForgotPasswordRequest resetRequest, BindingResult result);

	public ResearcherDto getCurrentlyLoggedInResearcherDto();

	public ResearcherDto getResearcherDto(Long id);

	public ProfilePicture getProfilePictureForThisResearcher(Long id);

}
