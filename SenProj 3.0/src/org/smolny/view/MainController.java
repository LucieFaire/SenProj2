package org.smolny.view;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.smolny.agent.Agent;
import org.smolny.agent.Grass;
import org.smolny.agent.Rabbit;
import org.smolny.agent.Wolf;
import org.smolny.view.agents.AgentView;
import org.smolny.view.agents.AgentViewFactory;
import org.smolny.view.preypred.GrassViewBuilder;
import org.smolny.view.preypred.RabbitViewBuilder;
import org.smolny.view.preypred.WolfViewBuilder;
import org.smolny.world.Cell;
import org.smolny.world.World;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Asus on 14.03.2016.
 */
public class MainController implements Initializable {

    @FXML
    private Pane pane;

    public static final List<Class<? extends Agent>> agentViewOrder = new ArrayList<>();
    {
        agentViewOrder.add(Grass.class);
        agentViewOrder.add(Rabbit.class);
        agentViewOrder.add(Wolf.class);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private World world = null;



    public void createAndDrawWorld() {
        world = createInitialWorld();
        drawWorld(world);
    }

    public void stopWorld() {
        //TODO
    }

    public void runWorld() {

        if(world == null) {
            Alert d = new Alert(Alert.AlertType.ERROR);
            d.setHeaderText("Generate world first");
            d.showAndWait();
            return;
        }

        Object lock = new Object();
        world.addTickListener(() -> {
            AtomicBoolean done = new AtomicBoolean(false);

            Platform.runLater(() -> {
                synchronized (lock) {
                    drawWorld(world);
                    done.set(true);
                    lock.notifyAll();
                }
            });

            synchronized (lock) {
                while (!done.get()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        Thread t = new Thread( () -> {
            world.start();
        });
        t.setName("WorldProcessing");
        t.setDaemon(true);
        t.start();
    }


    private void drawWorld(World world) {
        pane.getChildren().clear();

        int width = world.getGrid().length;
        int height = world.getGrid()[0].length;

        Bounds  paneBounds = pane.getLayoutBounds();

        drawGrid(paneBounds);

        double yDelta = paneBounds.getHeight() / height;
        double xDelta = paneBounds.getWidth() / width;

        //draw-grid-----------------------------------------------------------------------------------------------------
        Canvas canvas = new Canvas();
        canvas.setWidth(paneBounds.getWidth());
        canvas.setHeight(paneBounds.getHeight());

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.BLACK);
        graphics.setLineWidth(0.3);

        for(int i = 0; i < width; i++) {
            graphics.moveTo(i * xDelta, 0);
            graphics.lineTo(i * xDelta, canvas.getHeight());
        }
        for(int j = 0; j < height; j++) {
            graphics.moveTo(0,                 j * yDelta);
            graphics.lineTo(canvas.getWidth(), j * yDelta);
        }
        graphics.stroke();

        //draw-agents---------------------------------------------------------------------------------------------------

        List<Agent> agents = new ArrayList<>(world.getAgentLocations().keySet());
        Collections.sort(agents, (a1, a2) -> {
            int i1 = agentViewOrder.indexOf(a1.getClass());
            int i2 = agentViewOrder.indexOf(a2.getClass());
            return Integer.compare(i1, i2);
        });

        for(Agent agent : agents) {
            Cell cell = world.getAgentLocations().get(agent);

            if  (agent instanceof Grass) {
                Rectangle r = new Rectangle();
                r.setX(cell.getPoint().getX() * xDelta);
                r.setY(cell.getPoint().getY() * yDelta);
                r.setWidth(xDelta);
                r.setHeight(yDelta);
                r.setFill(Color.GREENYELLOW);
                pane.getChildren().add(r);
            }
            if (agent instanceof Wolf) {
                Circle circle = new Circle();
                circle.setCenterX(cell.getPoint().getX() * xDelta + xDelta / 3);
                circle.setCenterY(cell.getPoint().getY() * yDelta + yDelta / 3);
                circle.setRadius(xDelta / 3);
                circle.setFill(Color.RED);
                pane.getChildren().add(circle);
            }
            if (agent instanceof Rabbit) {
                Circle circle = new Circle();
                circle.setCenterX(cell.getPoint().getX() * xDelta + xDelta / 1.5);
                circle.setCenterY(cell.getPoint().getY() * yDelta + yDelta / 1.5);
                circle.setRadius(xDelta / 3);
                circle.setFill(Color.BLUE);
                pane.getChildren().add(circle);
            }


        }

    }

    private void animateAgents(AnimationHistory history) {
        ParallelTransition animation = new ParallelTransition();

        for(AnimationStep as : history.getSteps()) {
            AgentView view = getView(as.getAgent());

            switch(as.getType()) {
                case EAT:
                    break;
                case CREATE:
                    break;
                case SEX:
                    break;
                case DIE:
                    break;
                case MOVE:
                    break;
            }
        }
    }

    private void drawGrid(Bounds paneBounds) {
        int width = world.getGrid().length;
        int height = world.getGrid()[0].length;
        double yDelta = paneBounds.getHeight() / height;
        double xDelta = paneBounds.getWidth() / width;

        Canvas canvas = new Canvas();
        canvas.setWidth(paneBounds.getWidth());
        canvas.setHeight(paneBounds.getHeight());

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.BLACK);
        graphics.setLineWidth(0.3);

        for(int i = 0; i < width; i++) {
            graphics.moveTo(i * xDelta, 0);
            graphics.lineTo(i * xDelta, canvas.getHeight());
        }
        for(int j = 0; j < height; j++) {
            graphics.moveTo(0,                 j * yDelta);
            graphics.lineTo(canvas.getWidth(), j * yDelta);
        }
        graphics.stroke();

        pane.getChildren().add(canvas);

    }


    private World createInitialWorld() {
        World world = new World(50, 50);

        //register view builders
        AgentViewFactory.registerView(Grass.class, new GrassViewBuilder());
        AgentViewFactory.registerView(Wolf.class, new WolfViewBuilder());
        AgentViewFactory.registerView(Rabbit.class, new RabbitViewBuilder());

        return world;
    }

    //--view-management-------------------------------------------------------------------------------------------------

    private Map<Agent, AgentView> views = new HashMap<>();

    private AgentView getView(Agent a) {
        AgentView res = views.get(a);
        if(res == null) {
            res = AgentViewFactory.createAgentView(a);
            views.put(a, res);
        }
        return res;
    }
}
