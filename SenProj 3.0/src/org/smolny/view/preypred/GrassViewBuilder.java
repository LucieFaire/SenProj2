package org.smolny.view.preypred;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.smolny.agent.PreyPredator.Grass;
import org.smolny.view.agents.AgentView;
import org.smolny.view.agents.AgentViewBuilder;

/**
 * Created by Asus on 12.04.2016.
 */
public class GrassViewBuilder implements AgentViewBuilder<Grass> {

    @Override
    public AgentView createAgent(final Grass agent) {
        return new AgentView() {
            @Override
            public Node getView(int radius, int centerX, int centerY) {
                Rectangle r = new Rectangle();
                r.setFill(Color.GREEN);
                r.setX(centerX - radius);
                r.setY(centerY - radius);
                r.setHeight( radius * 2 );
                r.setWidth( radius * 2 );
                return r;
            }

            @Override
            public int getZIndex() {
                return -1;
            }
        };
    }

}
