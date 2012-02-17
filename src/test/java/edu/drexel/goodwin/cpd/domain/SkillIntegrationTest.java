package edu.drexel.goodwin.cpd.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import edu.drexel.goodwin.cpd.domain.Skill;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext-security.xml" })
@RooIntegrationTest(entity = Skill.class)
public class SkillIntegrationTest {

	
    @Test
    public void testMarkerMethod() {
    }
    

}
