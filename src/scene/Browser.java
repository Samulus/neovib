package src.scene;

import src.audio.Audio;
import src.event.VibEvent;
import src.ui.FileBrowser;

import java.io.File;

public class Browser extends AbstractScene {

    public static File fsong;
    private FileBrowser browser;
    private String rootpath;

    public Browser() {
        browser = new FileBrowser(rootpath, "file");
    }

    public void pass(Object data) {
        rootpath = (String) data;
    }

    public void setup() {
        browser.start();
    }

    public void render() {
        browser.render();
    }

    public void logic() {
    }

    public void input(VibEvent event) {
        if (browser.isBrowsing()) {
            if (browser.input(event)) {
                Scene.pass(VibEvent.SCENE_GAME, browser.getFile());
                try {
                    Scene.pass(VibEvent.SCENE_PAUSE, Audio.parseArtist(browser.getFile().getCanonicalPath()));
                } catch (Exception e) {
                }
                Scene.focus(VibEvent.SCENE_GAME);
            }
        }
    }
}
