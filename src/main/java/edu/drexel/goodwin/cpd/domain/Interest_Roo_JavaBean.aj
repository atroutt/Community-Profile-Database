package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.Interest;

privileged aspect Interest_Roo_JavaBean {
    
    public String Interest.getSubject() {    
        return this.subject;        
    }    
    
    public void Interest.setSubject(String subject) {    
        this.subject = subject;        
    }    
    
}
