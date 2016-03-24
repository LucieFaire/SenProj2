package org.smolny.utils;

/**
 * Created by Asus on 24.03.2016.
 */
public class Point {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Point() {}

    public static Point create(int x, int y) {
        Point point = new Point();
        point.x = x;
        point.y = y;
        return point;
    }

    public Point plus(Point point) {
        return create(x + point.x, y + point.y);
    }

    public Point minus(Point point) {
        return create(x - point.x, y - point.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

}
