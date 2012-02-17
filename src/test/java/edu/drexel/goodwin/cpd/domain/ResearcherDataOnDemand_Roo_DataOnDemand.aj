package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.Researcher;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ResearcherDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ResearcherDataOnDemand: @Component;    
    
    private Random ResearcherDataOnDemand.rnd = new SecureRandom();    
    
    private List<Researcher> ResearcherDataOnDemand.data;    
    
    public Researcher ResearcherDataOnDemand.getRandomResearcher() {    
        init();        
        Researcher obj = data.get(rnd.nextInt(data.size()));        
        return Researcher.findResearcher(obj.getId());        
    }    
    
    public boolean ResearcherDataOnDemand.modifyResearcher(Researcher obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void ResearcherDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = edu.drexel.goodwin.cpd.domain.Researcher.findResearcherEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Researcher' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<edu.drexel.goodwin.cpd.domain.Researcher>();        
        for (int i = 0; i < 10; i++) {        
            edu.drexel.goodwin.cpd.domain.Researcher obj = getNewTransientResearcher(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
