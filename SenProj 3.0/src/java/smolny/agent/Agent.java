package java.smolny.agent;

import org.smolny.agent_world.Cell;
import org.smolny.agent_world.World;

import java.util.Random;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    private Cell location;
    private int x;
    private int y;
    Random rand = new Random();
    public static int lifeLevel;

    public Agent() {
        this.x = rand.nextInt(World.length);
        this.y = rand.nextInt(World.length);
        this.location = new Cell(this.x, this.y);
        this.lifeLevel = 100;
    }

    public Cell getLocation() {
        return location;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }


}
