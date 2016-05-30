package org.smolny.AI;

import org.smolny.agent.Agent;
import org.smolny.agent.LivingEntity;
import org.smolny.agent.PreyPredator.Grass;
import org.smolny.agent.PreyPredator.PreyPredator;
import org.smolny.agent.PreyPredator.Rabbit;
import org.smolny.agent.PreyPredator.Wolf;
import org.smolny.agent.memory.Memory;
import org.smolny.utils.IntPoint;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by dsh on 4/18/16.
 */
public final class RFLModule implements RFLearning {

    private List<AgentHistory> history = new ArrayList<>();
    private Agent agent;
    private long LLevel;
    private Action action;
    private boolean p;
    private boolean f;
    private boolean h;
    private boolean e;
    private State st;
    private Memory memo;
    private State nextSt;
    private int reward;
    private UUID id;
    private int localStepReward;
    private static final double alpha = 0.5;
    private static final double gamma = 0.5;
    private List<Integer> totalRew = new ArrayList<>();
    private HashMap<State, HashMap<Action, Double>> q = setQ();

    private WorldHandle handle;

    private boolean hasDied = false;

    public RFLModule(Agent agent, WorldHandle handle) {
        this.agent  = agent;
        this.handle = handle;
        this.id = agent.getId();
    }

    public void agentDied() {
        hasDied = true;
    }


    public HashMap<State, HashMap<Action, Double>> setQ() {

        HashMap<State, HashMap<Action, Double>> q = new HashMap<State, HashMap<Action, Double>>();
        for (int i = 0; i < WRState.numberOfStates(); i++) {
            HashMap<Action, Double> qa = new HashMap<Action, Double>();
            qa.put(new Action("eat"), 0.25);
            qa.put(new Action("partner"), 0.25);
            qa.put(new Action("runAway"), 0.25);
            q.put(new WRState(i), qa);
        }
        return q;

    }

    private int getReward() {
        return reward;
    }

    //call this method from the world at the end of a cycle
    public void updateReward() {
        int r;
        if (hasDied) {
            reset();
            r = -100;
        } else if (agent.getLifeLevel() > LLevel) {
            r = 12;
        } else if (action.equals(Action.PARTNER) && p) {
            r = 3;
        } else  if (action.equals(Action.RUNAWAY) && e){
            // runs away
            r = 5;
        } else {
            r = 0;
        }
        localStepReward = r;
        learning(alpha, gamma);
    }

    // create a new state of the agent
    public State newState(Agent a, Memory memory) {
        memo = memory;
        LLevel = agent.getLifeLevel();
        boolean eat = false;
        boolean enemy = false;
        boolean partner = false;
        boolean hunger = false;
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                CellProjection c = memory.get(agent.getLocalPosition().getX() + i, agent.getLocalPosition().getY() + j);
                if (c != null) {
                    if (c.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class) ) {
                        eat = true;
                        f = eat;
                    }
                    if (c.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Wolf.class)) {
                        enemy = true;
                        e = enemy;
                    }
                    if (c.IsRelevantAgent(Rabbit.class, ((PreyPredator)a).getSex())) {
                        partner = true;
                        p = partner;
                    }
                    if (a.getLifeLevel() < 50) {
                        hunger = true;
                        h = hunger;
                    }
                }
            }
        }
        State state = new WRState(eat, enemy, partner, hunger);
        st = state;
        return state;

    }

    // according to the state of agent choose the action
    public Action chooseAction(double epsilon) {
        Action a = epsilonGreedy(q.get(st), epsilon);
        action = a;
        return a;

    }

    public void learning( double alpha, double gamma) {
        nextSt = newState(agent, memo);
        HashMap<Action, Double> qa = q.get(st);
        Double qav = qa.get(action) + alpha *(localStepReward + gamma * (getMaxQAV(q.get(nextSt))) - qa.get(action));
        qa.put(action, qav); // update value for the taken value in the hashmap qa
        q.put(st, qa); // update the value = hashmap qa for the current state s in q
        reward += localStepReward;
        updateHistory(st, action, localStepReward);

        //st = nextSt;
        System.out.printf("Q-function: %s-%s %f\n", st.toString(), action.getAction(), qav);
    }

    private void reset() {
        totalRew.add(reward);
        System.out.println("His total reward is " + reward);
        this.reward = 0;
        this.hasDied = false;
        IntelligentAgent ag = handle.createIntelligence(id);
        this.agent = ag;

    }

    public void updateHistory(State state, Action action, int reward) {
        AgentHistory event = new AgentHistory();
        event.setState(state);
        event.setAction(action);
        event.setReward(reward);
        history.add(event);
    }


    //----------------------------------------------------------------------------------------------------------
    //---------------------- inner logic------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------

    private Action epsilonGreedy(HashMap<Action, Double> qa, double epsilon) {

        Random rand = new Random();
        Double maxVal = Double.NEGATIVE_INFINITY;
        int maxCount = 0; // # of max values duplicates
        int totalCount = 0; // total # of actions in qa

        LinkedList<Action> actions = new LinkedList<Action>(); // list of all the names of actions
        LinkedList<Double> probs = new LinkedList<Double>(); // list of all the probabilities of the actions

        //loop over each action name from the key set of hashmap qa
        for (Action action : qa.keySet()) {
            totalCount++; // increase the # of actions in the set
            Double cVal = qa.get(action); // return the value for the current action

            // System.out.printf("%f vs. %f\n", currValue, maxValue);

            int cmp = cVal.compareTo(maxVal); // compare current value to max value

            if (cmp > 0) { // if current value is bigger than max
                maxVal = cVal; // replace max value with current one
                maxCount = 1; // set max value as unique
            } else if (cmp == 0) { // values are equal
                maxCount++; // count duplicates of max value
            }

        }

        Double simpleProb = epsilon/totalCount; // probability over all the actions
        Double greedyProb = (1.0 - epsilon)/ maxCount + simpleProb; // e-greedy probability
        Double oldProb = 0.0;

        //repeat for each action in the key set of hashmap qa
        for (Action action : qa.keySet()) {
            Double cVal = qa.get(action); // return the value of the chosen action

            if (cVal.compareTo(maxVal) == 0) { // if values are equal
                oldProb += greedyProb; // increase the total prob by the greedy prob
            } else {
                oldProb += simpleProb; // increase the total prob by the simple prob
            }

            // add the results to the proper lists: action - probability
            // after looping creates the total action-prob scheme
            probs.add(oldProb);
            actions.add(action);
        }

        double r = rand.nextDouble();

        // loop over each action
        for (int i = 0; i < totalCount; i++) {

            //conditional to check if the random value smaller or not than the estimated prob for each action
            // if yes - return name of that action
            if (r < probs.get(i)){
                return actions.get(i);
            }
        }
        //otherwise if no action was chosen, return name of some action
        return actions.get(totalCount - 1);
    }

    private Double getMaxQAV(HashMap<Action, Double> qa) {

        Double maxVal = Double.NEGATIVE_INFINITY;

        for (Action act : qa.keySet()) {
            Double cVal = qa.get(act); // get the value of the current action
            int cmp = cVal.compareTo(maxVal); // compare two values

            if (cmp > 0) { // current value of action if bigger than the max value
                maxVal = cVal; // update the max value

            }
        }
        return maxVal; // return the best action value
    }




}
