package ru.job4j.userstorage;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenUseUserStorage() {
        User user1 = new User(1);
        User user2 = new User(2);
        User user3 = new User(3);
        UserStorage testStorage = new UserStorage();
        boolean result = testStorage.add(user1);
        assertThat(result, is(true));
        result = testStorage.add(user1);
        assertThat(result, is(false));
        testStorage.add(user2);
        result = testStorage.delete(user2);
        assertThat(result, is(true));
        result = testStorage.delete(user3);
        assertThat(result, is(false));

    }

    @Test
    public void whenTransfer() {
        User user1 = new User(1, 10);
        User user2 = new User(2);
        UserStorage testStorage = new UserStorage();
        testStorage.add(user1);
        testStorage.add(user2);
        boolean result = testStorage.transfer(user1.geId(), user2.geId(), 5);
        assertThat(result, is(true));
        assertThat(user1.getAmount(), is(5));
        assertThat(user2.getAmount(), is(5));
    }

}