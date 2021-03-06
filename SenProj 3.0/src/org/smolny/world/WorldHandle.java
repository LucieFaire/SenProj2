package org.smolny.world;

import org.smolny.AI.IntelligentAgent;
import org.smolny.agent.Agent;
import org.smolny.agent.Material;
import org.smolny.utils.IntPoint;

import java.awt.*;
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

    void createMaterial(Agent a, IntPoint lp);

    <T extends Agent> void createAgent(Class<T> c, Agent a, int number);

    void addAgent(Agent a, int x, int y);

    IntelligentAgent createIntelligence(UUID id);



}
