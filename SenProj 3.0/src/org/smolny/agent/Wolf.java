package org.smolny.agent;

import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsh on 3/19/16.
 */
public class Wolf extends LivingEntity {


    private int MAX = 1000000;
    public Wolf() {
        super();
        this.name = "Wolfy";

    }


    @Override
    public void tick(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2; // agent position
        int[][] heuristics = new int[size][size];
        calcHeuristic(environment[center][center], heuristics, center);
        int count = MAX;
        CellProjection prey = new CellProjection();
        if (lifeLevel < 1) {
            handle.die();
        } else
        if (lifeLevel < 75) {
            // search for food
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    if (environment[i][j].getAgents().contains("Rabbit")) {
                        int h = getHeuristic(heuristics, i, j);
                      if (h < count) {
                          count = h;
                          prey = environment[i][j];
                      }
                    }
                }
            }
            if (prey != null) {
                // rabbit found
                pathFindTo(prey, environment, heuristics);
                handle.eat();
            }
        } else {
            randomMove(environment);
        }
    }

    /**
     * find the shortest path to the prey using A* search
     */
    private void pathFindTo(CellProjection prey, CellProjection[][] env, int[][] h) {
        //TODO
    }



}


