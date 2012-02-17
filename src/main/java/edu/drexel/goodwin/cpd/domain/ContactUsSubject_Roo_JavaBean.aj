package edu.drexel.goodwin.cpd.domain;

import java.lang.String;

privileged aspect ContactUsSubject_Roo_JavaBean {
    
    public String ContactUsSubject.getName() {    
        return this.name;        
    }    
    
    public void ContactUsSubject.setName(String name) {    
        this.name = name;        
    }    
    
}
