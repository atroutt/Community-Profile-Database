package edu.drexel.goodwin.cpd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import edu.drexel.goodwin.cpd.domain.Interest;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class InterestTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Interest.countInterests();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Interest.countInterests());
    }
}
