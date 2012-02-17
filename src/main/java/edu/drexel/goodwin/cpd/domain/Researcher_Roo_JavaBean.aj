package edu.drexel.goodwin.cpd.domain;

import java.util.Set;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.domain.ProfilePicture;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.domain.Skill;

privileged aspect Researcher_Roo_JavaBean {
    
    public String Researcher.getFirstName() {    
        return this.firstName;        
    }    
    
    public void Researcher.setFirstName(String firstName) {    
        this.firstName = firstName;        
    }    
    
    public String Researcher.getLastName() {    
        return this.lastName;        
    }    
    
    public void Researcher.setLastName(String lastName) {    
        this.lastName = lastName;        
    }    
    
    public String Researcher.getJobTitle() {    
        return this.jobTitle;        
    }    
    
    public void Researcher.setJobTitle(String jobTitle) {    
        this.jobTitle = jobTitle;        
    }    
    
    public String Researcher.getOrganization() {    
        return this.organization;        
    }    
    
    public void Researcher.setOrganization(String organization) {    
        this.organization = organization;        
    }    
    
    public String Researcher.getWebsite() {    
        return this.website;        
    }    
    
    public String Researcher.getBibliography() {    
        return this.bibliography;        
    }    
    
    public void Researcher.setBibliography(String bibliography) {    
        this.bibliography = bibliography;        
    }    
    
    public Set<Interest> Researcher.getInterests() {    
        return this.interests;        
    }    
    
    public void Researcher.setInterests(Set<Interest> interests) {    
        this.interests = interests;        
    }    
    
    public Set<Skill> Researcher.getSkills() {    
        return this.skills;        
    }    
    
    public void Researcher.setSkills(Set<Skill> skills) {    
        this.skills = skills;        
    }    
    
    public String Researcher.getEmail() {    
        return this.email;        
    }    
    
    public void Researcher.setEmail(String email) {    
        this.email = email;        
    }    
    
    public String Researcher.getPassword() {    
        return this.password;        
    }    
    
    public void Researcher.setPassword(String password) {    
        this.password = password;        
    }    
    
    public String Researcher.getPasswordConfirm() {    
        return this.passwordConfirm;        
    }    
    
    public void Researcher.setPasswordConfirm(String passwordConfirm) {    
        this.passwordConfirm = passwordConfirm;        
    }    
    
    public ProfilePicture Researcher.getProfilePicture() {    
        return this.profilePicture;        
    }    
    
    public void Researcher.setProfilePicture(ProfilePicture profilePicture) {    
        this.profilePicture = profilePicture;        
    }    
    
    public boolean Researcher.isAdmin() {    
        return this.admin;        
    }    
    
    public void Researcher.setAdmin(boolean admin) {    
        this.admin = admin;        
    }    
    
    public boolean Researcher.isDeleted() {    
        return this.deleted;        
    }    
    
    public void Researcher.setDeleted(boolean deleted) {    
        this.deleted = deleted;        
    }    
    
}
