package src.ui;

import processing.core.PConstants;
import src.event.VibEvent;
import src.scene.Scene;

import java.util.ArrayList;

public class Menu {

   private ArrayList<String> values;
   private int selected = 0;
   private int offset = 0;
   private boolean needScroll = false;
   private boolean noScroll = false;

   public Menu(ArrayList<String> values, boolean noScroll) {
      this.values = (values == null) ? new ArrayList<String>() : values;
      this.noScroll = noScroll;
   }

   public void render(float x, float y) {

      Scene.p.pushMatrix();
      Scene.p.textAlign(PConstants.LEFT);
      Scene.p.translate(x, y);

      // avoid rendering null menu
      if (this.values == null) {
         Scene.p.popMatrix();
         return;
      }

      float dst = 0;
      int vibrate = 0;

      offset = (noScroll) ? 0 : selected;
      for (int i = offset; i < values.size(); ++i) {
         vibrate = (i == selected) ? 5 : 0;
         Scene.p.text(values.get(i), 0, dst + Scene.p.random(vibrate));
         dst += 32;

         this.needScroll = (dst >= Scene.p.height - 128);
         if (this.needScroll) {
            break;
         }
      }

      Scene.p.popMatrix();
   }

   public void navigate(VibEvent event) {
      if (event == VibEvent.INPUT_DOWN) {
         selected = (selected < values.size() - 1) ? selected + 1 : 0;
      } else if (event == VibEvent.INPUT_UP) {
         selected = (selected > 0) ? selected - 1 : values.size() - 1;
      }

   }

   public int getIndex() {
      return selected;
   }

   public String getValue() {
      return values.get(selected);
   }

   public void reset() {
      selected = 0;
      offset = 0;
   }

   public void refresh(ArrayList<String> values) {
      this.values = values;
   }

}
