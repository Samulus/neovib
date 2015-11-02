package src.input;
import processing.core.PConstants;
import src.event.VibEvent;
import src.scene.Scene;
import src.event.EQ;

public class Input {
   
  private static boolean keyHeld = false;

  public static void poll(char pressed) {
     
     if (!Scene.p.keyPressed)
        keyHeld = false;
     
     if (Scene.p.keyPressed && !keyHeld) {
       keyHeld = true;
       switch (pressed) {
         case 'k':
         case PConstants.UP:       EQ.enqueue(VibEvent.INPUT_UP);    break;
         case 'j':
         case PConstants.DOWN:     EQ.enqueue(VibEvent.INPUT_DOWN);  break;
         case 'h':
         case PConstants.LEFT:     EQ.enqueue(VibEvent.INPUT_LEFT);  break;
         case 'l':
         case PConstants.RIGHT:    EQ.enqueue(VibEvent.INPUT_RIGHT); break;
         case PConstants.ENTER:  
         case PConstants.RETURN:    EQ.enqueue(VibEvent.INPUT_ACCEPT);  break;
         case PConstants.BACKSPACE: EQ.enqueue(VibEvent.INPUT_CANCEL);  break;
       }
     }

  }
}
