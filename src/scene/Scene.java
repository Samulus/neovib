package src.scene;

import processing.core.PApplet;
import src.event.VibEvent;

import java.util.HashMap;

public class Scene {

   public static PApplet p = null;
   private static HashMap<String, AbstractScene> list = new HashMap<String, AbstractScene>();
   private static AbstractScene current = null;
   private static VibEvent currEvent;

   public static void setContext(PApplet p) {
      Scene.p = p;
   }

   public static void initScenes() {
      Scene.p.textFont(Scene.p.createFont("data/ostrich-regular.ttf", 64));
      Scene.list.put(VibEvent.SCENE_TITLE.getStr(), new Title());
      Scene.list.put(VibEvent.SCENE_GAME.getStr(), new Game());
      Scene.list.put(VibEvent.SCENE_BROWSER.getStr(), new Browser());
      Scene.list.put(VibEvent.SCENE_PAUSE.getStr(), new Pause());
      Scene.list.put(VibEvent.SCENE_SOUND.getStr(), new Sound());
   }

   public static void focus(VibEvent event) {
      currEvent = event;
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
   }

   public static void logic() {
      if (Scene.current == null) return;
      Scene.current.logic();
   }

   public static void pass(VibEvent event, Object data) {
      Scene.list.get(event.getStr()).pass(data);
   }
}
