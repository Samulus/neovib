package scene;

import event.VibEvent;
import ui.Menu;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class Sound extends AbstractScene {

   private static String selectedMixer;
   private ArrayList<String> mixNames;
   private Menu menu;

   public Sound() {
      mixNames = new ArrayList<>();
      for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
         mixNames.add(mixerInfo.getName());
      }
      menu = new Menu(mixNames, false);

      try {
         selectedMixer = mixNames.get(0);
      } catch (Exception e) {
         System.err.println("Sound: We couldn't even find 1 mixer!");
         throw new RuntimeException();
      }
   }

   public static String getSelectedMixer() {
      return selectedMixer;
   }

   public void pass(Object data) {
   }

   public void setup() {
   }

   public void render() {
      Scene.p.background(0);
      menu.render(Scene.p.width / 10f, Scene.p.height / 10f);
   }

   public void input(VibEvent event) {
      menu.navigate(event);

      if (event == VibEvent.INPUT_ACCEPT) {
         selectedMixer = menu.getValue();
         Scene.focus(VibEvent.SCENE_TITLE);
      }

      if (event == VibEvent.INPUT_CANCEL) {
         Scene.focus(VibEvent.SCENE_TITLE);
      }

   }

   public void logic() {
   }

}
