package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    @Override
    public void preTick() {
        super.preTick();
        lifeLevel--;

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


    public void calcHeuristic(CellProjection[][] env, int[][] heuristics, int pos) {
        for (int i = 0; i < heuristics.length; i++) {
            for (int j = 0; j < heuristics[i].length; j++) {
                if (env[i][j] != null) { // if there will be blocks in future
                    heuristics[i][j] = Math.abs(pos - i) + Math.abs(pos - j);
                } else {
                    heuristics[i][j] = MAX;
                }
            }
        }

    }

    public int getHeuristic(int[][] h, int x, int y) {
        return h[x][y];
    }

}

