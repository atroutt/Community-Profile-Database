package edu.drexel.goodwin.cpd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import edu.drexel.goodwin.cpd.domain.Researcher;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ResearcherTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Researcher.countResearchers();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Researcher.countResearchers());
    }
}
