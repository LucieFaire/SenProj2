package org.smolny.view;

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
import org.smolny.agent.Agent;
import org.smolny.agent.Grass;
import org.smolny.agent.Rabbit;
import org.smolny.agent.Wolf;
import org.smolny.world.Cell;
import org.smolny.world.World;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Asus on 14.03.2016.
 */
public class MainController implements Initializable {

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    private World world = null;

    public void createAndDrawWorld() {
        world = createInitialWorld();
        drawWorld(world);
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

            while ( !done.get() ) {
                synchronized (lock) {
                    try { lock.wait(); } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t = new Thread( () -> {
            world.start();
        });
        t.setDaemon(true);
        t.start();
    }


    private void drawWorld(World world) {
        pane.getChildren().clear();

        int width = world.getGrid().length;
        int height = world.getGrid()[0].length;

        Bounds  paneBounds = pane.getLayoutBounds();
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

        for(Map.Entry<Agent, Cell> e : world.getAgentLocations().entrySet()) {
            Agent agent = e.getKey();
            Cell cell = e.getValue();
            Circle circle = new Circle();
            circle.setRadius(xDelta / 2);
            if  (agent instanceof Grass) {
                circle.setFill(Color.GREENYELLOW);
            }
            if (agent instanceof Wolf) {
                circle.setFill(Color.RED);
            }
            if (agent instanceof Rabbit) {
                circle.setFill(Color.BLUE);
            }
            circle.setCenterX(cell.getPoint().getX() * xDelta + xDelta / 2);
            circle.setCenterY(cell.getPoint().getY() * yDelta + yDelta / 2);
            pane.getChildren().add(circle);
        }


        pane.getChildren().add(canvas);

    }

    private World createInitialWorld() {
        World world = new World(50, 50);
        return world;
    }
}
