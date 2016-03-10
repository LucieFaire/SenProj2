package java.smolny.agent;

import org.smolny.agent_world.Cell;
import org.smolny.agent_world.World;

import java.smolny.agent.types.AnimalType;
import java.smolny.agent.types.MaterialType;
import java.util.Random;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Agent {

    static Object type;
    private Cell location;
    private int x;
    private int y;
    Random rand = new Random();
    public static int lifeLevel;
    private int id;
    static int hitLevel;

    static double hungerLevel;

    public Agent() {
        this.x = rand.nextInt(World.length);
        this.y = rand.nextInt(World.length);
        this.location = new Cell(this.x, this.y);
        lifeLevel = 100;
        hitLevel = 10;

    }

    public Cell getLocation() {

        return location;
    }

    public int getLifeLevel() {

        return lifeLevel;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public int hit() {
        if (type.equals(AnimalType.WOLF) || type.equals(AnimalType.BEAR)) {
            hitLevel = 20;
        } else
        if (type.equals(MaterialType.KNIFE) || type.equals(MaterialType.WEAPON)) {
            hitLevel = 25;
        } else
        if (type.equals(MaterialType.STICK) || type.equals(MaterialType.STONE)) {
            hitLevel = 15;
        } else {
            hitLevel = 0;
        }

        return hitLevel;

    }

}
