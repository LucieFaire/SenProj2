package org.smolny;

import org.smolny.world.World;

/**
 * Created by dsh on 3/12/16.
 */
public class Main {

    public static  void main(String... args) {
        World world = new World(50,50);
        world.start();
    }

}
