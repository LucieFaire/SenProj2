package org.smolny.agent;

import org.smolny.world.CellProjection;

/**
 * Created by dsh on 3/19/16.
 */
public class Rabbit extends  LivingEntity {

    public Rabbit() {
        super();
        this.name = "Rabbit";
    }

    @Override
    public void tick(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2;

        if (lifeLevel < 1) {
            handle.die();
        } else {
            // don't know if they should first search for food or check there is no danger and then eat or combine together both
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    if (environment[i][j].getAgents().contains("Wolfy")) {
                        runAway(i, j);
                    } else {
                        if (lifeLevel < 75) {
                            searchForFood(environment);
                        } else {
                            randomMove(environment);
                        }
                    }
                }
            }
        }

    }


    private void runAway(int i, int j) {
        //TODO
    }

    private void searchForFood(CellProjection[][] env) {
        //TODO
    }


}
