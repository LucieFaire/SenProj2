package org.smolny.agent;

import org.smolny.agent.memory.Memory;
import org.smolny.utils.IntPoint;
import org.smolny.world.CellProjection;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dsh on 3/6/16.
 */
public class LivingEntity extends Agent {


    protected Memory memory = new Memory();


    //private String age;
    protected Random rand = new Random();
    //private List<Material> inventory;


    public LivingEntity() {
        super();
        this.lifeLevel = 100;

    }


    @Override
    public void tick(CellProjection[][] environment) {
        memory.update(environment);
    }

    /**
     * inner logic of the agent behavior
     */
    public void randomStep(Memory memory, IntPoint lp ) {
        List<Integer> options = new ArrayList<>();
        if (memory.get(lp.getX(), lp.getY() - 1) != null) {
            int goUp = 1;
            options.add(goUp);
        }
        if (memory.get(lp.getX(), lp.getY() + 1) != null) {
            int goDown = 2;
            options.add(goDown);
        }
        if (memory.get(lp.getX() - 1, lp.getY()) != null) {
            int goLeft = 3;
            options.add(goLeft);
        }
        if (memory.get(lp.getX() + 1, lp.getY()) != null) {
            int goRight = 4;
            options.add(goRight);
        }

        int choice = rand.nextInt(options.size());
        choice = options.get(choice);
        switch (choice) {
            case 1:
                handle.goUp();
                break;
            case 2:
                handle.goDown();
                break;
            case 3:
                handle.goLeft();
                break;
            case 4:
                handle.goRight();
                break;
        }
    }

    //--path-finding-logic----------------------------------------------------------------------------------------------

    /**
     * find the shortest path to the prey using A* search
     */
    public void pathFindTo(IntPoint start, IntPoint goal, Memory memo, int steps) {
        PriorityQueue<CellProjection> open = new PriorityQueue<>((Object o1, Object o2) -> {
            CellProjection c1 = (CellProjection)o1;
            CellProjection c2 = (CellProjection)o2;

            return c1.getCost()<c2.getCost()?-1:
                    c1.getCost()>c2.getCost()?1:0;
        });

        if (memo.get(goal) == null) {
            // option for null cells
            return;
        }
        calcHeuristic(memo, goal.getX(), goal.getY());
        Set<IntPoint> visited = new HashSet<>();
        open.add(memo.get(start));


        CellProjection current;

        while (true) {
            current = open.poll();

            if (current == null) {
                break;
            }
            IntPoint p = current.getLocalPoint();
            visited.add(current.getLocalPoint());

            if (current.getLocalPoint().equals(goal)) {
                break;
            }

            CellProjection t;

            if (memo.get(p.getX(), p.getY() - 1) != null) {

                t = memo.get(p.getX(), p.getY() - 1);
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, visited, open);
            }

            if (memo.get(p.getX() - 1, p.getY()) != null) {

                t = memo.get(p.getX() - 1, p.getY());
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, visited, open);
            }

            if (memo.get(p.getX(), p.getY() + 1) != null) {

                t = memo.get(p.getX(), p.getY() + 1);
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, visited, open);
            }

            if (memo.get(p.getX() + 1, p.getY()) != null) {

                t = memo.get(p.getX() + 1, p.getY());
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, visited, open);
            }

        }

        //backtrack the path
        List<IntPoint> path = new ArrayList<>();
        if (visited.contains(goal)) {
            current = memo.get(goal);
            path.add(goal);
            while (current.getParent() != null) {
                IntPoint p = current.getParent().getLocalPoint();
                path.add(IntPoint.create(p.getX(), p.getY()));
                current = current.getParent();
            }
            Collections.reverse(path); // reverse the order: from start to finish
            if (path.size() == 1) {
                return;
            } else {
                if (path.size() < steps) {
                    steps = path.size() - 1;
                }
                int s = 0;
                while (s < steps) {
                    IntPoint c = path.get(s);
                    IntPoint f = path.get(s+1);
                    if (f.getX() > c.getX()) {
                        handle.goRight();
                    } else if (f.getX() < c.getX()) {
                        handle.goLeft();
                    } else if (f.getY() > c.getY()) {
                        handle.goDown();
                    } else if (f.getY() < c.getY()) {
                        handle.goUp();
                    }
                    s++;
                }
            }
        }
    }

    private void calcHeuristic(Memory memory, int x, int y) {
        for (IntPoint lp : memory.getKSet()) {
            CellProjection cp = memory.get(lp);
            if (cp != null) { // if there will be blocks in future
                cp.setHCost(Math.abs(x - lp.getX()) + Math.abs(y - lp.getX()));
            } else {
                cp.setHCost(MAX);
            }
        }
    }


    private void checkUpdateCost(CellProjection current, CellProjection t, int cost, Set<IntPoint> visited, PriorityQueue<CellProjection> open) {
        if (t == null || visited.contains(t.getLocalPoint())) {
            return;
        }

        int finalCost = t.getHCost() + cost;
        boolean inOpen = open.contains(t);

        if (!inOpen || finalCost < t.getCost()) {
            t.setCost(finalCost);
            t.setParent(current);
            if (!inOpen) {
                open.add(t);
            }
        }
    }


}




