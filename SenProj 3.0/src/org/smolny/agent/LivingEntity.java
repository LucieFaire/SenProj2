package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.*;

/**
 * Created by dsh on 3/6/16.
 */
public class LivingEntity extends Agent {

    public int MAX = 1000000000;

    //private String age;
    Random rand = new Random();
    //private List<Material> inventory;


    public LivingEntity() {
        super();
        this.lifeLevel = 100;
    }


    /**
     * inner logic of the agent behavior
     */
    public void randomMove(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2;
        ArrayList<Integer> options = new ArrayList<Integer>();
        if (environment[center][center - 1] != null) {
            int goUp = 1;
            options.add(goUp);
        }
        if (environment[center][center + 1] != null) {
            int goDown = 2;
            options.add(goDown);
        }
        if (environment[center - 1][center] != null) {
            int goLeft = 3;
            options.add(goLeft);
        }
        if (environment[center + 1][center] != null) {
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


    public void calcHeuristic(CellProjection[][] env, int x, int y) {
        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[i].length; j++) {
                if (env[i][j] != null) { // if there will be blocks in future
                    env[i][j].setHCost(Math.abs(x - i) + Math.abs(y - j));
                } else {
                    env[i][j].setHCost(MAX);
                }
            }
        }

    }

    public static PriorityQueue<CellProjection> open = new PriorityQueue<CellProjection>(2);

    /**
     * find the shortest path to the prey using A* search
     */
    public void pathFindTo(CellProjection predator, CellProjection prey, CellProjection[][] env) {
        calcHeuristic(env, prey.getX(), prey.getY());
        boolean closed[][] = new boolean[env.length][env[0].length];
        open.add(prey);

        CellProjection current;

        while (true) {
            current = open.poll();
            if (current == null) {
                break;
            }
            closed[current.getX()][current.getY()] = true;

            if (current.equals(prey)) {
                return;
            }

            CellProjection t;
            if (current.getX() - 1 >= 0) {
                t = env[current.getX() - 1][current.getY()];
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, closed);

                if (current.getY() - 1 >= 0) {
                    t = env[current.getX() - 1][current.getY() - 1];
                    checkUpdateCost(current, t, current.getCost() + CellProjection.DIAGONAL_COST, closed);
                }

                if (current.getY() + 1 < env[0].length) {
                    t = env[current.getX() - 1][current.getY() + 1];
                    checkUpdateCost(current, t, current.getCost() + CellProjection.DIAGONAL_COST, closed);
                }
            }

            if (current.getY() - 1 >= 0) {
                t = env[current.getX()][current.getY() - 1];
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, closed);
            }

            if (current.getY() + 1 < env[0].length) {
                t = env[current.getX()][current.getY() + 1];
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, closed);
            }

            if (current.getX() + 1 < env.length) {
                t = env[current.getX() + 1][current.getY()];
                checkUpdateCost(current, t, current.getCost() + CellProjection.V_H_COST, closed);

                if (current.getY() - 1 >= 0) {
                    t = env[current.getX() + 1][current.getY() - 1];
                    checkUpdateCost(current, t, current.getCost() + CellProjection.DIAGONAL_COST, closed);
                }

                if (current.getY() + 1 < env[0].length) {
                    t = env[current.getX() + 1][current.getY() + 1];
                    checkUpdateCost(current, t, current.getCost() + CellProjection.DIAGONAL_COST, closed);
                }
            }
        }

        //backtrack the path
        ArrayList<Path> path = new ArrayList<>();
        if (closed[prey.getX()][prey.getY()]) {
            current = env[prey.getX()][prey.getY()];
            path.add(new Path(prey.getX(), prey.getY()));
            while (current.getParent() != null) {
                path.add(new Path(current.getParent().getX(), current.getParent().getY()));
                current = current.getParent();
            }
            Collections.reverse(path); // reverse the order: from start to finish
            Path c = path.get(0);
            for (Path p : path) {
                if (p.x > c.x) {
                    handle.goRight();
                } else
                if (p.x < c.x) {
                    handle.goLeft();
                } else
                if (p.y > c.y) {
                    handle.goDown();
                } else
                if (p.y < c.y) {
                    handle.goUp();
                }
                c = p;
            }
        }
    }

    private void checkUpdateCost(CellProjection current, CellProjection t, int cost, boolean[][] closed) {
        if (t == null || closed[t.getX()][t.getY()]) {
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


//-----inner class-----------------------------------------------------------------------------------
class Path {
    public int x;
    public int y;

    public Path(int x, int y) {
        this.x = x;
        this.y = y;
    }
}



