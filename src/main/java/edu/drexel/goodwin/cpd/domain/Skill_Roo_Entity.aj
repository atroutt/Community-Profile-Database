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

import edu.drexel.goodwin.cpd.domain.Skill;

privileged aspect Skill_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Skill.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Skill.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Skill.version;    
    
    public Long Skill.getId() {    
        return this.id;        
    }    
    
    public void Skill.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Skill.getVersion() {    
        return this.version;        
    }    
    
    public void Skill.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Skill.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Skill.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Skill attached = this.entityManager.find(Skill.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Skill.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Skill.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Skill merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager Skill.entityManager() {    
        EntityManager em = new Skill().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Skill.countSkills() {    
        return (Long) entityManager().createQuery("select count(o) from Skill o").getSingleResult();        
    }    
    
    public static Skill Skill.findSkill(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Skill");        
        return entityManager().find(Skill.class, id);        
    }    
    
    public static List<Skill> Skill.findSkillEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Skill o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
