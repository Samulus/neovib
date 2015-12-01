import processing.core.PApplet;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.scene.Scene;

public class Neovib extends PApplet {

    public static void main(String[] args) {
        PApplet.main(Neovib.class.getName());

    }

    public void settings() {
        size(1280, 720, P2D);
    }

    public void setup() {
        Scene.setContext(this);
        Scene.initScenes();
        Scene.p.frameRate(1000);
        Scene.focus(VibEvent.SCENE_TITLE);
    }

    public void draw() {

        while (true) {

            //Input.trigger();
            VibEvent event = EQ.dequeue();
            if (event == null) break;

            if (event.isNavigate()) {
                Scene.input(event);
            } else if (event == VibEvent.ERROR_CODE) {
                System.out.println("error detected audi5k we bounce now");
                System.exit(-1);
            } else if (event.isScene()) {
                Scene.focus(event);
            }
        }

        Scene.logic();
        Scene.render();

    }

    public void keyPressed() {
        Input.poll(key);
        //Input.key(key, true);
    }

    public void keyReleased() {
        //Input.key(key, true);
    }

}
