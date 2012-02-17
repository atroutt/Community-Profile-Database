package edu.drexel.goodwin.cpd.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.domain.Skill;

public class ResearcherDto {
    
    private Long id;  
    private Integer version;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String organization;
    private String website;
    private String bibliography;
    private Set<Interest> interests = new HashSet<Interest>();
    private Set<Skill> skills = new HashSet<Skill>();
    private String email;
    private String password;
    private String passwordConfirm;
    private boolean admin = false;
    private boolean deleted = false;
    private String recaptcha_challenge_field;
    private String recaptcha_response_field;
    private transient MultipartFile uploadedPicture;
    private boolean hasProfilePicture;
    private Integer profilePictureWidth;
	private Integer profilePictureHeight;

	public String getRecaptcha_response_field() {
		return recaptcha_response_field;
	}

	public void setRecaptcha_response_field(String recaptchaResponseField) {
		recaptcha_response_field = recaptchaResponseField;
	}

	public String getRecaptcha_challenge_field() {
		return recaptcha_challenge_field;
	}

	public void setRecaptcha_challenge_field(String recaptchaChallengeField) {
		recaptcha_challenge_field = recaptchaChallengeField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBibliography() {
		return bibliography;
	}

	public void setBibliography(String bibliography) {
		this.bibliography = bibliography;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public MultipartFile getUploadedPicture() {
		return uploadedPicture;
	}

	public void setUploadedPicture(MultipartFile uploadedPicture) {
		this.uploadedPicture = uploadedPicture;
	}

	public boolean isHasProfilePicture() {
		return hasProfilePicture;
	}

	public void setHasProfilePicture(boolean hasProfilePicture) {
		this.hasProfilePicture = hasProfilePicture;
	}

	public void setProfilePictureWidth(Integer profilePictureWidth) {
		this.profilePictureWidth = profilePictureWidth;
	}

	public Integer getProfilePictureHeight() {
		return profilePictureHeight;
	}

	public void setProfilePictureHeight(Integer profilePictureHeight) {
		this.profilePictureHeight = profilePictureHeight;
	}

	public Integer getProfilePictureWidth() {
		return profilePictureWidth;
	}
	

}
