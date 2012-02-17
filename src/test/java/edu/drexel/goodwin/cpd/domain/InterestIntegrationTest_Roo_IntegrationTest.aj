package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.InterestDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect InterestIntegrationTest_Roo_IntegrationTest {
    
    declare @type: InterestIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);    
    
    @Autowired    
    private InterestDataOnDemand InterestIntegrationTest.dod;    
    
    @Test    
    public void InterestIntegrationTest.testCountInterests() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        long count = edu.drexel.goodwin.cpd.domain.Interest.countInterests();        
        org.junit.Assert.assertTrue("Counter for 'Interest' incorrectly reported there were no entries", count > 0);        
    }    
    
    @Test    
    public void InterestIntegrationTest.testFindInterest() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        java.lang.Long id = dod.getRandomInterest().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Interest obj = edu.drexel.goodwin.cpd.domain.Interest.findInterest(id);        
        org.junit.Assert.assertNotNull("Find method for 'Interest' illegally returned null for id '" + id + "'", obj);        
        org.junit.Assert.assertEquals("Find method for 'Interest' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @Test    
    public void InterestIntegrationTest.testFindAllInterests() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        long count = edu.drexel.goodwin.cpd.domain.Interest.countInterests();        
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Interest', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<edu.drexel.goodwin.cpd.domain.Interest> result = edu.drexel.goodwin.cpd.domain.Interest.findAllInterests();        
        org.junit.Assert.assertNotNull("Find all method for 'Interest' illegally returned null", result);        
        org.junit.Assert.assertTrue("Find all method for 'Interest' failed to return any data", result.size() > 0);        
    }    
    
    @Test    
    public void InterestIntegrationTest.testFindInterestEntries() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        long count = edu.drexel.goodwin.cpd.domain.Interest.countInterests();        
        if (count > 20) count = 20;        
        java.util.List<edu.drexel.goodwin.cpd.domain.Interest> result = edu.drexel.goodwin.cpd.domain.Interest.findInterestEntries(0, (int)count);        
        org.junit.Assert.assertNotNull("Find entries method for 'Interest' illegally returned null", result);        
        org.junit.Assert.assertEquals("Find entries method for 'Interest' returned an incorrect number of entries", count, result.size());        
    }    
    
    @Test    
    @Transactional    
    public void InterestIntegrationTest.testFlush() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        java.lang.Long id = dod.getRandomInterest().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Interest obj = edu.drexel.goodwin.cpd.domain.Interest.findInterest(id);        
        org.junit.Assert.assertNotNull("Find method for 'Interest' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyInterest(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Interest' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void InterestIntegrationTest.testMerge() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        java.lang.Long id = dod.getRandomInterest().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Interest obj = edu.drexel.goodwin.cpd.domain.Interest.findInterest(id);        
        org.junit.Assert.assertNotNull("Find method for 'Interest' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyInterest(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Interest' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void InterestIntegrationTest.testPersist() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        edu.drexel.goodwin.cpd.domain.Interest obj = dod.getNewTransientInterest(Integer.MAX_VALUE);        
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to provide a new transient entity", obj);        
        org.junit.Assert.assertNull("Expected 'Interest' identifier to be null", obj.getId());        
        obj.persist();        
        obj.flush();        
        org.junit.Assert.assertNotNull("Expected 'Interest' identifier to no longer be null", obj.getId());        
    }    
    
    @Test    
    @Transactional    
    public void InterestIntegrationTest.testRemove() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to initialize correctly", dod.getRandomInterest());        
        java.lang.Long id = dod.getRandomInterest().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Interest' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.Interest obj = edu.drexel.goodwin.cpd.domain.Interest.findInterest(id);        
        org.junit.Assert.assertNotNull("Find method for 'Interest' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        org.junit.Assert.assertNull("Failed to remove 'Interest' with identifier '" + id + "'", edu.drexel.goodwin.cpd.domain.Interest.findInterest(id));        
    }    
    
}
