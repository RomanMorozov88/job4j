package ru.job4j.zadachi;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class OtrezkiTest {


    @Test
    public void whenABInCD() {
        Otrezki srvn = new Otrezki();
        int[] ab = new int[] {3, 8};
        int[] cd = new int[] {1, 9};
        boolean result = srvn.srv(ab, cd);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenABnotCD() {
        Otrezki srvn = new Otrezki();
        int[] ab = new int[] {3, 5};
        int[] cd = new int[] {6, 9};
        boolean result = srvn.srv(ab, cd);
        boolean expect = false;
        assertThat(result, is(expect));
    }

    @Test
    public void whenABinCD01() {
        Otrezki srvn = new Otrezki();
        int[] ab = new int[] {3, 8};
        int[] cd = new int[] {6, 9};
        boolean result = srvn.srv(ab, cd);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenCDInAB() {
        Otrezki srvn = new Otrezki();
        int[] ab = new int[] {1, 8};
        int[] cd = new int[] {3, 7};
        boolean result = srvn.srv(ab, cd);
        boolean expect = true;
        assertThat(result, is(expect));
    }
}