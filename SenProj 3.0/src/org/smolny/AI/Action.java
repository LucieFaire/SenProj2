package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class Action {

    public static final String EAT = "eat";
    public static final String DIE = "die";
    //public static final String PARTNER = "partner";
    public static final String RUNAWAY = "runAway";

    private String action;
    // add additional information to hold

    public Action(String a) {
        this.action = a;
    }

    public String getAction() {
        return this.action;
    }


}
