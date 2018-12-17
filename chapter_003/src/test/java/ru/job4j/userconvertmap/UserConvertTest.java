package ru.job4j.userconvertmap;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserConvertTest {

    @Test
    public void whenHashMapHaveUserA() {
        UserConvert list = new UserConvert();
        User a = new User("A", "Aa");
        User b = new User("B", "Bb");
        List<User> userList = List.of(
                a,
                b
        );
        boolean result = list.process(userList).containsValue(a);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenHashMapHave3Elements() {
        UserConvert list = new UserConvert();
        User a = new User("A", "Aa");
        User b = new User("B", "Bb");
        User c = new User("C", "Cc");
        List<User> userList = List.of(
                a,
                b,
                c
        );
        int result = list.process(userList).size();
        int expect = 3;
        assertThat(result, is(expect));
    }
}