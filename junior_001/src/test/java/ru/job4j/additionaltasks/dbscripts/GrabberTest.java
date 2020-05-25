package ru.job4j.additionaltasks.dbscripts;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GrabberTest {

    @Test
    public void whenGet() {
        VulnerabilityScriptWithoutDb v1 = new VulnerabilityScriptWithoutDb(10);
        VulnerabilityScriptWithoutDb v2 = new VulnerabilityScriptWithoutDb(1);
        VulnerabilityScriptWithoutDb v3 = new VulnerabilityScriptWithoutDb(5);
        VulnerabilityScriptWithoutDb v4 = new VulnerabilityScriptWithoutDb(7, Arrays.asList(v3, v1));
        VulnerabilityScriptWithoutDb v5 = new VulnerabilityScriptWithoutDb(22, Arrays.asList(v4, v2));
        Grabber testGrabber = new Grabber();
        testGrabber.getDependenciesFromObj(v5);
        int result = testGrabber.getDependencyID();
        assertThat(result, is(1));
        result = testGrabber.getDependencyID();
        assertThat(result, is(10));
        result = testGrabber.getDependencyID();
        assertThat(result, is(5));
        result = testGrabber.getDependencyID();
        assertThat(result, is(7));
        result = testGrabber.getDependencyID();
        assertThat(result, is(22));
    }

}