package org.smolny.agent;

import org.smolny.agent.PreyPredator.PreyPredator;

import java.util.UUID;

/**
 * Created by dsh on 3/27/16.
 */
public class AgentProjection {

    private Class c;
    private long lifeTime;
    private UUID id;
    private char sex;
    private AgentProjection() {}

    public static AgentProjection create(Agent a) {
        AgentProjection ap = new AgentProjection();
        ap.lifeTime = a.getLifeTime();
        ap.c = a.getClass(); // what class does it return: Agent or ?
        ap.id = a.getId();

        if (PreyPredator.class.isAssignableFrom(a.getClass())) {
            ap.sex = ((PreyPredator) a).getSex();
        } else {
            ap.sex = ' ';
        }
        return ap;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public UUID getId() {
        return id;
    }

    public Class getC() {
        return c;
    }

    public char getSex() {
        return sex;
    }
}
