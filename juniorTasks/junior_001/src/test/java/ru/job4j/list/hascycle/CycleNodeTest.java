package ru.job4j.list.hascycle;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CycleNodeTest {

    private Node first = new Node(1);
    private Node two = new Node(2);
    private Node third = new Node(3);
    private Node four = new Node(4);

    @Test
    public void whenHaveCycleOne() {
        first.next = two;
        two.next = third;
        third.next = four;
        //Ссылка на first.
        four.next = first;
        assertThat(CycleNode.hasCycleTH(first), is(true));
    }

    @Test
    public void whenHaveCycleTwo() {
        first.next = two;
        two.next = third;
        //Ссылка на two.
        third.next = two;
        four.next = first;
        assertThat(CycleNode.hasCycleTH(first), is(true));
    }

    @Test
    public void whenHaveNotCycle() {
        first.next = two;
        two.next = third;
        third.next = four;
        assertThat(CycleNode.hasCycleTH(first), is(false));
    }
}