package edu.drexel.goodwin.cpd.domain;

import org.springframework.beans.factory.annotation.Configurable;

import edu.drexel.goodwin.cpd.domain.Interest;

privileged aspect Interest_Roo_Configurable {
    
    declare @type: Interest: @Configurable;    
    
}
