package org.smolny.agent;

import org.smolny.world.Cell;

import org.smolny.agent.types.AnimalType;
import org.smolny.agent.types.MaterialType;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    protected int lifeLevel;

    public Agent() {
        lifeLevel = 100;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    /**
     * Handle some tick dependent counters and internal processes, e.g. level of "hungry"
     */
    public void preTick() {

    }


    /**
     * 2*sight + 1 square array where central cell is the agent's location
     * @param environment
     */
    public void tick(Cell[][] environment) {

    }

}
