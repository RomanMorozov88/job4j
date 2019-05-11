package ru.job4j.ood.srp;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class InteractCalcTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String separator = System.lineSeparator();

    public void setUp(String testString) {
        ByteArrayInputStream in = new ByteArrayInputStream(testString.getBytes());
        PrintStream printStream = new PrintStream(out);
        InteractCalc testCalc = new InteractCalc(in, printStream::println);
        testCalc.mainLoopForCalculate();
    }

    @Test
    public void whenSimpeAdditionAndStop() {
        String testString = new StringBuilder()
                .append("8 + 8").append(separator)
                .append("stop").append(separator).toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("16.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenSimpeSubtractionAndStop() {
        String testString = new StringBuilder()
                .append("16.0 -8").append(separator)
                .append("stop").append(separator).toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("8.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenSimpeDivisionAndStop() {
        String testString = new StringBuilder()
                .append("25/5.0").append(separator)
                .append("stop").append(separator).toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("5.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenSimpeMmultiplicationAndStop() {
        String testString = new StringBuilder()
                .append("2.0* 2.0").append(separator)
                .append("stop").append(separator).toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("4.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenFirstOperationThenSecondOperationAndStop() {
        String testString = new StringBuilder()
                .append("2.0* 2.0").append(separator)
                .append("+ 2").append(separator)
                .append("stop").append(separator).toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("4.0").append(separator)
                                .append("6.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenWrongInput() {
        String testString = new StringBuilder()
                .append("fg+y.0").append(separator)
                .toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenCosSqrt() {
        String testString = new StringBuilder()
                .append("cos0").append(separator)
                .append("+ 3").append(separator)
                .append("sqrt").append(separator)
                .append(" sin 90").append(separator)
                .append("stop").append(separator)
                .toString();
        setUp(testString);
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append("1.0").append(separator)
                                .append("4.0").append(separator)
                                .append("2.0").append(separator)
                                .append("1.0").append(separator)
                                .append("There is no mathematical expression.").append(separator)
                                .toString()
                )
        );
    }
}