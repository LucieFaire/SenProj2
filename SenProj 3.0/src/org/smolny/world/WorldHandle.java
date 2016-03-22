package org.smolny.world;

import org.smolny.agent.Agent;

/**
 * Created by Asus on 12.03.2016.
 */
public interface WorldHandle {

    void goUp();

    void goDown();

    void goLeft();

    void goRight();

    void eat(Agent a);

    void die();

}
