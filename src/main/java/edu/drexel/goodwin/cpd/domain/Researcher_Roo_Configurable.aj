package edu.drexel.goodwin.cpd.domain;

import org.springframework.beans.factory.annotation.Configurable;

import edu.drexel.goodwin.cpd.domain.Researcher;

privileged aspect Researcher_Roo_Configurable {
    
    declare @type: Researcher: @Configurable;    
    
}
