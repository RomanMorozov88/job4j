package ru.job4j.generic.store;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StoreTest {

    private RoleStore rs;
    private UserStore us;

    @Before
    public void setUp() {
        rs = new RoleStore(4);
        us = new UserStore(4);
    }

    //Проверяем .add Role. и .findById
    @Test
    public void addRoleMethodTest() {
        Role r1 = new Role("OneRole");
        rs.add(r1);
        Role result = rs.findById("OneRole");
        assertThat(result, is(r1));
    }

    //Проверяем .add User. и .findById
    @Test
    public void addUserMethodTest() {
        User u1 = new User("OneUser");
        us.add(u1);
        User result = us.findById("OneUser");
        assertThat(result, is(u1));
    }

    //Проверяем .replace Role.
    @Test
    public void replaceRoleMethodTest() {
        Role r1 = new Role("OneRole");
        Role r2 = new Role("TwoRole");
        rs.add(r1);
        rs.replace("OneRole", r2);
        Role result = rs.findById("TwoRole");
        assertThat(result, is(r2));
    }

    //Проверяем .remove и заодно .iterator.
    @Test
    public void removeIntMethodTest() {
        Role r1 = new Role("OneRole");
        Role r2 = new Role("TwoRole");
        Role r3 = new Role("ThreeRole");
        Role r4 = new Role("FourRole");

        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        rs.add(r4);

        //Удаляем элемент "Three".
        rs.delete("ThreeRole");

        Iterator<Role> it = rs.warehouse.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(r1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(r2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(r4));
        assertThat(it.hasNext(), is(false));
    }
}