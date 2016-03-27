package org.smolny.agent;

import org.smolny.agent.memory.Memory;
import org.smolny.utils.Point;
import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.*;

/**
 * Created by dsh on 3/6/16.
 */
public class LivingEntity extends Agent {

    protected int MAX = 1000000000;
    protected Memory memory = new Memory();

    //private String age;
    Random rand = new Random();
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
    public void randomMove(Memory memory, Point lp) {
        ArrayList<Integer> options = new ArrayList<>();
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
        if (choice == 1) {
            handle.goUp();
        } else if (choice == 2) {
            handle.goDown();
        } else if (choice == 3) {
            handle.goLeft();
        } else if (choice == 4) {
            handle.goRight();
        }
    }


    public void calcHeuristic(Memory memory, int x, int y) {
        for (Point lp : memory.getKSet()) {
            CellProjection cp = memory.get(lp);
            if (cp != null) { // if there will be blocks in future
               cp.setHCost(Math.abs(x - lp.getX()) + Math.abs(y - lp.getX()));
            } else {
               cp.setHCost(MAX);
            }
        }
    }



    /**
     * find the shortest path to the prey using A* search
     */
    public void pathFindTo(Point start, Point goal, Memory memo) {
        PriorityQueue<CellProjection> open = new PriorityQueue<>(2);
        calcHeuristic(memo, goal.getX(), goal.getY());
        Set<Point> visited = new HashSet<>();
        int size = memo.getSize();
        open.add(memo.get(start));

        CellProjection current;

        while (true) {
            current = open.poll();
            Point p = current.getLocalPoint();
            if (current == null) {
                break;
            }

            visited.add(current.getLocalPoint());

            if (current.getLocalPoint().equals(goal)) {
                return;
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
        ArrayList<Point> path = new ArrayList<>();
        if (visited.contains(memo.get(goal))) {
            current = memo.get(goal);
            path.add(goal);
            while (current.getParent() != null) {
                Point p = current.getParent().getLocalPoint();
                path.add(Point.create(p.getX(), p.getY()));
                current = current.getParent();
            }
            Collections.reverse(path); // reverse the order: from start to finish
            Point c = path.get(0);
            if (goal.getX() > c.getX()) {
                handle.goRight();
            } else if (goal.getX() < c.getX()) {
                handle.goLeft();
            } else if (goal.getY() > c.getY()) {
                handle.goDown();
            } else if (goal.getY() < c.getY()) {
                handle.goUp();
            }
        }
    }

    private void checkUpdateCost(CellProjection current, CellProjection t, int cost, Set<Point> visited, PriorityQueue<CellProjection> open) {
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




