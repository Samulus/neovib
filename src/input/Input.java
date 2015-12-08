/*
    Input.java
    ----------

    The Input module is responsible for polling the user input
    and detecting which keys are being held, it only allows users
    to repeatedly trigger at whatever VibConstant.MIN_DELAY is set at.

    I included the depress and isDown methods for the Game module so that
    it would have low latency access to the keyboard so that it could perform
    more reliable beat detection without relying on the events traveling
    through the system fast enough.

 */

package input;

import constants.*;
import processing.core.PConstants;
import clock.Clock;
import event.EQ;
import event.VibEvent;

public class Input {

   private static Clock time;
   private static double lastTime;
   private static boolean[] pressed;
   private static boolean held;

   public static void Init() {
      pressed = new boolean[VibEvent.INPUT_LENGTH.getCode()];
      time = new Clock();
      lastTime = time.elapsedTime();
   }

   public static void poll() {

      if (time == null) {
         System.err.println("Input.java: Call Input.Init() first!");
         throw new RuntimeException();
      }


      if (held) {
         double now = time.elapsedTime();
         if (now - lastTime < VibConstant.MIN_DELAY) return;
         lastTime = now;
      }

      if (!held) return; /* avoid locking up system */

      /* General Traversal */
      if (pressed[VibEvent.INPUT_DOWN.getCode()]) EQ.enqueue(VibEvent.INPUT_DOWN);
      if (pressed[VibEvent.INPUT_UP.getCode()]) EQ.enqueue(VibEvent.INPUT_UP);
      if (pressed[VibEvent.INPUT_LEFT.getCode()]) EQ.enqueue(VibEvent.INPUT_LEFT);
      if (pressed[VibEvent.INPUT_RIGHT.getCode()]) EQ.enqueue(VibEvent.INPUT_RIGHT);
      if (pressed[VibEvent.INPUT_ACCEPT.getCode()]) EQ.enqueue(VibEvent.INPUT_ACCEPT);
      if (pressed[VibEvent.INPUT_CANCEL.getCode()]) EQ.enqueue(VibEvent.INPUT_CANCEL);
      if (pressed[VibEvent.INPUT_MARK_DIR.getCode()]) EQ.enqueue(VibEvent.INPUT_MARK_DIR);
      if (pressed[VibEvent.INPUT_PREVIOUS.getCode()]) EQ.enqueue(VibEvent.INPUT_PREVIOUS);
   }

   public static void keyAction(char key, boolean isPress) {

      held = isPress;

      // @formatter:off
      switch (key) {
         case 'k':
         case PConstants.UP: pressed[VibEvent.INPUT_UP.getCode()] = isPress; break;
         case 'j':
         case PConstants.DOWN: pressed[VibEvent.INPUT_DOWN.getCode()] = isPress; break;
         case 'h':
         case PConstants.LEFT: pressed[VibEvent.INPUT_LEFT.getCode()] = isPress; break;
         case 'l':
         case PConstants.RIGHT: pressed[VibEvent.INPUT_RIGHT.getCode()] = isPress; break;
         case 'm': pressed[VibEvent.INPUT_MARK_DIR.getCode()] = isPress; break;
         case 'q': pressed[VibEvent.INPUT_CANCEL.getCode()] = isPress; break;
         case PConstants.ENTER:
         case PConstants.RETURN: pressed[VibEvent.INPUT_ACCEPT.getCode()] = isPress; break;
         case PConstants.BACKSPACE: pressed[VibEvent.INPUT_PREVIOUS.getCode()] = isPress; break;
         case 'a': pressed[VibEvent.INPUT_DODGE_CROSS.getCode()] = isPress;  break;
         case 's': pressed[VibEvent.INPUT_DODGE_CIRCLE.getCode()] = isPress; break;
         case 'd': pressed[VibEvent.INPUT_DODGE_SQUARE.getCode()] = isPress; break;
         case 'f': pressed[VibEvent.INPUT_DODGE_TRIANGLE.getCode()] = isPress; break;
      }
      // @formatter:on

   }

   public static void depress(VibEvent check) {
      try {
         pressed[check.getCode()] = false;
      } catch (Exception e) {
         System.err.printf("Input.java: %s isn't a valid key\n", check);
         e.printStackTrace();
      }
   }

   public static boolean isDown(VibEvent check) {
      try {
         return pressed[check.getCode()];
      } catch (Exception e) {
         System.err.printf("Input.java: %s isn't a valid key\n", check);
         e.printStackTrace();
      }

      return false;
   }

}
