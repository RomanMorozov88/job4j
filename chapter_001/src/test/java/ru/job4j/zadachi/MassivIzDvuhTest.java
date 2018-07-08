package ru.job4j.zadachi;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MassivIzDvuhTest {

    @Test
    public void Arr1PlusArr201() {
        MassivIzDvuh sln = new MassivIzDvuh();
        int[] arr1 = new int[] {3, 8};
        int[] arr2 = new int[] {1, 9};
        int[] result = sln.sliyanie(arr1, arr2);
        int[] expect = {1, 3, 8, 9};
        assertThat(result, is(expect));
    }

    @Test
    public void Arr1PlusArr202() {
        MassivIzDvuh sln = new MassivIzDvuh();
        int[] arr1 = new int[] {1, 2, 3};
        int[] arr2 = new int[] {2, 4, 7, 8};
        int[] result = sln.sliyanie(arr1, arr2);
        int[] expect = {1, 2, 2, 3, 4, 7, 8};
        assertThat(result, is(expect));
    }
}
