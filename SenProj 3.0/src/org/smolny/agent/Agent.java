package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.Direction;
import org.smolny.world.WorldHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    protected int sight;
    private Random rand = new Random();
    public String name;

    protected int lifeLevel;
    protected int lifeTime;

    protected WorldHandle handle;

    public Agent() {
        this.lifeTime = 0;
        this.sight = rand.nextInt(4);
        if (this.sight == 0) {
            this.sight += 1;
        }
    }

    public void setHandle(WorldHandle handle) {
        this.handle = handle;
    }

    public int getSight() {
        return sight;
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

    public String getName() {
        return name;
    }

    /**
     * 2*sight + 1 square array where central cell is the agent's location
     * @param environment
     */
    public void tick(CellProjection[][] environment) {

    }




}
