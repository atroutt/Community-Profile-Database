package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.ContactUsSubjectDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ContactUsSubjectIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ContactUsSubjectIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);    
    
    @Autowired    
    private ContactUsSubjectDataOnDemand ContactUsSubjectIntegrationTest.dod;    
    
    @Test    
    public void ContactUsSubjectIntegrationTest.testCountContactUsSubjects() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        long count = edu.drexel.goodwin.cpd.domain.ContactUsSubject.countContactUsSubjects();        
        org.junit.Assert.assertTrue("Counter for 'ContactUsSubject' incorrectly reported there were no entries", count > 0);        
    }    
    
    @Test    
    public void ContactUsSubjectIntegrationTest.testFindContactUsSubject() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        java.lang.Long id = dod.getRandomContactUsSubject().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubject(id);        
        org.junit.Assert.assertNotNull("Find method for 'ContactUsSubject' illegally returned null for id '" + id + "'", obj);        
        org.junit.Assert.assertEquals("Find method for 'ContactUsSubject' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @Test    
    public void ContactUsSubjectIntegrationTest.testFindAllContactUsSubjects() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        long count = edu.drexel.goodwin.cpd.domain.ContactUsSubject.countContactUsSubjects();        
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'ContactUsSubject', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<edu.drexel.goodwin.cpd.domain.ContactUsSubject> result = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findAllContactUsSubjects();        
        org.junit.Assert.assertNotNull("Find all method for 'ContactUsSubject' illegally returned null", result);        
        org.junit.Assert.assertTrue("Find all method for 'ContactUsSubject' failed to return any data", result.size() > 0);        
    }    
    
    @Test    
    public void ContactUsSubjectIntegrationTest.testFindContactUsSubjectEntries() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        long count = edu.drexel.goodwin.cpd.domain.ContactUsSubject.countContactUsSubjects();        
        if (count > 20) count = 20;        
        java.util.List<edu.drexel.goodwin.cpd.domain.ContactUsSubject> result = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubjectEntries(0, (int)count);        
        org.junit.Assert.assertNotNull("Find entries method for 'ContactUsSubject' illegally returned null", result);        
        org.junit.Assert.assertEquals("Find entries method for 'ContactUsSubject' returned an incorrect number of entries", count, result.size());        
    }    
    
    @Test    
    @Transactional    
    public void ContactUsSubjectIntegrationTest.testFlush() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        java.lang.Long id = dod.getRandomContactUsSubject().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubject(id);        
        org.junit.Assert.assertNotNull("Find method for 'ContactUsSubject' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyContactUsSubject(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'ContactUsSubject' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ContactUsSubjectIntegrationTest.testMerge() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        java.lang.Long id = dod.getRandomContactUsSubject().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubject(id);        
        org.junit.Assert.assertNotNull("Find method for 'ContactUsSubject' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyContactUsSubject(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'ContactUsSubject' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ContactUsSubjectIntegrationTest.testPersist() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = dod.getNewTransientContactUsSubject(Integer.MAX_VALUE);        
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to provide a new transient entity", obj);        
        org.junit.Assert.assertNull("Expected 'ContactUsSubject' identifier to be null", obj.getId());        
        obj.persist();        
        obj.flush();        
        org.junit.Assert.assertNotNull("Expected 'ContactUsSubject' identifier to no longer be null", obj.getId());        
    }    
    
    @Test    
    @Transactional    
    public void ContactUsSubjectIntegrationTest.testRemove() {    
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to initialize correctly", dod.getRandomContactUsSubject());        
        java.lang.Long id = dod.getRandomContactUsSubject().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'ContactUsSubject' failed to provide an identifier", id);        
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubject(id);        
        org.junit.Assert.assertNotNull("Find method for 'ContactUsSubject' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        org.junit.Assert.assertNull("Failed to remove 'ContactUsSubject' with identifier '" + id + "'", edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubject(id));        
    }    
    
}
