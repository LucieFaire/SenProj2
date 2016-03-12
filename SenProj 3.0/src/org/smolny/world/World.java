package org.smolny.world;

import org.smolny.agent.Agent;
import org.smolny.agent.HomoErectus;
import org.smolny.agent.LivingEntity;
import org.smolny.agent.Material;

import java.util.*;

/**
 * Created by dsh on 03.03.2016.
 */
public class World {

    private Cell[][] grid;
    private Random rand = new Random();

    private Map<Agent, Cell> agentLocations = new HashMap<>();

    public World(int l, int w) {
         this.grid = new Cell[l][w];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Cell cell = new Cell(i,j);
                grid[i][j] = cell;
            }
        }
        initialize();
    }

    //------------------------------------------------------------------------------------------------------------------
    //--main-cycle------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    private long tickDelay = 100;
    private long currentTick = 0;

    private volatile boolean isRunning = true;

    public void start() {
        while (isRunning) {
            printState();
            Set<Agent> agentsToTick = new HashSet<>(agentLocations.keySet());
            while( !agentsToTick.isEmpty() ) {
                Agent agent = chooseAgentToTick(agentsToTick);
                agent.preTick();
                Cell[][] env = getEnvironment(agent);
                agent.tick(env, new WorldHandleImpl(agent));
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

    private Agent chooseAgentToTick(Set<Agent> agents) {
        //in future we will add an initiative parameter, but right now do just stupid random
        int toReturn = rand.nextInt(agents.size());
        int counter = 0;
        Iterator<Agent> it = agents.iterator();
        Agent result = null;
        while (it.hasNext() && counter <= toReturn) {
            result = it.next();
            counter++;
        }
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------
    //---inner class helper---------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    public void printState() {
        String res = "[";
        for (Agent a : agentLocations.keySet()) {
            Cell c = agentLocations.get(a);
            res += a.getName();
            res += ": (" + c.getX() + "," + c.getY() + ")";
        }
        res += "]";
        System.out.println(res);
    }

    public class WorldHandleImpl implements WorldHandle {

        private Agent agent;
        private Cell cell;
        private int x;
        private int y;

        public WorldHandleImpl(Agent agent) {
            this.agent = agent;
            this.cell = agentLocations.get(agent);
            this.x = cell.getX();
            this.y = cell.getY();
        }

        @Override
        public void goUp() {
            safeExec( () -> {
                setAgentLocation(agent, x, y--);
            });
        }

        @Override
        public void goDown() {
            safeExec( () -> {
                setAgentLocation(agent, x, y++);
            });
        }

        @Override
        public void goLeft() {
            safeExec( () -> {
                setAgentLocation(agent, x++, y);
            });
        }

        @Override
        public void goRight() {
            safeExec( () -> {
                setAgentLocation(agent, x++, y);
            });
        }


        private void safeExec(Runnable action) {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    //---------- helper methods ----------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------


    public Cell[][] getEnvironment(Agent agent) {
        int sight = agent.getSight();
        int q = sight * 2 + 1; // the size of each axis of the new grid
        Cell[][] env = new Cell[q][q];
        switch (sight) {
            case 1:
                env = setEnv(env, agent);
                break;
            case 2:
                env = setEnv(env, agent);
                break;
            case 3:
                env = setEnv(env, agent);
                break;
            case 4:
                env = setEnv(env, agent);
                break;
        }

        return env;
    }


    private void setAgentLocation(Agent agent, Cell cell) {
        Cell prevCell = agentLocations.get(agent);

        if (prevCell == cell)
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


     private Cell[][] setEnv(Cell[][] env, Agent agent) {
         //Cell[][] env = new Cell[q][q];
         int sight = agent.getSight();
         Cell location = agentLocations.get(agent);
         int x = location.getX();
         int y = location.getY();
         int k = 0;
         for (int i = (-sight); i <= sight; i++) {
            int l = 0;
            for (int j = (-sight); j <= sight; j++) {
                if (((x + i) >= 0 && (x + i) < grid.length) && ((y + j) >= 0 && (y + j) < grid[x].length)) {
                    env[k][l] = grid[x+i][y+j];
                } else {
                    env[k][l] = null;
                }
                l++;
            }
            k++;
         }

        return env;
    }


    //--initialize------------------------------------------------------------------------------------------------------

    private void initialize() {
        createAgents();
    }

    private Agent createAgents() {
        Agent agent = new HomoErectus();
        setAgentLocation(agent, grid.length/2, grid.length/2);
        return agent;
    }


    //------------------------------------------------------------------------------------------------------------------
    //--probably-useful-stuff-------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------


}
