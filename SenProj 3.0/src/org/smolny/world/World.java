package org.smolny.world;

import org.smolny.agent.Agent;
import org.smolny.agent.LivingEntity;
import org.smolny.agent.Material;

import java.util.*;

/**
 * Created by I326876 on 03.03.2016.
 */
public class World {

    private Cell[][] grid;

    private Map<Agent, Cell> agentLocations = new HashMap<>();

    public World(int l, int w) {
         this.grid = new Cell[l][w];
         initialize();
    }

    //------------------------------------------------------------------------------------------------------------------
    //--main-cycle------------------------------------------------------------------------------------------------------

    private long tickDelay = 100;
    private long currentTick = 0;

    private volatile boolean isRunning = true;

    public void start() {
        while ( isRunning ) {
            Set<Agent> agentsToTick = new HashSet<>(agentLocations.keySet());
            while( !agentsToTick.isEmpty() ) {
                Agent agent = chooseAgentToTick ( agentsToTick );
                agent.preTick();
                Cell[][] env = getEnvironment(agent);
                agent.tick(env);
                agentsToTick.remove(agent);
            }

            currentTick++;

            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    private Random rand = new Random();

    private Agent chooseAgentToTick(Set<Agent> agents) {
        //in future we will add an initiative parameter, but right now do just stupid random
        int toReturn = rand.nextInt(agents.size());
        int counter = 0;
        Iterator<Agent> it = agents.iterator();
        Agent result = null;
        while ( it.hasNext() && counter <= toReturn ) {
            result = it.next();
            counter ++;
        }
        return result;
    }



    //------------------------------------------------------------------------------------------------------------------
    //---------- helper methods ----------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------


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


    private void setAgentLocation(Agent agent, Cell cell) {
        Cell prevCell = agentLocations.get(agent);

        if ( prevCell == cell )
            return;

        if ( prevCell != null ) {
            prevCell.getAgents().remove(agent);
        }

        cell.getAgents().add(agent);
        agentLocations.put(agent, cell);
    }

    private void setAgentLocation(Agent agent, int x, int y) {
        Cell cell = grid[x][y];
        setAgentLocation(agent, cell);
    }


     private Cell[][] env(int q, Agent agent) {
         Cell[][] env = new Cell[q][q];
         int sight = ((LivingEntity) agent).getSight();
         Cell location = agentLocations.get(agent);
         int x = location.getX();
         int y = location.getY();
         int k = 0;
         int l = 0;
         for (int i = sight; i <= -sight; i--) {
            for (int j = sight; j <= -sight; j--) {
                if ((x + i >= 0 && x + i < grid.length) && (y + j >= 0 && y + j < grid[x].length)) {
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


    //--initialize------------------------------------------------------------------------------------------------------

    private void initialize() {
        createAgents();
    }

    private Agent createAgents() {
        Agent agent = new Agent();
        return agent;
    }


    //------------------------------------------------------------------------------------------------------------------
    //--probably-useful-stuff-------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------


    public Material drop() {
        // randomly drop items
        return null;
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

}
