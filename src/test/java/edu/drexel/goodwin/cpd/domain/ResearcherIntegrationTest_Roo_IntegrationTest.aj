package edu.drexel.goodwin.cpd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ResearcherIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ResearcherIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);    
    
    @Test    
    public void ResearcherIntegrationTest.testCountResearchers() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        long count = edu.drexel.goodwin.cpd.domain.Researcher.countResearchers();        
        org.junit.Assert.assertTrue("Counter for 'Researcher' incorrectly reported there were no entries", count > 0);        
    }    
    
    @Test    
    public void ResearcherIntegrationTest.testFindResearcher() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        java.lang.Long id = dod.getRandomResearcher().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Researcher obj = edu.drexel.goodwin.cpd.domain.Researcher.findResearcher(id);        
        org.junit.Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);        
        org.junit.Assert.assertEquals("Find method for 'Researcher' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @Test    
    public void ResearcherIntegrationTest.testFindAllResearchers() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        long count = edu.drexel.goodwin.cpd.domain.Researcher.countResearchers();        
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Researcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<edu.drexel.goodwin.cpd.domain.Researcher> result = edu.drexel.goodwin.cpd.domain.Researcher.findAllResearchers();        
        org.junit.Assert.assertNotNull("Find all method for 'Researcher' illegally returned null", result);        
        org.junit.Assert.assertTrue("Find all method for 'Researcher' failed to return any data", result.size() > 0);        
    }    
    
    @Test    
    public void ResearcherIntegrationTest.testFindResearcherEntries() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        long count = edu.drexel.goodwin.cpd.domain.Researcher.countResearchers();        
        if (count > 20) count = 20;        
        java.util.List<edu.drexel.goodwin.cpd.domain.Researcher> result = edu.drexel.goodwin.cpd.domain.Researcher.findResearcherEntries(0, (int)count);        
        org.junit.Assert.assertNotNull("Find entries method for 'Researcher' illegally returned null", result);        
        org.junit.Assert.assertEquals("Find entries method for 'Researcher' returned an incorrect number of entries", count, result.size());        
    }    
    
    @Test    
    @Transactional    
    public void ResearcherIntegrationTest.testFlush() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        java.lang.Long id = dod.getRandomResearcher().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Researcher obj = edu.drexel.goodwin.cpd.domain.Researcher.findResearcher(id);        
        org.junit.Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyResearcher(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Researcher' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ResearcherIntegrationTest.testMerge() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        java.lang.Long id = dod.getRandomResearcher().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Researcher obj = edu.drexel.goodwin.cpd.domain.Researcher.findResearcher(id);        
        org.junit.Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyResearcher(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Researcher' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ResearcherIntegrationTest.testPersist() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        edu.drexel.goodwin.cpd.domain.Researcher obj = dod.getNewTransientResearcher(Integer.MAX_VALUE);        
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to provide a new transient entity", obj);        
        org.junit.Assert.assertNull("Expected 'Researcher' identifier to be null", obj.getId());        
        obj.persist();        
        obj.flush();        
        org.junit.Assert.assertNotNull("Expected 'Researcher' identifier to no longer be null", obj.getId());        
    }    
    
    @Test    
    @Transactional    
    public void ResearcherIntegrationTest.testRemove() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());        
        java.lang.Long id = dod.getRandomResearcher().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Researcher obj = edu.drexel.goodwin.cpd.domain.Researcher.findResearcher(id);        
        org.junit.Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        org.junit.Assert.assertNull("Failed to remove 'Researcher' with identifier '" + id + "'", edu.drexel.goodwin.cpd.domain.Researcher.findResearcher(id));        
    }    
    
}
