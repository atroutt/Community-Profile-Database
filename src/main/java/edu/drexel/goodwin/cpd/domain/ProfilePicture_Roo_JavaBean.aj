package edu.drexel.goodwin.cpd.domain;

import edu.drexel.goodwin.cpd.domain.ProfilePicture;

privileged aspect ProfilePicture_Roo_JavaBean {
    
    public String ProfilePicture.getMimeType() {    
        return this.mimeType;        
    }    
    
    public void ProfilePicture.setMimeType(String mimeType) {    
        this.mimeType = mimeType;        
    }    
    
    public int ProfilePicture.getWidth() {    
        return this.width;        
    }    
    
    public void ProfilePicture.setWidth(int width) {    
        this.width = width;        
    }    
    
    public int ProfilePicture.getHeight() {    
        return this.height;        
    }    
    
    public void ProfilePicture.setHeight(int height) {    
        this.height = height;        
    }    
    
    public byte[] ProfilePicture.getBytes() {    
        return this.bytes;        
    }    
    
    public void ProfilePicture.setBytes(byte[] bytes) {    
        this.bytes = bytes;        
    }    
    
}
