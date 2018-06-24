package ru.job4j.max;

public class Max {
    public int max(int first, int second) {
        return first > second ? first : second;
    }
    public int max(int first, int second, int third) {
        int temp1 = this.max(first, second);
        int temp2 = this.max(third, temp1);
        return temp2;
    }
}
