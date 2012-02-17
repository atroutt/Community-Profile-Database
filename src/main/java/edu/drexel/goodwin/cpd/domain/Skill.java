package edu.drexel.goodwin.cpd.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
@RooEntity
public class Skill implements Comparable<Skill> {

    @NotNull
    @Size(min = 1, max = 50)
    private String subject;
    
    @Override
    public String toString() {
    	return subject;
    }

	@Override
	public int compareTo(Skill other) {
		return toString().compareTo(other.toString());
	}
	
	public static List<Skill> findAllSkills() {    
		@SuppressWarnings("unchecked")
        List<Skill> resultList = Skill.entityManager().createQuery("select o from Skill o").getResultList();
        Collections.sort(resultList);
		return resultList;        
    }    
}
