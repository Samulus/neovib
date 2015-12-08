/*
   Title.java
   ----------
   The title module is responsible for rendering the main menu in the game.

 */
package scene;

import processing.core.PConstants;
import event.VibEvent;
import musicdb.MusicDB;
import ui.FileBrowser;
import ui.Menu;

import java.util.ArrayList;

public class Title extends AbstractScene {

   private ArrayList<String> options;

   private Menu menu;
   private FileBrowser browser;

   public Title() {
      options = new ArrayList<String>();
      options.add("Browse");
      options.add("Sound Device");
      options.add("Set Library Path");

      menu = new Menu(options, true);
      browser = new FileBrowser(null, false);
   }

   public void pass(Object data) {
      if (MusicDB.getLibraryPath().equals("(not set)")) {
         Scene.pass(VibEvent.SCENE_BROWSER, MusicDB.getLibraryPath());
      }
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

      if (event == VibEvent.INPUT_CANCEL) {
         browser.stop();
      }

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
         if (choice.equals("Sound Device")) Scene.focus(VibEvent.SCENE_SOUND);
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
      Scene.p.text("current path: " + MusicDB.getLibraryPath(), 0, 0);
      Scene.p.popMatrix();
   }

}