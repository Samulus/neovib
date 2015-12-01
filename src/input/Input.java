package src.input;

import processing.core.PConstants;
import src.clock.Clock;
import src.event.EQ;
import src.event.VibEvent;

public class Input {

    public static boolean HAPPENING = false;
    private static boolean keyHeld = false;
    private static Clock time;
    private static double lastTime;

    public static void poll(char pressed) {

      /*
      if (time == null) {
         time = new Clock();
         lastTime = time.elapsedTime();
      } else if (lastTime - time.elapsedTime() < 200) {
         lastTime = time.elapsedTime();
      } else return;
      */


        switch (pressed) {

            /* Movement */
            case 'k':
            case PConstants.UP:
                EQ.enqueue(VibEvent.INPUT_UP);
                break;
            case 'j':
            case PConstants.DOWN:
                EQ.enqueue(VibEvent.INPUT_DOWN);
                break;
            case 'h':
            case PConstants.LEFT:
                EQ.enqueue(VibEvent.INPUT_LEFT);
                break;
            case 'l':
            case PConstants.RIGHT:
                EQ.enqueue(VibEvent.INPUT_RIGHT);
                break;
            case 'm':
                EQ.enqueue(VibEvent.INPUT_MARK_DIR);
                break;
            case 'q':
                EQ.enqueue(VibEvent.INPUT_CANCEL);
                break;
            case PConstants.ENTER:
            case PConstants.RETURN:
                System.out.println("Triggered");
                EQ.enqueue(VibEvent.INPUT_ACCEPT);
                break;
            case PConstants.BACKSPACE:
                EQ.enqueue(VibEvent.INPUT_PREVIOUS);
                break;
        }


    }


    public static void toggle() {
    }

}
