package org.smolny.utils;

/**
 * Created by Asus on 24.03.2016.
 */
public class IntPoint {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private IntPoint() {}

    public static IntPoint create(int x, int y) {
        IntPoint point = new IntPoint();
        point.x = x;
        point.y = y;
        return point;
    }

    public IntPoint plus(IntPoint point) {
        return create(x + point.x, y + point.y);
    }

    public IntPoint minus(IntPoint point) {
        return create(x - point.x, y - point.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntPoint point = (IntPoint) o;

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
