

package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {

    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate doubleR = new ArrayDuplicate();
        String[] input = new String[]{"g", "h", "g", "r", "z", "h"};
        String[] result = doubleR.remove(input);
        String[] expect = {"g", "h", "r", "z"};
        assertThat(result, arrayContainingInAnyOrder(expect));
    }

    @Test
    public void whenWithoutDuplicate() {
        ArrayDuplicate doubleR = new ArrayDuplicate();
        String[] input = new String[]{"g", "h", "s", "r", "z", "u"};
        String[] result = doubleR.remove(input);
        String[] expect = {"g", "h", "s", "r", "z", "u"};
        assertThat(result, arrayContainingInAnyOrder(expect));
    }

    @Test
    public void whenRemoveDuplicates2() {
        ArrayDuplicate doubleR = new ArrayDuplicate();
        String[] input = new String[]{"f", "h", "g", "r", "g", "h"};
        String[] result = doubleR.remove(input);
        String[] expect = {"f", "h", "g", "r"};
        assertThat(result, arrayContainingInAnyOrder(expect));
    }

    @Test
    public void whenRemoveDuplicates3() {
        ArrayDuplicate doubleR = new ArrayDuplicate();
        String[] input = new String[]{"g", "h", "s", "r", "z", "z"};
        String[] result = doubleR.remove(input);
        String[] expect = {"g", "h", "s", "r", "z"};
        assertThat(result, arrayContainingInAnyOrder(expect));
    }

    @Test
    public void whenRemoveDuplicates4() {
        ArrayDuplicate doubleR = new ArrayDuplicate();
        String[] input = new String[]{"got", "home", "sea", "zero", "zero", "rider"};
        String[] result = doubleR.remove(input);
        String[] expect = {"got", "home", "sea", "zero", "rider"};
        assertThat(result, arrayContainingInAnyOrder(expect));
    }
}

