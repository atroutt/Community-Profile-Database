package edu.drexel.goodwin.cpd.domain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import edu.drexel.goodwin.cpd.domain.Skill;

@RooDataOnDemand(entity = Skill.class)
public class SkillDataOnDemand {
	
	public Set<Skill> getRandomSkills(Random random) {
		Set<Skill> skills = new HashSet<Skill>();
		int count = random.nextInt(8);
		for (int i = 0; i < count; i++) {
			int number = random.nextInt(8) + 15;
			Skill skill = Skill.findSkill((long) number);
			if (skill != null && !skills.contains(skill)) {
				skills.add(skill);
			}
		}
		return skills;
	}
}
