package org.smolny.world;

import org.smolny.AI.IntelligentAgent;
import org.smolny.AI.RFLModule;
import org.smolny.agent.*;
import org.smolny.agent.PreyPredator.Grass;
import org.smolny.agent.PreyPredator.Rabbit;
import org.smolny.agent.PreyPredator.Wolf;
import org.smolny.utils.IntPoint;

import java.util.*;
import java.util.List;

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
//                if (rand.nextDouble() < 0.05) {
//                    Cell cell = null;
//                    grid[i][j] = cell;
//                } else {
                    Cell cell = new Cell(i, j);
                    grid[i][j] = cell;
//                }
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

    public void run() {
        while (isRunning) {
            System.out.println("Tick №: " + currentTick);
            //printState();
            Set<Agent> agentsToTick = new HashSet<>(agentLocations.keySet());
            while( !agentsToTick.isEmpty() ) {
                Agent agent = chooseAgentToTick1(agentsToTick);
                if (agentLocations.containsKey(agent)) {
                    agent.preTick();
                    if (agentLocations.containsKey(agent)) {
                        CellProjection[][] env = getEnvironment(agent);
                        agent.tick(env);
                    }
                }
                agentsToTick.remove(agent);
            }
            for (RFLModule m : rflModules.values()) {
                m.updateReward();
            }
            currentTick++;
            notifyTick();
            //System.out.println("notify tick is done");
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
        //in future we will add an initiative parameter, but right now do just random
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



    private Agent chooseAgentToTick1(Set<Agent> agents) {
        double sum = 0;

        List<Double> ps = new ArrayList<>();
        for (Agent a : agents) {
            if (!(a.getClass().getSuperclass().equals(Material.class))) {
                sum += a.getInitiative();
            }
        }
        double count = 0;
        Map<Double, Agent> initiative = new HashMap<>();
        List<Agent> material = new ArrayList<>();
        for (Agent a : agents) {
            if (!(a.getClass().getSuperclass().equals(Material.class))) {
                double init = a.getInitiative() / sum;
                count += init;
                initiative.put(count, a);
                ps.add(count);
            } else {
                material.add(a);
            }
        }
        if (material.size() > 0) {
            return material.get(0);
        }

        double r = rand.nextDouble();
        for (double p : ps) {
            if (r <= p) {
                return initiative.get(p);
            }

        }
        throw new IllegalStateException();

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
            res += a.getClass();
            res += ": (" + c.getPoint().getX() + "," + c.getPoint().getY() + ")";
        }
        res += "]";
        System.out.println(res);
    }

    //--history-for-view------------------------------------------------------------------------------------------------

    private boolean keepTurnHistory;


    public class WorldHandleImpl implements WorldHandle {

        private Agent agent;

        public WorldHandleImpl(Agent agent) {
            this.agent = agent;
        }

        @Override
        public void goUp() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setGlobalAgentLocation(agent, location.getPoint().getX(), location.getPoint().getY() - 1);
                IntPoint p = agent.getLocalPosition();
                agent.setLocalPosition(IntPoint.create(p.getX(), p.getY() - 1));
            });
        }

        @Override
        public void goDown() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setGlobalAgentLocation(agent, location.getPoint().getX(), location.getPoint().getY() + 1);
                IntPoint p = agent.getLocalPosition();
                agent.setLocalPosition(IntPoint.create(p.getX(), p.getY() + 1));
            });
        }

        @Override
        public void goLeft() {
            safeExec( () -> {
                Cell location = agentLocations.get(agent);
                setGlobalAgentLocation(agent, location.getPoint().getX() - 1, location.getPoint().getY());
                IntPoint p = agent.getLocalPosition();
                agent.setLocalPosition(IntPoint.create(p.getX() - 1, p.getY()));
            });
        }

        @Override
        public void goRight() {
            safeExec(() -> {
                Cell location = agentLocations.get(agent);
                setGlobalAgentLocation(agent, location.getPoint().getX() + 1, location.getPoint().getY());
                IntPoint p = agent.getLocalPosition();
                agent.setLocalPosition(IntPoint.create(p.getX() + 1, p.getY()));
            });
        }

        @Override
        public void eat(UUID id) {
            if (id != null) {
                Agent a = getAgentById(id);
                agent.setLifeLevel(30);
                dead(a);
            }
        }

        @Override
        public void addAgent(Agent a, int x, int y) {
            setGlobalAgentLocation(a, x, y);
            a.onCreate();
        }

        @Override
        public void die() {
            dead(agent);
        }

        @Override
        public <T extends Agent> void createAgent(Class<T> c, Agent a, int number) {
            try {
                for (int t = 0; t < number; t++) {
                    IntPoint p = agentLocations.get(a).getPoint();
                    Agent agent = c.newInstance();
                    agent.setHandle(new WorldHandleImpl(agent));
                    addAgent(agent, p.getX(), p.getY());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public IntelligentAgent createIntelligence(UUID cid) {
            IntelligentAgent a = new IntelligentAgent();
            UUID id = a.getId();
            a.setHandle(new WorldHandleImpl(a));
            setGlobalAgentLocation(a, rand.nextInt(getGrid().length), rand.nextInt(getGrid().length));
            a.setRFLModule(rflModules.get(cid));
            rflModules.replace(a.getId(), rflModules.get(cid));
            return a;
        }

        @Override
        public void createMaterial(Agent a, IntPoint lp) {
            IntPoint p = agentLocations.get(a).getPoint().plus(lp);
            Agent agent = new Grass();
            agent.setHandle(new WorldHandleImpl(agent));
            setGlobalAgentLocation(agent, p.getX(), p.getY());
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
        IntPoint localPosition = agent.getLocalPosition();
        int sight = agent.getSight();
        Cell location = agentLocations.get(agent);
        int x = location.getPoint().getX();
        int y = location.getPoint().getY();
        int q = sight * 2 + 1; // the size of each axis of the new grid
        CellProjection[][] env = new CellProjection[q][q];
        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[i].length; j++) {
                CellProjection cp = new CellProjection();
                env[i][j] = cp;
                if (((x - sight + i) >= 0 && (x - sight + i) < grid.length)
                        && ((y - sight + j) >= 0 && (y - sight + j) < grid[x - sight + i].length)) {
                    cp.createCopy(grid[x-sight+i][y-sight+j]);
                    IntPoint lp = IntPoint.create(localPosition.getX() - sight + i, localPosition.getY() - sight + j);
                    cp.setLocalPoint(lp);
                } else {
                    env[i][j] = null;
                }
            }
        }
        return env;
    }


    private void setGlobalAgentLocation(Agent agent, Cell cell) {
        Cell prevCell = agentLocations.get(agent);

        if (prevCell == cell)
            return;

        if ( prevCell != null ) {
            prevCell.getAgents().remove(agent);
        }

        cell.getAgents().add(agent);
        agentLocations.put(agent, cell);
    }

    private void setGlobalAgentLocation(Agent agent, int x, int y) {
        Cell cell = grid[x][y];
        setGlobalAgentLocation(agent, cell);
    }


    private void dead(Agent a) {
        Cell cell = agentLocations.get(a);
        Class c = a.getClass();
        cell.getAgents().remove(a);
        agentLocations.remove(a);
        a.onDie();

    }


    //--initialize------------------------------------------------------------------------------------------------------

    private void initialize() {
        createAgents();
    }

    private void createAgents() {

        int count = 0;
        while (count < 25) {
            Agent w = new Wolf();
            w.setHandle(new WorldHandleImpl(w));
            setGlobalAgentLocation(w, rand.nextInt(getGrid().length), rand.nextInt(getGrid().length));
            count++;
        }

        for (int i = 0; i < 60; i++) {
            Agent r = new Rabbit();
            r.setHandle(new WorldHandleImpl(r));
            setGlobalAgentLocation(r, rand.nextInt(getGrid().length), rand.nextInt(getGrid().length));
        }


        for (int i = 0; i < 30; i++) {
            Agent v = new Grass();
            v.setHandle(new WorldHandleImpl(v));
            setGlobalAgentLocation(v, rand.nextInt(getGrid().length), rand.nextInt(getGrid().length));
        }

        //intelligent agent
        IntelligentAgent smart = new IntelligentAgent();
        WorldHandle handle = new WorldHandleImpl(smart);
        smart.setHandle(handle);
        RFLModule rflm = new RFLModule(smart, handle);
        smart.setRFLModule(rflm);
        rflModules.put(smart.getId(), rflm);
        setGlobalAgentLocation(smart, rand.nextInt(getGrid().length), rand.nextInt(getGrid().length));
    }

    //--Reinforcement-Learning-----------------------------------------------------------------------------------

    private Map<UUID, RFLModule> rflModules = new HashMap<>();

    //------------------------------------------------------------------------------------------------------------------
    //--useful-stuff-------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    public Cell[][] getGrid() {
        return grid;
    }

    public Map<Agent, Cell> getAgentLocations() {
        return agentLocations;
    }

    private Agent getAgentById(UUID id) {
        for (Agent a : agentLocations.keySet()) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return  null;
    }

}
