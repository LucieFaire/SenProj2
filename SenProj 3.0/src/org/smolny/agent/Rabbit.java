package org.smolny.agent;

import org.smolny.world.CellProjection;

import java.util.ArrayList;

/**
 * Created by dsh on 3/19/16.
 */
public class Rabbit extends LivingEntity {

    public Rabbit() {
        super();
        this.name = "Rabbit";
    }

    @Override
    public void tick(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2;
        CellProjection prey = environment[center][center];
        int x = 0;
        int y = 0;
        if (this.getLifeLevel() < 1) {
            handle.die();
        } else {
            // don't know if they should first search for food or check there is no danger and then eat or combine together both
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    if (environment[i][j].getAgents().contains("Wolfy")) {
                        x = x + Math.abs(center - i);
                        y = y + Math.abs(center - j);
                    }
                    if (environment[i][j].getAgents().contains("Grass")) {
                        //TODO
                    }

                }
            }
            if (x == 0 && y == 0 && this.getLifeLevel() < 50) {
                // no danger
                searchForFood(environment);
            } else
            if (x != 0 && y != 0) {
                //danger
                pathFindTo(prey, environment[center - x][center - y], environment);
            } else {
                // no hunger and danger
                randomMove(environment);
            }
        }

    }

    private void searchForFood(CellProjection[][] env) {
        //TODO
    }


}
