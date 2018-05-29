package arkanoid.controller.actions;

import arkanoid.Gameplay;

public class LeftKeyPressedAction extends Action {

    public LeftKeyPressedAction(Gameplay gameplay){
        this.gameplay = gameplay;
    }
    @Override
    public void perform() {
        gameplay.getPlayersPad().move(-gameplay.getSpeed());
    }
}
