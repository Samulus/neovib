/*
   Scene.java
   ----------
   The Scene class is responsible for managing the various states in the game. Every
   single Scene in the game should extend the Abstract class AbstractScene and implement
   the required callbacks -> {input(VibEvent e), logic(), render(), pass()}
   Then in this class the Scene should be inserted in the HashMap. This way we can just call
   Scene.focus(VibEvent.SCENE_BLAH) and automatically start displaying that Scene in the game.

 */

package scene;

import processing.core.PApplet;
import event.VibEvent;

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
