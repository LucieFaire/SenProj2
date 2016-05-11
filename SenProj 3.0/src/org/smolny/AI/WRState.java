package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class WRState implements State {
    public boolean nearEnemy;
    public boolean nearPartner;
    public boolean nearFood;
    public boolean wantsToEat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WRState wrState = (WRState) o;

        if (nearEnemy != wrState.nearEnemy) return false;
        if (nearPartner != wrState.nearPartner) return false;
        if (nearFood != wrState.nearFood) return false;
        return wantsToEat == wrState.wantsToEat;

    }

    @Override
    public int hashCode() {
        int result = (nearEnemy ? 1 : 0);
        result = 31 * result + (nearPartner ? 1 : 0);
        result = 31 * result + (nearFood ? 1 : 0);
        result = 31 * result + (wantsToEat ? 1 : 0);
        return result;
    }
}
