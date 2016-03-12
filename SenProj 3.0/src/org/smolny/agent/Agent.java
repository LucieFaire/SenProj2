package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.Direction;
import org.smolny.world.WorldHandle;


/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    protected int lifeLevel;
    protected int lifeTime;

    public Agent() {
        this.lifeTime = 0;
    }

    public long getLifeLevel() {
        return lifeLevel;
    }


    /**
     * Handle some tick dependent counters and internal processes, e.g. level of "hungry"
     */
    public void preTick() {
        lifeTime++; // counts ticks in game
    }


    /**
     * 2*sight + 1 square array where central cell is the agent's location
     * @param environment
     */
    public void tick(Cell[][] environment, WorldHandle handle) {

    }




}
