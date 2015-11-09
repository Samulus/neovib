package src.scene;

import processing.core.PApplet;
import src.event.VibEvent;

import java.util.HashMap;

public class Scene {

   public static PApplet p = null;
   private static HashMap<String, AbstractScene> list = new HashMap<String, AbstractScene>();
   private static AbstractScene current = null;

   public static void setContext(PApplet p) {
      Scene.p = p;
   }

   public static void initScenes() {
      Scene.p.textFont(Scene.p.createFont("data/ostrich-regular.ttf", 64));
      Scene.list.put("Title", new Title());
      Scene.list.put("Game", new Game());
      Scene.list.put("Browser", new Browser());
      Scene.list.put("Video Lag", new VideoLag());
   }

   public static void focus(VibEvent event) {
      assert event.isScene();
      Scene.p.background(0);
      Scene.p.noFill();
      Scene.current = Scene.list.get(event.getStr());
      Scene.current.setup();
   }

   public static void render() {
      if (Scene.current == null) return;
      Scene.current.render();
   }

   public static void input(VibEvent key) {
      if (Scene.current == null) return;
      Scene.current.input(key);

      if (Scene.p.keyPressed) {
      }

   }

   public static void logic() {
      if (Scene.current == null) return;
      Scene.current.logic();
   }

}
