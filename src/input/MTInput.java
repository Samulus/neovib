/*
   MTInput.java
   -----------------------------------------
   This class is an attempt to get multithreaded game input working
   in Java. The reason why I am using a seperate thread instead of
   doing everything in the same thread is to overcome latency that is
   introduced. The class accepts a HashMap of AbstractScenes, whenever it detects
   input it immediately calls the input method of the focused scene.

   I don't think I need to worry about race conditions or anything like that
   because this module doesn't switch scenes it only calls the input method of the
   current scene.

 */
package src.input;

import src.clock.Clock;
import src.scene.Scene;

public class MTInput implements Runnable {

   public void run() {
      Clock c = new Clock();
      int speed = 100;

      while (true) {
         synchronized ((Boolean) Scene.p.keyPressed) {
            if (c.elapsedTime() > 7000) {
               speed = 10;
            }

            if (Scene.p.keyPressed) {
               Input.poll(Scene.p.key);
            }
            try {
               Thread.sleep(speed);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }
}

