package org.smolny.world;

import org.smolny.agent.Agent;
import org.smolny.agent.HomoErectus;

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
                CellProjection[][] env = getEnvironment(agent);
                agent.tick(env);
                agentsToTick.remove(agent);
            }

            currentTick++;
            notifyTick();
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


    private List<Runnable> tickListeners = new ArrayList<>();
    public void addTickListener(Runnable r) {
        tickListeners.add(r);
    }

    public void notifyTick() {
        tickListeners.forEach(Runnable::run);
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

        public WorldHandleImpl(Agent agent) {
            this.agent = agent;
        }

        @Override
        public void goUp() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setAgentLocation(agent, location.getX(), location.getY() - 1);
            });
        }

        @Override
        public void goDown() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setAgentLocation(agent, location.getX(), location.getY() + 1);
            });
        }

        @Override
        public void goLeft() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setAgentLocation(agent, location.getX() - 1, location.getY());
            });
        }

        @Override
        public void goRight() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setAgentLocation(agent, location.getX() + 1, location.getY());
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


    public CellProjection[][] getEnvironment(Agent agent) {
        int sight = agent.getSight();
        int q = sight * 2 + 1; // the size of each axis of the new grid
        CellProjection[][] env;
        env = init(q);
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


     private CellProjection[][] setEnv(CellProjection[][] env, Agent agent) {
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
                    env[k][l].createCopy(grid[x+i][y+j]);
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
        Agent agent = new HomoErectus("Lucy");

        agent.setHandle(new WorldHandleImpl(agent));

        setAgentLocation(agent, grid.length / 2, grid.length / 2);
        return agent;
    }

    public CellProjection[][] init(int q) {
        CellProjection[][] env = new CellProjection[q][q];
        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[i].length; j++) {
                CellProjection cp = new CellProjection();
                env[i][j] = cp;
            }
        }
        return env;
    }


    //------------------------------------------------------------------------------------------------------------------
    //--probably-useful-stuff-------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    public Cell[][] getGrid() {
        return grid;
    }

    public Map<Agent, Cell> getAgentLocations() {
        return agentLocations;
    }


}
