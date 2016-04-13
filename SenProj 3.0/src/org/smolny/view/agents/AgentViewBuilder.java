package org.smolny.view.agents;

import org.smolny.agent.Agent;

/**
 * Created by Asus on 12.04.2016.
 */
public interface AgentViewBuilder<T extends Agent> {

    AgentView createAgent(T agent);

}
