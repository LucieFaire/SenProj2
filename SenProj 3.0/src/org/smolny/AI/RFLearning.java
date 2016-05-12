package org.smolny.AI;

import org.smolny.agent.Agent;
import org.smolny.agent.memory.Memory;

import java.util.HashMap;

/**
 * Created by dsh on 5/12/16.
 */
public interface RFLearning {

    HashMap<State, HashMap<Action, Double>> setQ();

    int updateReward(Action a);

    void learning( double alpha, double gamma);

    void newState(Agent a, Memory memo);

    Action chooseAction(double epsilon);

    void updateHistory(State state, Action action, int reward);

}
