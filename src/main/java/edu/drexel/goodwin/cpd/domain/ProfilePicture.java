package edu.drexel.goodwin.cpd.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
@RooEntity
public class ProfilePicture {

	@NotNull
    private String mimeType;
	
	@NotNull
	private int width;
	
	@NotNull
	private int height;
    
	@Lob
    private byte[] bytes;
	
	@Override
	public String toString() {
		return "ProfilePicture id: " + getId() + " mimeType: " + mimeType;
	}
}
