package org.smolny.agent;

import org.smolny.agent.types.MaterialType;
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
        this.hungerLevel = 50;  
        hit();
    }

    public void eat(Material item) {
        if (isInHand(item) && lifeLevel <= 75) {
            if (type.equals(MaterialType.BERRIES) || type.equals(MaterialType.MEAT)
                                                  || type.equals(MaterialType.MUSHROOM)) {
                lifeLevel += 25;
                //do smth about the item - has to despawn
            }
        }
    }

    public double starve() {
        this.hungerLevel -= 0.5; // once per tick
         return hungerLevel;
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

    public void damage() {
        //depends on the type of the damage
    }

    public List<Material> getInventory() {

        return inventory;
    }

    public Material pickUp() {

        return null;
    }

    public void SetInHand(Material item) {

    }

    public void dropItem() {

    }

    public void putInInventory(Material item) {

    }

    public boolean isInInventory(Material item) {
        return false;
    }

    public boolean isInHand(Material item) {
        return false;
        //variable for material to hold the value?
    }
}
