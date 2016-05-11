package org.smolny.AI;

import org.smolny.agent.Agent;
import org.smolny.agent.memory.Memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsh on 4/18/16.
 */
public class RFLModule {

    private List<AgentHistory> history = new ArrayList<>();

    public static void updateReward() {

    }

    public static State newState(Agent a, Memory memo) {
        return null;
    }

    public static Action learning(State state) {
        return null;
    }

    private void updateHistory(State state, Action action, int reward) {
        AgentHistory event = new AgentHistory();
        event.setState(state);
        event.setAction(action);
        event.setReward(reward);
        history.add(event);
    }
}
