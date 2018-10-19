package ru.job4j.calculatefunctions;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculateFuncTest {

    /**
     * Считаем функцию y = 2*x +5.
     */
    @Test
    public void whenLinear() {
        CalculateFunc calc = new CalculateFunc();
        List<Double> result = calc.diapason(
                0, 2,
                (index) -> {
                    return 2 * index + 5;
                }
        );
        List<Double> expect = Arrays.asList(5.0, 7.0, 9.0);
        assertThat(result, is(expect));
    }

    /**
     * Считаем функцию y = x*x.
     */
    @Test
    public void whenSquare() {
        CalculateFunc calc = new CalculateFunc();
        List<Double> result = calc.diapason(
                0, 3,
                (index) -> {
                    return Math.pow(index, 2);
                }
        );
        List<Double> expect = Arrays.asList(0.0, 1.0, 4.0, 9.0);
        assertThat(result, is(expect));
    }

    /**
     * Считаем натуральный логарифм.
     */
    @Test
    public void whenLn() {
        CalculateFunc calc = new CalculateFunc();
        List<Double> result = calc.diapason(
                1, 3,
                (index) -> {
                    return Math.log(index);
                }
        );
        List<Double> expect = Arrays.asList(0.0, 0.6931471805599453, 1.0986122886681098);
        assertThat(result, is(expect));
    }
}