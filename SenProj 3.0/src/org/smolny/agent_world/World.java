package org.smolny.agent_world;

import java.util.Set;

/**
 * Created by I326876 on 03.03.2016.
 */
public class World {

    private int length;
    private int width;
    private Cell[][] grid;

    public Set<Agent> agents;

    public World(int length, int width) {
        this.grid = new Cell[length][width];
        initialize();

    }

    public Cell[][] getEnvironment(Agent agent) {
        //int sight = agent.getSight();
        //...
        return null;
    }

//    private Agent createAgents() {
//        Agent agent = new Agent(); //????? method from Agent class
//        return agent;
//    }

    private void initialize() {

    }





}
