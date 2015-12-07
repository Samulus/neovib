package ui;

import event.VibEvent;
import musicdb.MusicDB;
import musicdb.VibArtist;
import scene.Scene;

import java.util.ArrayList;
import java.util.Set;

public class SimilarArtists {

   private ArrayList<String> names;
   private Menu menu;

   public SimilarArtists() {
      names = new ArrayList<>();
      menu = new Menu(names, false);
   }

   public void setup(String artist) {
      Set<VibArtist> vibArtists = MusicDB.getSimilar(new VibArtist(artist, null));

      if (vibArtists == null) {
         menu.refresh(null);
         return;
      }

      names.clear();
      for (VibArtist n : vibArtists)
         names.add(n.getName());
      menu.refresh(names);
   }

   public void render() {
      menu.render(Scene.p.width / 2f, Scene.p.height / 10f);
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 10f, Scene.p.height / 1.1f);
      Scene.p.text("Similar Artists are displayed to the right", 0, 0);
      Scene.p.popMatrix();
   }

   public void input(VibEvent event) {
      //menu.navigate(event);
   }

}
