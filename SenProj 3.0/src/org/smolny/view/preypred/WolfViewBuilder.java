package org.smolny.view.preypred;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.smolny.agent.Wolf;
import org.smolny.view.agents.AgentView;
import org.smolny.view.agents.AgentViewBuilder;

/**
 * Created by Asus on 12.04.2016.
 */
public class WolfViewBuilder implements AgentViewBuilder<Wolf> {

    @Override
    public AgentView createAgent(Wolf agent) {
        return new AgentView() {
            @Override
            public Node getView(int radius, int centerX, int centerY) {
                Circle c = new Circle();
                c.setCenterX(centerX);
                c.setCenterY(centerY);
                c.setRadius(radius);
                c.setFill(Color.RED);
                return c;
            }

            @Override
            public int getZIndex() {
                return 2;
            }
        };
    }

}
