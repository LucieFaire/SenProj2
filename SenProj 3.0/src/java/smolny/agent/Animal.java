package java.smolny.agent;

import java.smolny.agent.types.AnimalType;

/**
 * Created by dsh on 3/7/16.
 */
public class Animal extends Agent{

    public Animal(AnimalType t) {
        super();
        this.type = t;
        lifeLevel = 70;
        hungerLevel = 100;
        hit();
    }

    public void move() {

    }

    public void eat() {

    }

    public double starve() {
        this.hungerLevel -= 2.0; // once per tick
        return hungerLevel;
    }

    public void damage() {

    }


}

