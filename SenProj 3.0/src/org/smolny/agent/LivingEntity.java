package org.smolny.agent;

import org.smolny.world.Cell;
import org.smolny.world.CellProjection;
import org.smolny.world.WorldHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dsh on 3/6/16.
 */
public class LivingEntity extends Agent {

    //private String age;
    Random rand = new Random();
    //private List<Material> inventory;


    public LivingEntity() {
        super();
        this.lifeLevel = 100;
    }


    @Override
    public void preTick() {
        super.preTick();
        lifeLevel--;

    }


    /**
     * inner logic of the agent behavior
     */
    public void randomMove(CellProjection[][] environment) {
        int size = environment.length;
        int center = size / 2;
        ArrayList<Integer> options = new ArrayList<Integer>();
        if (environment[center][center - 1] != null) {
            int goUp = 1;
            options.add(goUp);
        }
        if (environment[center][center + 1] != null) {
            int goDown = 2;
            options.add(goDown);
        }
        if (environment[center - 1][center] != null) {
            int goLeft = 3;
            options.add(goLeft);
        }
        if (environment[center + 1][center] != null) {
            int goRight = 4;
            options.add(goRight);
        }

        int choice = rand.nextInt(options.size());
        choice = options.get(choice);
        if (choice == 1) {
            handle.goUp();
        } else if (choice == 2) {
            handle.goDown();
        } else if (choice == 3) {
            handle.goLeft();
        } else if (choice == 4) {
            handle.goRight();
        }
    }


    public void calcHeuristic(CellProjection cp, int[][] heuristics, int pos) {
        for (int i = 0; i < heuristics.length; i++) {
            for (int j = 0; j < heuristics[i].length; j++) {
                heuristics[i][j] = Math.abs(pos - i) + Math.abs(pos - j);
            }
        }

    }

    public int getHeuristic(int[][] h, int x, int y) {
        return h[x][y];
    }

}

        //------------------------------------------------------------------------------------------
    //---garbage--------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------


//    public void eat(Material item) {
//        if (isInHand(item) && lifeLevel <= 75) {
//            if (type.equals(MaterialType.BERRIES) || type.equals(MaterialType.MEAT)
//                                                  || type.equals(MaterialType.MUSHROOM)) {
//                lifeLevel += 25;
//                //do smth about the item - has to despawn
//            }
//        }
//    }

//    public double starve() {
//        this.hungerLevel -= 0.5; // once per tick
//         return hungerLevel;
//    }

//    public double increaseLifeTime() {
//        this.lifeTime += 0.5; // run once per tick
//        return lifeTime;
//    }


//    public String getAge() {
//        return age;
//    }


//    public void growUp() {
//        if (this.age.equals("Baby")) {
//            this.age = "Adult";
//        }
//    }


//    public void damage() {
//        //depends on the type of the damage
//    }

//    public List<Material> getInventory() {
//        return inventory;
//    }

//    public Material pickUp() {
//
//        return null;
//    }

//    public void SetInHand(Material item) {
//
//    }

//    public void dropItem() {
//
//    }

//    public void putInInventory(Material item) {
//
//    }

//    public boolean isInInventory(Material item) {
//        return false;
//    }

//    public boolean isInHand(Material item) {
//        return false;
//        //variable for material to hold the value?
//    }

