package org.smolny.agent_world;

import java.smolny.agent.Agent;
import java.smolny.agent.LivingEntity;
import java.util.Set;

/**
 * Created by I326876 on 03.03.2016.
 */
public class World {

    public static int length;
    public static int width;
    private Cell[][] grid;

    public Set<Agent> agents;

    public World(int l, int w) {
         this.grid = new Cell[length][width];
         initialize();

    }

//    public void getGrid() {
//        for (int i = 0; i < length; i++) {
//            for (int j = 0; j < width; j++) {
//
//            }
//        }
//    }

//    public void setGrid() {
//
//    }

    public Cell[][] getEnvironment(Agent agent) {
        int sight = ((LivingEntity) agent).getSight();
        int q = sight * 2 + 1; // the size of each axis of the new grid
        Cell[][] env = new Cell[q][q];
        switch (sight) {
            case 1:
                env = env1(q);
                break;
            case 2:
                env = env2(q);
                break;
            case 3:
                env = env3(q);
                break;
        }

        return env;
    }


    //    private Agent createAgents() {
//        Agent agent = new Agent(); //????? method from Agent class
//        return agent;
//    }

    private void initialize() {

    }

    private World updateWorld() {
        return null;
    }


    //-----------------------------------------------
    //-----------------------------------------------
    //---------- helper methods ---------------------
    //-----------------------------------------------
    //-----------------------------------------------

    // environment for the sight = 1, grid 3x3
    private Cell[][] env1(int q) {
        Cell[][] env = new Cell[q][q];
        //fill in

        return env;
    }

    private Cell[][] env2(int q) {
        Cell[][] env = new Cell[q][q];
        //fill in

        return env;
    }

    private Cell[][] env3(int q) {
        Cell[][] env = new Cell[q][q];
        //fill in

        return env;
    }
}
