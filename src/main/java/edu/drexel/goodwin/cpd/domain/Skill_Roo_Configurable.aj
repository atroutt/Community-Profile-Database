package edu.drexel.goodwin.cpd.domain;

import org.springframework.beans.factory.annotation.Configurable;

import edu.drexel.goodwin.cpd.domain.Skill;

privileged aspect Skill_Roo_Configurable {
    
    declare @type: Skill: @Configurable;    
    
}
