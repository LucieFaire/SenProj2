package org.smolny.agent;

import java.util.UUID;

/**
 * Created by dsh on 3/27/16.
 */
public class AgentProjection {

    private Class c;
    private UUID id;
    private AgentProjection() {}

    public static AgentProjection create(Agent a) {
        AgentProjection ap = new AgentProjection();
        ap.c = a.getClass(); // what class does it return: Agent or ?
        ap.id = a.getId();
        return ap;
    }

    public UUID getId() {
        return id;
    }

    public Class getC() {
        return c;
    }
}
