package ru.job4j.paint;

public class Paint {
    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }

    public static void main(String[] args) {
        Paint pull = new Paint();
        pull.draw(new Triangle());
        pull.draw(new Square());
    }
}