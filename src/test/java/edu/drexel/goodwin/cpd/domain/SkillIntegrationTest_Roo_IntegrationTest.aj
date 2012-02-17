package edu.drexel.goodwin.cpd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SkillIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SkillIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);    
    
    @Autowired    
    private SkillDataOnDemand SkillIntegrationTest.dod;    
    
    @Test    
    public void SkillIntegrationTest.testCountSkills() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        long count = edu.drexel.goodwin.cpd.domain.Skill.countSkills();        
        org.junit.Assert.assertTrue("Counter for 'Skill' incorrectly reported there were no entries", count > 0);        
    }    
    
    @Test    
    public void SkillIntegrationTest.testFindSkill() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        java.lang.Long id = dod.getRandomSkill().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Skill obj = edu.drexel.goodwin.cpd.domain.Skill.findSkill(id);        
        org.junit.Assert.assertNotNull("Find method for 'Skill' illegally returned null for id '" + id + "'", obj);        
        org.junit.Assert.assertEquals("Find method for 'Skill' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @Test    
    public void SkillIntegrationTest.testFindAllSkills() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        long count = edu.drexel.goodwin.cpd.domain.Skill.countSkills();        
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Skill', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<edu.drexel.goodwin.cpd.domain.Skill> result = edu.drexel.goodwin.cpd.domain.Skill.findAllSkills();        
        org.junit.Assert.assertNotNull("Find all method for 'Skill' illegally returned null", result);        
        org.junit.Assert.assertTrue("Find all method for 'Skill' failed to return any data", result.size() > 0);        
    }    
    
    @Test    
    public void SkillIntegrationTest.testFindSkillEntries() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        long count = edu.drexel.goodwin.cpd.domain.Skill.countSkills();        
        if (count > 20) count = 20;        
        java.util.List<edu.drexel.goodwin.cpd.domain.Skill> result = edu.drexel.goodwin.cpd.domain.Skill.findSkillEntries(0, (int)count);        
        org.junit.Assert.assertNotNull("Find entries method for 'Skill' illegally returned null", result);        
        org.junit.Assert.assertEquals("Find entries method for 'Skill' returned an incorrect number of entries", count, result.size());        
    }    
    
    @Test    
    @Transactional    
    public void SkillIntegrationTest.testFlush() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        java.lang.Long id = dod.getRandomSkill().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Skill obj = edu.drexel.goodwin.cpd.domain.Skill.findSkill(id);        
        org.junit.Assert.assertNotNull("Find method for 'Skill' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifySkill(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Skill' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void SkillIntegrationTest.testMerge() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        java.lang.Long id = dod.getRandomSkill().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Skill obj = edu.drexel.goodwin.cpd.domain.Skill.findSkill(id);        
        org.junit.Assert.assertNotNull("Find method for 'Skill' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifySkill(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Skill' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void SkillIntegrationTest.testPersist() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        edu.drexel.goodwin.cpd.domain.Skill obj = dod.getNewTransientSkill(Integer.MAX_VALUE);        
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to provide a new transient entity", obj);        
        org.junit.Assert.assertNull("Expected 'Skill' identifier to be null", obj.getId());        
        obj.persist();        
        obj.flush();        
        org.junit.Assert.assertNotNull("Expected 'Skill' identifier to no longer be null", obj.getId());        
    }    
    
    @Test    
    @Transactional    
    public void SkillIntegrationTest.testRemove() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to initialize correctly", dod.getRandomSkill());        
        java.lang.Long id = dod.getRandomSkill().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Skill' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Skill obj = edu.drexel.goodwin.cpd.domain.Skill.findSkill(id);        
        org.junit.Assert.assertNotNull("Find method for 'Skill' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        org.junit.Assert.assertNull("Failed to remove 'Skill' with identifier '" + id + "'", edu.drexel.goodwin.cpd.domain.Skill.findSkill(id));        
    }    
    
}
