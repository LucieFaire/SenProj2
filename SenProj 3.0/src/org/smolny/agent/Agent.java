package org.smolny.agent;

import org.smolny.utils.IntPoint;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.Random;
import java.util.UUID;


/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    protected int MAX = 1000000000;
    private double freq = 0.00000005;
    protected Random rand = new Random();
    protected WorldHandle handle;

    protected UUID id;
    protected int sight;
    protected double initiative;
    protected int lifeLevel;
    protected long lifeTime;

    protected IntPoint localPosition = IntPoint.create(0,0);

    public Agent() {
        this.id = UUID.randomUUID();
        this.lifeTime = 0;
        this.sight = rand.nextInt(4);
        if (this.sight == 0) {
            this.sight += 1;
        }
        this.initiative = rand.nextDouble();
    }

    public double getInitiative() {
        return this.initiative;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public void setHandle(WorldHandle handle) {
        this.handle = handle;
    }

    public int getSight() {
        return sight;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    public void setLifeLevel(int l) {
       this.lifeLevel += l;
        if (lifeLevel > 100) {
            this.lifeLevel = 100;
        }
    }

    public IntPoint getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(IntPoint localPosition) {
        this.localPosition = localPosition;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Handle some tick dependent counters and internal processes, e.g. level of "hungry"
     */
    public void preTick() {
        lifeTime++; // counts ticks in game
        lifeLevel -= 2;
        // || rand.nextDouble() < freq
        if (lifeLevel < 1) {
            this.handle.die();
        }
    }

    /**
     * 2*sight + 1 square array where central cell is the agent's location
     * @param environment
     */
    public void tick(CellProjection[][] environment) {

    }

    public void onDie() {

    }

    public void onCreate() {

    }

}
