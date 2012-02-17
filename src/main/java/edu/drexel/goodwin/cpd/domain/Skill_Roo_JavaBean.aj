package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.Skill;

privileged aspect Skill_Roo_JavaBean {
    
    public String Skill.getSubject() {    
        return this.subject;        
    }    
    
    public void Skill.setSubject(String subject) {    
        this.subject = subject;        
    }    
    
}
