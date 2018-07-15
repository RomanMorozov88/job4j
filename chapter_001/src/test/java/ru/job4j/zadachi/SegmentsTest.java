package ru.job4j.zadachi;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class SegmentsTest {


    @Test
    public void whenABInCD() {
        Segments srvn = new Segments();
        boolean result = srvn.predict(1,4,4,9);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenABnotCD() {
        Segments srvn = new Segments();
        boolean result = srvn.predict(3,5,6,9);
        boolean expect = false;
        assertThat(result, is(expect));
    }

    @Test
    public void whenABinCD01() {
        Segments srvn = new Segments();
        boolean result = srvn.predict(3,8,0,9);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenCDInAB() {
        Segments srvn = new Segments();
        boolean result = srvn.predict(1,8,3,7);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenCDNotAB() {
        Segments srvn = new Segments();
        boolean result = srvn.predict(1,8,-3,0);
        boolean expect = false;
        assertThat(result, is(expect));
    }
}