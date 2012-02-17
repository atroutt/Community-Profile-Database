package edu.drexel.goodwin.cpd.domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.drexel.goodwin.cpd.domain.Skill;

privileged aspect SkillDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SkillDataOnDemand: @Component;    
    
    private final Random SkillDataOnDemand.rnd = new SecureRandom();    
    
    private List<Skill> SkillDataOnDemand.data;    
    
    public Skill SkillDataOnDemand.getNewTransientSkill(int index) {    
        edu.drexel.goodwin.cpd.domain.Skill obj = new edu.drexel.goodwin.cpd.domain.Skill();        
        obj.setSubject("subject_" + index);        
        return obj;        
    }    
    
    public Skill SkillDataOnDemand.getRandomSkill() {    
        init();        
        Skill obj = data.get(rnd.nextInt(data.size()));        
        return Skill.findSkill(obj.getId());        
    }    
    
    public boolean SkillDataOnDemand.modifySkill(Skill obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void SkillDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = edu.drexel.goodwin.cpd.domain.Skill.findSkillEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Skill' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<edu.drexel.goodwin.cpd.domain.Skill>();        
        for (int i = 0; i < 10; i++) {        
            edu.drexel.goodwin.cpd.domain.Skill obj = getNewTransientSkill(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
