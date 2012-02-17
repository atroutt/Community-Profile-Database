package edu.drexel.goodwin.cpd.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import edu.drexel.goodwin.cpd.exception.UserSaltAlreadyInitializedException;

@Entity
@RooJavaBean
@RooEntity
public class Researcher implements UserDetails {

	private static final long serialVersionUID = 3607476610441705138L;

	@NotNull
	@Size(min = 1, max = 50)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 50)
	private String lastName;

	@NotNull
	@Size(min = 1, max = 100)
	private String jobTitle;

	@NotNull
	@Size(min = 1, max = 100)
	private String organization;

	private String website;

	private String bibliography;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Interest> interests = new HashSet<Interest>();

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Skill> skills = new HashSet<Skill>();

	@NotNull
	@Size(min = 3, max = 100)
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String email;

	@NotNull
	private String password;

	@Transient
	private String passwordConfirm;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ProfilePicture profilePicture;

	private boolean admin = false;

	private boolean deleted = false;

	private String salt;

	public void setWebsite(String website) {
		if (StringUtils.hasLength(website) && website.indexOf("://") == -1) {
			website = "http://" + website;
		}
		this.website = website;
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Researcher().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	/* ****************************************************************
	 * These are methods that I have customized from the auto-generated versions ***************************************************************
	 */

	@SuppressWarnings("unchecked")
	public static List<Researcher> findAllResearchers() {
		return entityManager().createQuery("select o from Researcher o  where deleted = false order by lastName").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Researcher> findResearcherEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("select o from Researcher o where deleted = false order by lastName").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static long countResearchers() {
		return (Long) entityManager().createQuery("select count(o) from Researcher o where deleted = false ").getSingleResult();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ").append(getId()).append(", ");
		sb.append("Version: ").append(getVersion()).append(", ");
		sb.append("Name: ").append(getFirstName()).append(" ").append(getLastName()).append(", ");
		sb.append("JobTitle: ").append(getJobTitle()).append(", ");
		sb.append("Organization: ").append(getOrganization()).append(", ");
		sb.append("Email: ").append(getEmail()).append(", ");
		return sb.toString();
	}

	public static Query findResearchersByEmailEquals(String email) {
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException("The email argument is required");
		}
		EntityManager em = Researcher.entityManager();
		Query q = em.createQuery("SELECT Researcher FROM Researcher AS researcher WHERE researcher.email = :email");
		q.setParameter("email", email);
		return q;
	}

	@SuppressWarnings("unchecked")
	public static List<Researcher> findResearchersBySkillsAndInterests(Set<Skill> skills, Set<Interest> interests, int firstResult, int maxResults) {
		if (skills == null) {
			skills = new HashSet<Skill>();
		}
		if (interests == null) {
			interests = new HashSet<Interest>();
		}
		EntityManager em = Researcher.entityManager();
		StringBuilder queryBuilder = new StringBuilder("SELECT Researcher FROM Researcher AS researcher WHERE ");
		int numSkills;
		for (numSkills = 0; numSkills < skills.size(); numSkills++) {
			if (numSkills == 0) {
				queryBuilder.append(" (");
			}
			if (numSkills > 0) {
				queryBuilder.append(" OR");
			}
			queryBuilder.append(" :skills_item").append(numSkills).append(" MEMBER OF researcher.skills");
		}
		for (int i = 0; i < interests.size(); i++) {
			if (i == 0 && numSkills == 0) {
				queryBuilder.append(" (");
			}
			if (i > 0 || numSkills > 0) {
				queryBuilder.append(" OR");
			}
			queryBuilder.append(" :interests_item").append(i).append(" MEMBER OF researcher.interests");
		}
		if (skills.size() > 0 || interests.size() > 0) {
			queryBuilder.append(" ) AND");
		}
		queryBuilder.append(" researcher.deleted = false");
		queryBuilder.append(" order by researcher.lastName");
		Query q = em.createQuery(queryBuilder.toString());
		int i = 0;
		for (Skill skill : skills) {
			q.setParameter("skills_item" + i++, skill);
		}
		i = 0;
		for (Interest interest : interests) {
			q.setParameter("interests_item" + i++, interest);
		}
		return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static long countResearchersBySkillsAndInterests(Set<Skill> skills, Set<Interest> interests) {
		if (skills == null) {
			skills = new HashSet<Skill>();
		}
		if (interests == null) {
			interests = new HashSet<Interest>();
		}
		EntityManager em = Researcher.entityManager();
		StringBuilder queryBuilder = new StringBuilder("SELECT count(Researcher.id) FROM Researcher AS researcher WHERE ");
		int numSkills;
		for (numSkills = 0; numSkills < skills.size(); numSkills++) {
			if (numSkills == 0) {
				queryBuilder.append(" (");
			}
			if (numSkills > 0) {
				queryBuilder.append(" OR");
			}
			queryBuilder.append(" :skills_item").append(numSkills).append(" MEMBER OF researcher.skills");
		}
		for (int i = 0; i < interests.size(); i++) {
			if (i == 0 && numSkills == 0) {
				queryBuilder.append(" (");
			}
			if (i > 0 || numSkills > 0) {
				queryBuilder.append(" OR");
			}
			queryBuilder.append(" :interests_item").append(i).append(" MEMBER OF researcher.interests");
		}
		if (skills.size() > 0 || interests.size() > 0) {
			queryBuilder.append(" ) AND");
		}
		queryBuilder.append(" researcher.deleted = false");
		Query q = em.createQuery(queryBuilder.toString());
		int i = 0;
		for (Skill skill : skills) {
			q.setParameter("skills_item" + i++, skill);
		}
		i = 0;
		for (Interest interest : interests) {
			q.setParameter("interests_item" + i++, interest);
		}
		return (Long) q.getSingleResult();
	}

	/* ****************************************************************
	 * These are methods for authentication via ResearcherDetailsService ***************************************************************
	 */

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));
		if (admin) {
			authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}
		return authList;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !isDeleted();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		if (this.salt != null) {
			throw new UserSaltAlreadyInitializedException("This user's salt has already been initialized.");
		}
		this.salt = salt;

	}
}
