package edu.drexel.goodwin.cpd.domain;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.roo.addon.property.editor.RooEditor;

@RooEditor(providePropertyEditorFor = Interest.class)
public class InterestEditor extends java.beans.PropertyEditorSupport {

	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	public String getAsText() {    
        Object obj = getValue();        
        if (obj == null) {        
            return null;            
        }        
        return (String) typeConverter.convertIfNecessary(((Interest) obj).getId(), String.class);        
    }

	public void setAsText(String text) {    
        if (text == null || 0 == text.length()) {        
            setValue(null);            
            return;            
        }        
        
        Long identifier = (Long) typeConverter.convertIfNecessary(text, Long.class);        
        if (identifier == null) {        
            setValue(null);            
            return;            
        }        
        
        setValue(Interest.findInterest(identifier));        
    }
}
