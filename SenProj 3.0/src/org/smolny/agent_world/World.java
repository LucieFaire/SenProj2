package org.smolny.agent_world;

import org.smolny.agent.Agent;
import org.smolny.agent.LivingEntity;
import org.smolny.agent.Material;
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
                env = env(q, agent);
                break;
            case 2:
                env = env(q, agent);
                break;
            case 3:
                env = env(q, agent);
                break;
        }

        return env;
    }

    public Material drop() {
        // randomly drop items
        return null;
    }

    private void initialize() {

        createAgents();

    }

    //-----------------------------------------------
    //-----------------------------------------------
    //---------- helper methods ---------------------
    //-----------------------------------------------
    //-----------------------------------------------

     private Cell[][] env(int q, Agent agent) {
        Cell[][] env = new Cell[q][q];
        int sight = ((LivingEntity) agent).getSight();
        int x = agent.getLocation().getX();
        int y = agent.getLocation().getY();
        int k = 0;
        int l = 0;
        for (int i = sight; i <= -sight; i--) {
            for (int j = sight; j <= -sight; j--) {
                if ((x + i >= 0 && x + i < length) && (y + j >= 0 && y + j < width)) {
                    env[k][l] = grid[x+i][y+j];
                } else {
                    env[k][l] = null;
                }
                k++;
                l++;
            }
        }

        return env;
    }


    private Agent createAgents() {
        Agent agent = new Agent(); //
        return agent;
    }
}
