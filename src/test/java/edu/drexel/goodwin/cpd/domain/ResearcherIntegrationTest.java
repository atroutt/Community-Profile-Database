package edu.drexel.goodwin.cpd.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.domain.Skill;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext-security.xml" })
@TransactionConfiguration(defaultRollback=true)
@RooIntegrationTest(entity = Researcher.class)
public class ResearcherIntegrationTest {

	@Autowired
	private ResearcherDataOnDemand dod;
	
    @Test
    public void testMarkerMethod() {
    }
    
    @Test
    @Transactional
    public void testCountFindBySkillsAndInterestsDoesNotReturnDeletedResearchers() {
    	Researcher randomResearcher = dod.getRandomResearcher();
    	assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
    	long count = Researcher.countResearchers();
    	assertTrue("Too expensive to perform a find all test for 'Researcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
    	Set<Skill> skills = randomResearcher.getSkills();
    	Set<Interest> interests = randomResearcher.getInterests();
    	List<Researcher> result = Researcher.findResearchersBySkillsAndInterests(skills, interests, 0, 10);
    	assertNotNull("Find by skills and interests method for 'Researcher' illegally returned null", result);
    	assertTrue("Find by skills and interests method for 'Researcher' failed to return any data", result.size() > 0);
    	int beforeCount = result.size();
    	
    	// Delete someone and save
    	Researcher firstResearcher = result.get(0);
    	firstResearcher.setDeleted(true);
    	firstResearcher.merge();
    	
    	// make sure the deleted researcher did not turn up in the search results
    	result = Researcher.findResearchersBySkillsAndInterests(skills, interests, 0, 10);
    	assertEquals(beforeCount - 1, result.size());
    	
    	long countReturned = Researcher.countResearchersBySkillsAndInterests(skills, interests);
    	assertEquals(result.size(), countReturned);
    }
    
    @Test
    @Transactional
	public void testFindBySkillsAndInterestsDoesNotReturnDeletedResearchers() {
		Researcher randomResearcher = dod.getRandomResearcher();
		assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
		long count = Researcher.countResearchers();
		assertTrue("Too expensive to perform a find all test for 'Researcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
		Set<Skill> skills = randomResearcher.getSkills();
		Set<Interest> interests = randomResearcher.getInterests();
		List<Researcher> result = Researcher.findResearchersBySkillsAndInterests(skills, interests, 0, 10);
		assertNotNull("Find by skills and interests method for 'Researcher' illegally returned null", result);
		assertTrue("Find by skills and interests method for 'Researcher' failed to return any data", result.size() > 0);
		int beforeCount = result.size();
		
		// Delete someone and save
		Researcher firstResearcher = result.get(0);
		firstResearcher.setDeleted(true);
		firstResearcher.merge();
		
		// make sure the deleted researcher did not turn up in the search results
		result = Researcher.findResearchersBySkillsAndInterests(skills, interests, 0, 10);
		assertEquals(beforeCount - 1, result.size());
	}
    

	@Test
	@Transactional
	public void testFindBySkillsAndInterests() {
		Researcher randomResearcher = dod.getRandomResearcher();
		assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", randomResearcher);
		long count = Researcher.countResearchers();
		assertTrue("Too expensive to perform a find all test for 'Researcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
		Set<Skill> skills = randomResearcher.getSkills();
		Set<Interest> interests = randomResearcher.getInterests();
		List<Researcher> result = Researcher.findResearchersBySkillsAndInterests(skills, interests, 0, 10);
		assertNotNull("Find by skills and interests  method for 'Researcher' illegally returned null", result);
		assertTrue("Find by skills and interests method for 'Researcher' failed to return any data although there should have been at least one", result.size() > 0);
	}

}
