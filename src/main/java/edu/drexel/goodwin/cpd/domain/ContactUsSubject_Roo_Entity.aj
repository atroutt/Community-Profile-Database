package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.ContactUsSubject;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ContactUsSubject_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager ContactUsSubject.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long ContactUsSubject.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer ContactUsSubject.version;    
    
    public Long ContactUsSubject.getId() {    
        return this.id;        
    }    
    
    public void ContactUsSubject.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer ContactUsSubject.getVersion() {    
        return this.version;        
    }    
    
    public void ContactUsSubject.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void ContactUsSubject.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void ContactUsSubject.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            ContactUsSubject attached = this.entityManager.find(ContactUsSubject.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void ContactUsSubject.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void ContactUsSubject.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        ContactUsSubject merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager ContactUsSubject.entityManager() {    
        EntityManager em = new ContactUsSubject().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long ContactUsSubject.countContactUsSubjects() {    
        return (Long) entityManager().createQuery("select count(o) from ContactUsSubject o").getSingleResult();        
    }    
    
    public static List<ContactUsSubject> ContactUsSubject.findAllContactUsSubjects() {    
        return entityManager().createQuery("select o from ContactUsSubject o").getResultList();        
    }    
    
    public static ContactUsSubject ContactUsSubject.findContactUsSubject(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of ContactUsSubject");        
        return entityManager().find(ContactUsSubject.class, id);        
    }    
    
    public static List<ContactUsSubject> ContactUsSubject.findContactUsSubjectEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from ContactUsSubject o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
