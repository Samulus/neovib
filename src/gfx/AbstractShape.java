package src.gfx;

public abstract class AbstractShape {

   protected float dst;
   protected float maxDistX;
   protected float vibrate;
   
   public AbstractShape(float dst, float maxDist) {
      this.dst = dst;
      this.maxDistX = maxDist;   
      this.vibrate = 0.0f;
   }

   public void advance(float speed)  {
     this.dst -= speed;
     //if (this.dst <= -60) /* flag delete me! */
   }

   public void setVibrate(float vibrate) {
     this.vibrate = vibrate;
   }

   public abstract void render();
   
 }
