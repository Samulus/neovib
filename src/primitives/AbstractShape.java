/*
   AbstractShape.java
   ------------------

   The abstract shape module is a parent class for all the various geometric shapes in the game.
   They all share the same code for coding the color / type of information so it makes sense to group them.

 */
package primitives;

import constants.VibConstant;
import scene.Scene;

public abstract class AbstractShape {

   protected float dst;
   protected float speed;
   protected float vibrate;
   protected String type;
   protected String state;

   public AbstractShape(float dst, float speed) {
      this.dst = dst;
      this.vibrate = 0.0f;
      this.speed = speed;
      this.state = "scrolling";
   }

   public void paint() {
      int[] c;
      if (state.equals("hit") && dst <= Scene.p.width / 7) {
         c = VibConstant.HIT_COLOR;
         Scene.p.stroke(c[0], c[1], c[2]);
      } else if (state.equals("miss") && dst <= Scene.p.width / 7f - 100) {
         c = VibConstant.MISS_COLOR;
         Scene.p.stroke(c[0], c[1], c[2]);
      } else {
         Scene.p.stroke(0);
      }

   }

   public void advance() {
      this.dst -= this.speed;
   }

   public float getDistance() {
      return this.dst;
   }

   public float getVibrate() {
      return this.vibrate;
   }

   public void setVibrate(float vibrate) {
      this.vibrate = vibrate;
   }

   public abstract void render();

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }
}
