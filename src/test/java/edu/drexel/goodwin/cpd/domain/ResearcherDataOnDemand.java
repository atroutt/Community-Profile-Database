package edu.drexel.goodwin.cpd.domain;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.dto.ResearcherDto;
import edu.drexel.goodwin.cpd.dto.ResearcherDtoAssembler;

@RooDataOnDemand(entity = Researcher.class)
public class ResearcherDataOnDemand {

	@Autowired
	private InterestDataOnDemand interestDod;
	
	@Autowired
	private SkillDataOnDemand skillDod;
	
	private static final ResearcherDtoAssembler RESEARCHER_DTO_ASSEMBLER = new ResearcherDtoAssembler();
	
	private Random random = new Random();

	public Researcher getNewTransientResearcher(int index) {
		edu.drexel.goodwin.cpd.domain.Researcher obj = new edu.drexel.goodwin.cpd.domain.Researcher();
		obj.setAdmin(false);
		obj.setBibliography("bibliography_" + index);
		obj.setDeleted(false);
		obj.setEmail("audrey+" + index + "@mathforum.org");
		obj.setFirstName("firstName_" + index);
		obj.setJobTitle("jobTitle_" + index);
		obj.setLastName("lastName_" + index);
		obj.setOrganization("organization_" + index);
		obj.setPassword("password_" + index);
		obj.setSalt("salt_" + index);
		obj.setSkills(skillDod.getRandomSkills(random));
		obj.setInterests(interestDod.getRandomInterests(random));
		return obj;
	}

	public ResearcherDto getRandomResearcherDto() {
		return RESEARCHER_DTO_ASSEMBLER.assemble(getRandomResearcher());
	}
}
