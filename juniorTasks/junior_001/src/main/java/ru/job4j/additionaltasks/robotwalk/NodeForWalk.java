package ru.job4j.additionaltasks.robotwalk;

/**
 * Класс содержит координаты по X и Y
 * и расстояние от начала передвижения.
 */
public class NodeForWalk {

    private int xcoord;
    private int ycoord;
    private int way;

    public NodeForWalk(int xcoord, int ycoord, int way) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.way = way;
    }

    public int getX() {
        return xcoord;
    }

    public int getY() {
        return ycoord;
    }

    public int getWay() {
        return way;
    }

}
