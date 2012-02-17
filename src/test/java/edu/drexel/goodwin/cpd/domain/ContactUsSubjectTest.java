package edu.drexel.goodwin.cpd.domain;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.junit.Test;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ContactUsSubjectTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        ContactUsSubject.countContactUsSubjects();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, ContactUsSubject.countContactUsSubjects());
    }
}
