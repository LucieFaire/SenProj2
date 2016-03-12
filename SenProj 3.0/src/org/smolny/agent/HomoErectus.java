package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.WorldHandle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dsh on 3/12/16.
 */
public class HomoErectus extends LivingEntity {

    private Random rand = new Random();


    public HomoErectus() {
        super();
        lifeLevel = 100;
        name = new String("Lucy");
    }

    @Override
    public void tick(Cell[][] environment, WorldHandle handle) {
        int size = environment.length;
        int center = (size + 1) / 2;
        ArrayList<Integer> options = new ArrayList<Integer>();
        if (environment[center][center--] != null) {
            int goUp = 1;
            options.add(goUp);
        }
        if (environment[center][center++] != null) {
            int goDown = 2;
            options.add(goDown);
        }
        if (environment[center--][center] != null) {
            int goLeft = 3;
            options.add(goLeft);
        }
        if (environment[center++][center] != null) {
            int goRight = 4;
            options.add(goRight);
        }

        int choice = rand.nextInt(options.size());
        if (choice == 0) {
            handle.goUp();
        } else
        if (choice == 1) {
            handle.goDown();
        } else
        if (choice == 2) {
           handle.goLeft();
        } else
        if (choice == 3) {
            handle.goRight();
        }

    }
}




