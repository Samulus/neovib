package src.primitives;

public abstract class AbstractShape {

   protected float dst;
   protected float speed;
   protected float vibrate;
   protected String state;

   public AbstractShape(float dst, float speed) {
      this.dst = dst;
      this.vibrate = 0.0f;
      this.speed = speed;
      this.state = "scrolling";
   }

   public void advance() {
      this.dst -= this.speed;
   }

   public float getDistance() {
      return this.dst;
   }

   public void setState(String state) {
      this.state = state;
   }

   public float getVibrate() {
      return this.vibrate;
   }

   public void setVibrate(float vibrate) {
      this.vibrate = vibrate;
   }

   public abstract void render();

}
