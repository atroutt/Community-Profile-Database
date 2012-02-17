package edu.drexel.goodwin.cpd.domain;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.springframework.transaction.annotation.Transactional;

import edu.drexel.goodwin.cpd.domain.Researcher;

privileged aspect Researcher_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Researcher.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Researcher.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Researcher.version;    
    
    public Long Researcher.getId() {    
        return this.id;        
    }    
    
    public void Researcher.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Researcher.getVersion() {    
        return this.version;        
    }    
    
    public void Researcher.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Researcher.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Researcher.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Researcher attached = this.entityManager.find(Researcher.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Researcher.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Researcher.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Researcher merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static Researcher Researcher.findResearcher(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Researcher");        
        return entityManager().find(Researcher.class, id);        
    }    
    
}
