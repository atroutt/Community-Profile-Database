package edu.drexel.goodwin.cpd.dto;

import edu.drexel.goodwin.cpd.domain.Researcher;

public class ResearcherDtoAssembler {

	public ResearcherDto assemble(Researcher researcher) {
		ResearcherDto dto = new ResearcherDto();
		dto.setId(researcher.getId());
		dto.setVersion(researcher.getVersion());
		dto.setFirstName(researcher.getFirstName());
		dto.setLastName(researcher.getLastName());
		dto.setJobTitle(researcher.getJobTitle());
		dto.setOrganization(researcher.getOrganization());
		dto.setWebsite(researcher.getWebsite());
		dto.setEmail(researcher.getEmail());
		dto.setSkills(researcher.getSkills());
		dto.setInterests(researcher.getInterests());
		dto.setBibliography(researcher.getBibliography());
		boolean hasProfilePicture = researcher.getProfilePicture() != null;
		dto.setHasProfilePicture(hasProfilePicture);
		if (hasProfilePicture) {
			dto.setProfilePictureWidth(researcher.getProfilePicture().getWidth());
			dto.setProfilePictureHeight(researcher.getProfilePicture().getHeight());
		}
		return dto;
	}

}
