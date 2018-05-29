package arkanoid.controller.actions;

import arkanoid.Gameplay;

public class RightKeyPressedAction extends Action{

    public RightKeyPressedAction(Gameplay gameplay){
        this.gameplay = gameplay;
    }
    @Override
    public void perform() {
        gameplay.getPlayersPad().move(gameplay.getSpeed());
    }
}
