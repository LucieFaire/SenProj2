package java.smolny.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dsh on 3/6/16.
 */
public class LivingEntity extends Agent {

    private String age;
    private int sight;
    Random rand = new Random();
    private List<Material> inventory;
    private double lifeTime;


    public LivingEntity() {
        super();
        this.age = "Adult";
        this.sight = rand.nextInt(3);
        if (this.sight == 0) {
            this.sight += 1;
        }
        this.inventory = new ArrayList<Material>(20);
        this.lifeTime = 0.0;
    }

    public double increaseLifeTime() {
        this.lifeTime += 0.5; // run once per tick
        return lifeTime;
    }


    public String getAge() {

        return age;
    }

    public int getSight() {

        return sight;
    }

    public void growUp() {
        if (this.age.equals("Baby")) {
            this.age = "Adult";
        }
    }

    public void move() {

    }

    public List<Material> getInventory() {

        return inventory;
    }

    public Material HoldInHand() {
        return null;
    }

    public void dropItem() {

    }

    public void putInInventory(Material item) {

    }

}
