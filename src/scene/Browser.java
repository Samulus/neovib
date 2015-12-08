/*
   Browser.java
   ------------
   The browser is a Scene that basically is an empty shell for using a FileBrowser
   ui element.

 */
package scene;

import event.VibEvent;
import musicdb.MusicDB;
import ui.FileBrowser;

import java.io.File;

public class Browser extends AbstractScene {

   public static File fsong;
   private FileBrowser browser;
   private String rootpath;
   private boolean once;

   public void pass(Object data) {
      rootpath = (String) data;
   }

   public void setup() {
      if (!once) {
         browser = new FileBrowser(MusicDB.getLibraryPath(), true);
         once = true;
      }
      browser.start();
   }

   public void render() {
      browser.render();
   }

   public void logic() {
   }

   public void input(VibEvent event) {

      if (event == VibEvent.INPUT_CANCEL) {
         Scene.focus(VibEvent.SCENE_TITLE);
      }

      if (browser.isBrowsing()) {
         if (browser.input(event)) {
            try {
               Scene.pass(VibEvent.SCENE_GAME, browser.getSelected());
               Scene.focus(VibEvent.SCENE_GAME);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }
}
