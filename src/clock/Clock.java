package clock;

public class Clock {

   private final long start;

   public Clock() {
      start = System.nanoTime() / 1000000;
   }

   public static void main(String[] args) {

      Clock c = new Clock();

      while (true) {
         System.out.println(c.elapsedTime());
      }

   }

   public double elapsedTime() {
      long now = System.nanoTime() / 1000000;
      return (now - start);
   }


}