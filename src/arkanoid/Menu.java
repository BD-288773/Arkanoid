package arkanoid;

import arkanoid.controller.Controller;
import arkanoid.controller.actions.RightKeyPressedAction;
import arkanoid.view.events.*;
import arkanoid.view.events.Event;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Menu extends JPanel{

    private class SpaceKeyListener implements KeyListener {
        BlockingQueue<Event> queue;
        public SpaceKeyListener(BlockingQueue<Event> queue) {
            this.queue = queue;
        }

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            try {
                queue.put(new SpaceKeyPressedEvent());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }

    private class RightKeyListener implements KeyListener{
        BlockingQueue<Event> queue;
        public RightKeyListener(BlockingQueue<Event> queue) {
            this.queue = queue;
        }

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            try {
                queue.put(new RightKeyPressedEvent());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }

    private class LeftKeyListener implements KeyListener{
        BlockingQueue<Event> queue;
        public LeftKeyListener(BlockingQueue<Event> queue) {
            this.queue = queue;
        }
        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            try {
                queue.put(new LeftKeyPressedEvent());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }

    private static JFrame frame;
    private static Gameplay game;
    private static GameInterface gameInterface;
    private static BlockingQueue<Event> queue;
    Menu() {

        frame = new JFrame("Arkanoid");
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        game = new Gameplay(new Dimension(frame.getWidth()*3/4,frame.getHeight()));
        gameInterface = new GameInterface(game);
        gameInterface.setSize(frame.getSize());
        queue = new LinkedBlockingQueue<>();
        frame.add(gameInterface);
        frame.addKeyListener(new SpaceKeyListener(queue));
        frame.addKeyListener(new RightKeyListener(queue));
        frame.addKeyListener(new LeftKeyListener(queue));
    }

    public static void main(String[] args) throws InterruptedException {
        new Menu();

        final double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        Controller controller = new Controller(game,gameInterface,queue);
        Thread thread = new Thread(controller);
        thread.start();
        while(!game.isInProgress()){}
        long lastTime = System.nanoTime();
        while(game.isInProgress()){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            //if (delta >= 1) {
              //  delta--;
                queue.put(new UpdateEvent());
            //}
            try {
                thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (game.isWon()) break;
        }

    }

};