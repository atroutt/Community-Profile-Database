package edu.drexel.goodwin.cpd.domain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import edu.drexel.goodwin.cpd.domain.Interest;

@RooDataOnDemand(entity = Interest.class)
public class InterestDataOnDemand {
	
	public Set<Interest> getRandomInterests(Random random) {
		Set<Interest> interests = new HashSet<Interest>();
		int count = random.nextInt(13);
		for (int i = 0; i < count; i++) {
			int number = random.nextInt(13);
			Interest interest = Interest.findInterest((long) number);
			if (interest != null && !interests.contains(interest)) {
				interests.add(interest);
			}
		}
		return interests;
	}
}
