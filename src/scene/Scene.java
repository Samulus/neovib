package src.scene;

import java.util.HashMap;
import processing.core.PApplet;
import src.event.VibEvent;

public class Scene {

   private static HashMap<String, AbstractScene> list = new HashMap<String, AbstractScene>();
   private static AbstractScene current = null;
   public  static PApplet p = null;

   public static void init(PApplet p) {
      Scene.p = p;
      Scene.p.textFont(Scene.p.createFont("data/ostrich-regular.ttf", 64));
      Scene.list.put("Title", new Title());
      Scene.list.put("Debug", new Debug());
   }

   public static void focus(String name) {
      Scene.p.background(0);
      Scene.current = Scene.list.get(name);
      Scene.current.setup();
   }

   public static void render() {
      if (Scene.current == null)
         return;
      Scene.current.render();
   }

   public static void input(VibEvent key) {
      if (Scene.current == null)
         return;
      Scene.current.input(key);
   }

   public static void logic() {
      if (Scene.current == null)
         return;
      Scene.current.logic();
   }

}
