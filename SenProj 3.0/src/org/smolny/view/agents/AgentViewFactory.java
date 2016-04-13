package org.smolny.view.agents;

import org.smolny.agent.Agent;
import org.smolny.agent.Wolf;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by Asus on 08.04.2016.
 */
public class AgentViewFactory {

    private static final ConcurrentHashMap<Class<? extends Agent>, AgentViewBuilder> viewBuilders =
        new ConcurrentHashMap<>();


    public static <T extends Agent> AgentView createAgentView(T agent) {
        AgentViewBuilder builder = viewBuilders.get(agent.getClass());
        if(builder == null)
            throw new RuntimeException("No view is registered for class " + agent.getClass());
        return builder.createAgent(agent);
    }

    public static <T extends Agent> void registerView (Class<T> clazz, AgentViewBuilder<T> viewBuilder) {
        viewBuilders.put(clazz, viewBuilder);
    }

}
