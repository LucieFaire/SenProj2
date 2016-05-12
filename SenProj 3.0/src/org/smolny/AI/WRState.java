package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class WRState implements State {

    public static final int EAT     = 1; // 0001
    public static final int ENEMY   = 2; // 0010
    public static final int HUNGRY = 4; // 0100
    //public static final int PARTNER  = 8; // 1000

    public final int state;

    public WRState(int state) {
        this.state = state;
    }

    public WRState(
            boolean eat,
            boolean enemy,
            boolean hunger) {
        int s = 0;
        if(eat)
            s = s | EAT;
        if(enemy)
            s = s | ENEMY;
        if(hunger)
            s = s | HUNGRY;
        state = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WRState wrState = (WRState) o;

        return state == wrState.state;

    }

    @Override
    public int hashCode() {
        return state;
    }

    public static int numberOfStates() {
        return (EAT | ENEMY | HUNGRY) + 1;
    }
}
