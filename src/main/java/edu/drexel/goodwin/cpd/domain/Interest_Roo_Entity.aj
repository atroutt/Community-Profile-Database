package edu.drexel.goodwin.cpd.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.springframework.transaction.annotation.Transactional;

import edu.drexel.goodwin.cpd.domain.Interest;

privileged aspect Interest_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Interest.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Interest.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Interest.version;    
    
    public Long Interest.getId() {    
        return this.id;        
    }    
    
    public void Interest.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Interest.getVersion() {    
        return this.version;        
    }    
    
    public void Interest.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Interest.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Interest.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Interest attached = this.entityManager.find(Interest.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Interest.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Interest.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Interest merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager Interest.entityManager() {    
        EntityManager em = new Interest().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Interest.countInterests() {    
        return (Long) entityManager().createQuery("select count(o) from Interest o").getSingleResult();        
    }    
    
    public static Interest Interest.findInterest(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Interest");        
        return entityManager().find(Interest.class, id);        
    }    
    
    public static List<Interest> Interest.findInterestEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Interest o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
