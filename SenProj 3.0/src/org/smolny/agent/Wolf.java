package org.smolny.agent;

import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Coordinates> coor = new ArrayList<>();

        if (lifeLevel < 1) {
            handle.die();
        } else
        if (lifeLevel < 75) {
            // search for food
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    if (environment[i][j].getAgents().contains("Rabbit")) {
                        Coordinates xy = new Coordinates(i, j);
                        coor.add(xy);
                    }
                }
            }
            if (coor.isEmpty()) {
                // no rabbit found
                randomMove(environment);
            } else {
                // rabbit found
                choosePrey(coor);
                handle.eat();
            }
        } else {
            randomMove(environment);
        }
    }

    /**
     * look for the closest rabbit if more that one was found and pathFindTo() it
     */
    private void choosePrey(ArrayList<Coordinates> c) {
        //TODO

    }


}


//----inner class-----------------------------------------------------------------------------------------------------------------
 class Coordinates {

        private int x;
        private int y;

        public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        }


 }

