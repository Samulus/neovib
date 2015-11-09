package src.primitives;

import src.clock.Clock;
import src.event.VibEvent;
import src.scene.Scene;

public class Player {

   public boolean jumping = false;
   Clock delay = null;

   public void render() {
      if (!jumping) {
         Scene.p.pushMatrix();
         Scene.p.translate(Scene.p.width / 7f, Scene.p.height / 2.2f);
         Scene.p.line(0, 0, 0, 64);
         Scene.p.popMatrix();
      } else {
         Scene.p.pushMatrix();
         Scene.p.translate(Scene.p.width / 7f, Scene.p.height / 2.2f);
         Scene.p.line(0, 0, 0, -64);
         Scene.p.popMatrix();
      }
   }

   public void input(VibEvent event) {

      if (event == VibEvent.PLAYER_DODGE && delay == null) {
         jumping = true; delay = new Clock();
      }

   }

   public void logic(double dst) {
      System.out.println(dst);
      if (delay != null && delay.elapsedTime() > 32) {
         jumping = false;
         delay = null;
      }
   }


}
