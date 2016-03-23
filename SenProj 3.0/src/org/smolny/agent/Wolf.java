package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;

import java.util.*;

/**
 * Created by dsh on 3/19/16.
 */
public class Wolf extends LivingEntity {


    public Wolf() {
        super();
        this.name = "Wolfy";

    }


    @Override
    public void tick(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2; // agent position
        int count = MAX;
        CellProjection prey = new CellProjection();
        CellProjection predator = environment[center][center];
        if (this.getLifeLevel() < 1) {
            handle.die();
        } else
        if (this.getLifeLevel() < 50) {
            // search for food
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    if (environment[i][j].getAgents().containsKey("Rabbit")) {
                        int h = Math.abs(center - i) + Math.abs(center - j);
                      if (h < count) {
                          count = h;
                          prey = environment[i][j];
                      }
                    }
                }
            }
            if (prey != null) {
                // rabbit found
                pathFindTo(predator, prey, environment);
                handle.eat(prey.getAgents().get("Rabbit"));
            }
        } else {
            randomMove(environment);
        }
    }

}
