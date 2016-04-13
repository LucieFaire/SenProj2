package org.smolny.view.preypred;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import org.smolny.agent.Rabbit;
import org.smolny.view.agents.AgentView;
import org.smolny.view.agents.AgentViewBuilder;
import org.smolny.view.agents.AgentViewFactory;

/**
 * Created by Asus on 12.04.2016.
 */
public class RabbitViewBuilder implements AgentViewBuilder<Rabbit> {

    @Override
    public AgentView createAgent(Rabbit agent) {
        return new AgentView() {
            @Override
            public Node getView(int radius, int centerX, int centerY) {
                Circle c = new Circle();
                c.setCenterX(centerX);
                c.setCenterY(centerY);
                c.setRadius(radius);
                return c;
            }

            @Override
            public int getZIndex() {
                return 1;
            }
        };
    }
}
