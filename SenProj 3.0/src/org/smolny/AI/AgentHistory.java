package org.smolny.AI;

/**
 * Created by dsh on 4/30/16.
 */
public class AgentHistory {

    private State state;
    private Action action;
    private int reward;

    public AgentHistory() {

    }

    public Action getAction() {
        return action;
    }

    public State getState() {
        return state;
    }

    public int getReward() {
        return reward;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setState(State state) {
        this.state = state;
    }
}
