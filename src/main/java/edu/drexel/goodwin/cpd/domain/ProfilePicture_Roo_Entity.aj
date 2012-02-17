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

import edu.drexel.goodwin.cpd.domain.ProfilePicture;

privileged aspect ProfilePicture_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager ProfilePicture.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long ProfilePicture.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer ProfilePicture.version;    
    
    public Long ProfilePicture.getId() {    
        return this.id;        
    }    
    
    public void ProfilePicture.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer ProfilePicture.getVersion() {    
        return this.version;        
    }    
    
    public void ProfilePicture.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void ProfilePicture.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void ProfilePicture.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            ProfilePicture attached = this.entityManager.find(ProfilePicture.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void ProfilePicture.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void ProfilePicture.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        ProfilePicture merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager ProfilePicture.entityManager() {    
        EntityManager em = new ProfilePicture().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long ProfilePicture.countProfilePictures() {    
        return (Long) entityManager().createQuery("select count(o) from ProfilePicture o").getSingleResult();        
    }    
    
    public static List<ProfilePicture> ProfilePicture.findAllProfilePictures() {    
        return entityManager().createQuery("select o from ProfilePicture o").getResultList();        
    }    
    
    public static ProfilePicture ProfilePicture.findProfilePicture(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of ProfilePicture");        
        return entityManager().find(ProfilePicture.class, id);        
    }    
    
    public static List<ProfilePicture> ProfilePicture.findProfilePictureEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from ProfilePicture o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
