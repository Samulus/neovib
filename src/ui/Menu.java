package src.ui;

import processing.core.PConstants;
import src.event.VibEvent;
import src.scene.Scene;

public class Menu {

   private String[] values = null;
   private int selected = 0;
   private int offset = 0;
   private boolean needScroll = false;
   private boolean noScroll = false;

   public Menu(String[] values) {
      this.values = values;
   }

   public Menu(String[] values, String mode) {
      this.values = values;
      noScroll = mode.equals("noscroll");
   }

   public void render(float x, float y) {

      Scene.p.pushMatrix();
      Scene.p.textAlign(PConstants.LEFT);
      Scene.p.translate(x, y);

        /* Invalid Directory / Error Entering Directory */
      if (this.values == null) {
         values = new String[1];
         values[0] = "(no valid files detected)";
         Scene.p.popMatrix();
         return;
      }

      float dst = 0;
      int vibrate = 0;

      offset = (noScroll) ? 0 : selected;
      for (int i = offset; i < values.length; ++i) {
         vibrate = (i == selected) ? 5 : 0;
         Scene.p.text(values[i], 0, dst + Scene.p.random(vibrate));
         dst += 32;

         this.needScroll = (dst >= Scene.p.height - 64);
         if (this.needScroll) {
            break;
         }
      }

      Scene.p.popMatrix();
   }


   public void navigate(VibEvent event) {
      assert event.isNavigate();

      if (event == VibEvent.INPUT_DOWN) {
         selected += (selected < values.length - 1) ? 1 : 0;
      } else if (event == VibEvent.INPUT_UP) {
         selected -= (selected > 0) ? 1 : 0;
      }

   }

   public int getIndex() {
      return selected;
   }

   public void reset() {
      selected = 0;
      offset = 0;
   }

   public void refresh(String[] values) {
      this.values = values;
   }


}
