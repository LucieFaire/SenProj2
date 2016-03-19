package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dsh on 3/12/16.
 */
public class HomoErectus extends LivingEntity {

    private Random rand = new Random();

    public HomoErectus(String name) {
        super();
        this.name = name;
    }

    @Override
    public void tick(CellProjection[][] environment) {
        randomMove(environment);
    }
}




