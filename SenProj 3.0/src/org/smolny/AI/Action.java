package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class Action {

    public static final String EAT = "eat";
    public static final String PARTNER = "partner";
    public static final String RUNAWAY = "runAway";

    private String action;


    public Action(String a) {
        this.action = a;
    }

    public String getAction() {
        return this.action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action1 = (Action) o;

        return action.equals(action1.action);

    }

    @Override
    public int hashCode() {
        return action != null ? action.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "action " + action + '\'';
    }
}
