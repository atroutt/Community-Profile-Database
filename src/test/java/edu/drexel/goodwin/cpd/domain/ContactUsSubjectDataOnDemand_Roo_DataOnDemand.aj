package edu.drexel.goodwin.cpd.domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.drexel.goodwin.cpd.domain.ContactUsSubject;

privileged aspect ContactUsSubjectDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ContactUsSubjectDataOnDemand: @Component;    
    
    private final Random ContactUsSubjectDataOnDemand.rnd = new SecureRandom();    
    
    private List<ContactUsSubject> ContactUsSubjectDataOnDemand.data;    
    
    public ContactUsSubject ContactUsSubjectDataOnDemand.getNewTransientContactUsSubject(int index) {    
        edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = new edu.drexel.goodwin.cpd.domain.ContactUsSubject();        
        obj.setName("name_" + index);        
        return obj;        
    }    
    
    public ContactUsSubject ContactUsSubjectDataOnDemand.getRandomContactUsSubject() {    
        init();        
        ContactUsSubject obj = data.get(rnd.nextInt(data.size()));        
        return ContactUsSubject.findContactUsSubject(obj.getId());        
    }    
    
    public boolean ContactUsSubjectDataOnDemand.modifyContactUsSubject(ContactUsSubject obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void ContactUsSubjectDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = edu.drexel.goodwin.cpd.domain.ContactUsSubject.findContactUsSubjectEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'ContactUsSubject' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<edu.drexel.goodwin.cpd.domain.ContactUsSubject>();        
        for (int i = 0; i < 10; i++) {        
            edu.drexel.goodwin.cpd.domain.ContactUsSubject obj = getNewTransientContactUsSubject(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
