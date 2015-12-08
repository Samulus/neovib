/*
   AbstractScene.java
   ------------------
   This is the AbstractScene module. Every Scene in the game should extend and implement
   these callbacks. This way the Scene.java module knows how to do the appropriate actions for each
   Scene.
 */

package scene;

import event.VibEvent;

public abstract class AbstractScene {

   public abstract void setup();

   public abstract void render();

   public abstract void logic();

   public abstract void input(VibEvent event);

   public abstract void pass(Object data);
}