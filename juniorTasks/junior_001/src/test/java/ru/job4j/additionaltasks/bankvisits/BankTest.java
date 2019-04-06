package ru.job4j.additionaltasks.bankvisits;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BankTest {

    @Test
    public void whenOneVisit() {
        List<Bank.Visit> visits = Arrays.asList(
                new Bank.Visit(time(8, 10), time(8, 20))
        );
        assertThat(
                new Bank().max(visits),
                is(
                        Arrays.asList(
                                new Bank.Info(
                                        1, time(8, 10), time(8, 20)
                                )
                        )
                )
        );
    }

    @Test
    public void whenCrossTwoVisit() {
        List<Bank.Visit> visits = Arrays.asList(
                new Bank.Visit(time(8, 10), time(8, 50)),
                new Bank.Visit(time(8, 30), time(9, 15))
        );
        assertThat(
                new Bank().max(visits),
                is(
                        Arrays.asList(
                                new Bank.Info(
                                        2, time(8, 30), time(8, 50)
                                )
                        )
                )
        );
    }

    @Test
    public void whenMultiCrossFiveVisit() {
        List<Bank.Visit> visits = Arrays.asList(
                new Bank.Visit(time(8, 00), time(20, 00)),
                new Bank.Visit(time(8, 10), time(8, 50)),
                new Bank.Visit(time(11, 30), time(12, 00)),
                new Bank.Visit(time(11, 50), time(12, 50)),
                new Bank.Visit(time(19, 30), time(19, 55))
        );
        assertThat(
                new Bank().max(visits),
                is(
                        Arrays.asList(
                                new Bank.Info(
                                        2, time(8, 10), time(8, 50)
                                ),
                                new Bank.Info(
                                        3, time(11, 50), time(12, 00)
                                ),
                                new Bank.Info(
                                        2, time(19, 30), time(19, 55)
                                )
                        )
                )
        );
    }

    private long time(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}