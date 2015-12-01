package src.scene;

import processing.core.PConstants;
import src.event.VibEvent;
import src.musicdb.MusicDatabase;
import src.ui.FileBrowser;
import src.ui.Menu;

import java.util.ArrayList;

public class Title extends AbstractScene {

    private ArrayList<String> options;

    private Menu menu;
    private FileBrowser browser;

    public Title() {

        /* game menu */
        options = new ArrayList<String>();
        options.add("Browse");
        options.add("Sound Device");
        options.add("Set Library Path");

        /* ui elements */
        menu = new Menu(options, "noscroll");
        browser = new FileBrowser(null, "directory");
    }

    public void pass(Object data) {
    }

    public void setup() {
        Scene.p.noFill();
        Scene.p.stroke(255);
        Scene.p.textAlign(PConstants.RIGHT);
    }

    public void render() {
        Scene.p.background(0);
        if (browser.isBrowsing()) {
            browser.render();
            return;
        }
        renderTitle();
        renderPath();
        menu.render(Scene.p.width / 7f, Scene.p.height / 2f);
    }

    public void input(VibEvent event) {

        if (browser.isBrowsing()) {
            browser.input(event);
            return;
        }

        if (event.isNavigate())
            menu.navigate(event);

        /* switch state */
        if (event == VibEvent.INPUT_ACCEPT) {
            String choice = menu.getValue();
            if (choice.equals("Browse")) Scene.focus(VibEvent.SCENE_BROWSER);
            if (choice.equals("Sound Device")) Scene.focus(VibEvent.SCENE_SOUND_SETTINGS);
            if (choice.equals("Set Library Path")) browser.start();
        }

    }

    public void logic() {
    }

    private void renderTitle() {
        Scene.p.pushMatrix();
        Scene.p.translate(Scene.p.width / 1.1f, Scene.p.height / 1.1f);
        Scene.p.textSize(32);
        Scene.p.text("neovib", 0, 0);
        Scene.p.popMatrix();
    }

    private void renderPath() {
        Scene.p.pushMatrix();
        Scene.p.translate(Scene.p.width / 16f, Scene.p.height / 1.1f);
        Scene.p.textSize(32);
        Scene.p.text("current path: " + MusicDatabase.libraryPath, 0, 0);
        Scene.p.popMatrix();
    }

}