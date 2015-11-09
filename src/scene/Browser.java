package src.scene;

import src.event.EQ;
import src.event.VibEvent;
import src.ui.Menu;
import src.util.Util;

import java.io.File;
import java.util.ArrayList;

public class Browser extends AbstractScene {

   public static File fsong;
   private Menu menu;
   private File buffer; // current location in filesystem
   private ArrayList<String> list; // all filenames in directory
   private File[] flist; // all file objects in directory

   public Browser() {
      menu = new Menu(null);
   }

   public void setup() {
      Scene.p.textSize(32);
      rebuild(".");
   }

   public void rebuild(String path) {
      buffer = new File(path);
      flist = buffer.listFiles();
      list = Util.toArrayList(buffer.list());
      menu.refresh(list);
      menu.reset();
   }

   public void render() {
      Scene.p.background(0);
      menu.render(Scene.p.width / 10f, Scene.p.height / 10f);
   }

   public void logic() {
   }

   public void input(VibEvent event) {

      if (event.isNavigate()) {
         menu.navigate(event);
         return;
      }

      File tmp = null;

      try {
         tmp = new File(flist[menu.getIndex()].getCanonicalPath());
      } catch (Exception e) {
         /* attempt to access non existent entry blocked */
      }
      
      /* switch state */
      if (event == VibEvent.INPUT_ACCEPT) {
         try {
            if (tmp.isDirectory()) {
               rebuild(tmp.getCanonicalPath());
            } else {
               if (tmp.isFile()) {
                  Browser.fsong = tmp;
                  EQ.enqueue(VibEvent.SCENE_GAME);
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      /* travel up */
      else if (event == VibEvent.INPUT_PREVIOUS) {
         try {
            rebuild(buffer.getCanonicalFile().getParent());

         } catch (Exception e) {
            /* attempt to travel to non existent directory blocked */
         }
      }


   }

}
