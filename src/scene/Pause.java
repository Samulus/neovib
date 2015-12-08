/*
   Pause.java
   ----------
   This is the pause screen. The name is kind of a misnomer because you can only try
   again or return to the browser.

 */

package scene;

import event.VibEvent;
import musicdb.MusicDB;
import ui.Menu;
import ui.SimilarArtists;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class Pause extends AbstractScene {

   private ArrayList<String> options = new ArrayList<String>();
   private Menu menu;
   private Set<String> artists;
   private String artist;
   private SimilarArtists sim;
   private boolean simMode;


   public Pause() {
      options = new ArrayList<String>();
      options.add("Try Again");
      options.add("Return to Browser");
      sim = new SimilarArtists();
      menu = new Menu(options, true);
   }

   public void pass(Object data) {
      artist = (String) data;
   }

   public void setup() {
      System.out.println("Working");
      if (MusicDB.getLibraryPath().equals("(not set)")) {
         options.remove("New Random Song");
         simMode = false;
      } else if (!options.contains("New Random Song")) {
         options.add(1, "New Random Song");
         simMode = true;
      }

      if (options.contains("New Random Song")) {
         simMode = true;
         sim.setup(artist);
      }


   }

   public void render() {
      Scene.p.background(0);
      menu.render(Scene.p.width / 7f, Scene.p.height / 2f);
      if (simMode) {
         sim.render();
         Scene.p.pushMatrix();
         Scene.p.translate(Scene.p.width / 10f, Scene.p.height / 1.1f);
         Scene.p.text("Similar Artists are displayed to the right", 0, 0);
         Scene.p.popMatrix();
      }
   }

   public void logic() {
   }

   public void input(VibEvent event) {
      menu.navigate(event);
      if (event == VibEvent.INPUT_ACCEPT) {

         if (options.get(menu.getIndex()).equals("Try Again")) {
            Scene.focus(VibEvent.SCENE_GAME);
         }

         if (options.get(menu.getIndex()).equals("New Random Song")) {
            String song = MusicDB.getRandom();
            Scene.pass(VibEvent.SCENE_GAME, new File(song));
            Scene.focus(VibEvent.SCENE_GAME);
         }

         if (options.get(menu.getIndex()).equals("Return to Browser")) {
            Scene.focus(VibEvent.SCENE_BROWSER);
         }
      }
   }


}
