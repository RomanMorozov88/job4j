package ru.job4j.userConvertMap;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserConvertTest {

    @Test
    public void whenHashMapHaveUserA() {
        UserConvert list = new UserConvert();
        User A = new User("A", "Aa");
        User B = new User("B", "Bb");
        List<User> userList = new ArrayList<>();
        userList.add(A);
        userList.add(B);
        boolean result = list.process(userList).containsValue(A);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    @Test
    public void whenHashMapHave3Elements() {
        UserConvert list = new UserConvert();
        User A = new User("A", "Aa");
        User B = new User("B", "Bb");
        User C = new User("C", "Cc");
        List<User> userList = new ArrayList<>();
        userList.add(A);
        userList.add(B);
        userList.add(C);
        int result = list.process(userList).size();
        int expect = 3;
        assertThat(result, is(expect));
    }
}