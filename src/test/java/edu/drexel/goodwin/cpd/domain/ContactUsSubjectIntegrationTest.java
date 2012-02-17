package edu.drexel.goodwin.cpd.domain;

import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import edu.drexel.goodwin.cpd.domain.ContactUsSubject;

import org.junit.Test;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext-security.xml" })
@RooIntegrationTest(entity = ContactUsSubject.class)
public class ContactUsSubjectIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
