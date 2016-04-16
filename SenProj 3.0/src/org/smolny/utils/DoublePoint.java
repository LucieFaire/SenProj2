package org.smolny.utils;

/**
 * Created by Asus on 14.04.2016.
 */
public class DoublePoint {

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private DoublePoint() {}

    public static DoublePoint create(double x, double y) {
        DoublePoint point = new DoublePoint();
        point.x = x;
        point.y = y;
        return point;
    }

    public DoublePoint plus(DoublePoint point) {
        return create(x + point.x, y + point.y);
    }

    public DoublePoint minus(DoublePoint point) {
        return create(x - point.x, y - point.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoublePoint point = (DoublePoint) o;


        if (x != point.x) return false;
        return y == point.y;

    }

    @Override
    public int hashCode() {
        long l1 = Double.doubleToLongBits(x);
        long l2 = Double.doubleToLongBits(y);
        return 31 * (Long.hashCode(l1) ^ Long.hashCode(l2));
    }

}
