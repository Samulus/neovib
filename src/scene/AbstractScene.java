package src.scene;

import src.event.VibEvent;

public abstract class AbstractScene {

   public abstract void setup();

   public abstract void render();

   public abstract void logic();

   public abstract void input(VibEvent event);

   public abstract void pass(Object data);
}