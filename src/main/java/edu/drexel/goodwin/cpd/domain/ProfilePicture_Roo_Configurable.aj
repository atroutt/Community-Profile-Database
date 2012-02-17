package edu.drexel.goodwin.cpd.domain;

import org.springframework.beans.factory.annotation.Configurable;

import edu.drexel.goodwin.cpd.domain.ProfilePicture;

privileged aspect ProfilePicture_Roo_Configurable {
    
    declare @type: ProfilePicture: @Configurable;    
    
}
