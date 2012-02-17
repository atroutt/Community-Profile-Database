package edu.drexel.goodwin.cpd.domain;

import javax.persistence.Entity;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
@RooEntity
public class ContactUsSubject {

    private String name;
    
    @Override
    public String toString() {    
        return name;      
    }   
    
}
