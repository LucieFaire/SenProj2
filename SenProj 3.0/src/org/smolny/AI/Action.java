package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class Action {

    public static final String GOUP = "goUp";
    public static final String GODOWN = "goDown";
    public static final String GOLEFT = "goLeft";
    public static final String GORIGHT = "goRight";
    public static final String EAT = "eat";
    public static final String DIE = "die";
    public static final String CREATEAGENT = "createAgent";
    public static final String ADDAGENT = "addAgent";

    private String action;
    // add additional information to hold

    public Action(String a) {
        this.action = a;
    }

    public String getAction() {
        return this.action;
    }


}
