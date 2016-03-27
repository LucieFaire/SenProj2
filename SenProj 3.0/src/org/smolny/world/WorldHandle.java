package org.smolny.world;

import org.smolny.agent.Agent;

import java.util.UUID;

/**
 * Created by Asus on 12.03.2016.
 */
public interface WorldHandle {

    void goUp();

    void goDown();

    void goLeft();

    void goRight();

    void eat(UUID id);

    void die();

    void grow(int x, int y);

}
