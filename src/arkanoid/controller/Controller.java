package arkanoid.controller;

import arkanoid.GameInterface;
import arkanoid.Gameplay;
import arkanoid.controller.actions.*;
import arkanoid.view.events.*;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {

    private Gameplay gameplay;
    private GameInterface gameInterface;
    private BlockingQueue<Event> blockingQueue;
    private HashMap<Class <? extends Event>, Action> actionMap;

    public Controller(Gameplay gameplay, GameInterface gameInterface, final BlockingQueue<Event> blockingQueue) {
        this.gameplay = gameplay;
        this.gameInterface = gameInterface;
        this.blockingQueue = blockingQueue;
        actionMap = new HashMap<>();
        createMapping();
    }

    private void createMapping() {
        //for(Event e : blockingQueue)
        //{
        //    if(e.getClass().equals(LeftKeyPressedEvent.class))
                actionMap.put(LeftKeyPressedEvent.class, new LeftKeyPressedAction(gameplay));
        //    if(e.getClass().equals(RightKeyPressedEvent.class))
                actionMap.put(RightKeyPressedEvent.class, new RightKeyPressedAction(gameplay));
        //    if(e.getClass().equals(SpaceKeyPressedEvent.class))
                actionMap.put(SpaceKeyPressedEvent.class, new SpaceKeyPressedAction(gameplay));
        //    if(e.getClass().equals(UpdateEvent.class))
                actionMap.put(UpdateEvent.class, new UpdateAction(gameplay,gameInterface));
        //}
    }

    public void run() {
        while(true) {
            try {
                Event event = blockingQueue.take();
                actionMap.get(event.getClass()).perform();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
