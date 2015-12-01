package src.scene;

import src.event.VibEvent;
import src.ui.Menu;

import java.util.ArrayList;
import java.util.Set;

public class Pause extends AbstractScene {

    private ArrayList<String> options = new ArrayList<String>();
    private Menu menu;
    private Set<String> artists;
    private String artist;

    public Pause() {
        options = new ArrayList<String>();
        options.add("Try Again");
        options.add("New Random Song");
        options.add("New Similar Artist");
        options.add("Return to Browser");
        menu = new Menu(options, "noscroll");
    }

    public void pass(Object data) {
        artist = (String) data;
    }

   public void setup() {
       // TODO: check if its offline first
       //Echonest.getSimilar(artist);
   }

   public void render() {
       Scene.p.background(0);
       menu.render(Scene.p.width / 7f, Scene.p.height / 2f);
   }

   public void logic() {
   }

   public void input(VibEvent event) {
       menu.navigate(event);
       if (event == VibEvent.INPUT_ACCEPT) {

           if (options.get(menu.getIndex()).equals("Try Again")) {
               Scene.focus(VibEvent.SCENE_GAME);
           }

           if (options.get(menu.getIndex()).equals("New Similar Artist")) {

               if (this.artist == null) {
                   System.err.println("Pause: you were supposed to pass a " +
                           "similar artist to the scene first");
                   throw new RuntimeException();
               }

               Scene.focus(VibEvent.SCENE_SIMILAR);
           }

           if (options.get(menu.getIndex()).equals("Return to Browser")) {
               Scene.focus(VibEvent.SCENE_BROWSER);
           }
       }
   }


}
