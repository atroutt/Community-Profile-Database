package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.Interest;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect InterestDataOnDemand_Roo_DataOnDemand {
    
    declare @type: InterestDataOnDemand: @Component;    
    
    private Random InterestDataOnDemand.rnd = new SecureRandom();    
    
    private List<Interest> InterestDataOnDemand.data;    
    
    public Interest InterestDataOnDemand.getNewTransientInterest(int index) {    
        edu.drexel.goodwin.cpd.domain.Interest obj = new edu.drexel.goodwin.cpd.domain.Interest();        
        obj.setSubject("subject_" + index);        
        return obj;        
    }    
    
    public Interest InterestDataOnDemand.getRandomInterest() {    
        init();        
        Interest obj = data.get(rnd.nextInt(data.size()));        
        return Interest.findInterest(obj.getId());        
    }    
    
    public boolean InterestDataOnDemand.modifyInterest(Interest obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void InterestDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = edu.drexel.goodwin.cpd.domain.Interest.findInterestEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Interest' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<edu.drexel.goodwin.cpd.domain.Interest>();        
        for (int i = 0; i < 10; i++) {        
            edu.drexel.goodwin.cpd.domain.Interest obj = getNewTransientInterest(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
