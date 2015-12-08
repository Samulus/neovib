/*
   FileBrowser.java
   ----------------
   The FileBrowser UI module provides an interface for Scenes to enable file / directory
   selection. Scenes should create a new instance of this class with the selected mode
   and starting location. Then they should call the start method to enable it, poll the
   isBrowsing method to check if a file / directory is still being selected, and then
   call the render method each time so it can be displayed. They should also call the input method.

   Essentially FileBrowser is a lightweight scene that gets overlaid on the current scene.

   TODO: fix the bug where if I go into the root folder or some other erroneous folder I can't get back out.
 */

package ui;

import event.VibEvent;
import musicdb.MusicDB;
import scene.Scene;

import java.io.File;
import java.util.*;

public class FileBrowser {

   boolean working = false;
   File selected;
   String marked;
   private Menu menu;
   private File buffer; // current location in filesystem
   private ArrayList<String> list; // all filenames in directory
   private File[] flist; // all file objects in directory
   private boolean fileMode; // if True then only picks files otherwise only directories can be marked
   private Stack<Integer> previous;

   public FileBrowser(String fpath, boolean fileMode) {

      this.fileMode = fileMode;
      menu = new Menu(null, false);
      list = new ArrayList<String>();
      previous = new Stack<Integer>();

      if (fpath == null) fpath = System.getProperty("user.home");
      rebuild(fpath);
   }

   public void start() {
      working = true;
   }

   public void rebuild(String path) {
      File prev = buffer;
      buffer = new File(path);
      flist = buffer.listFiles((File pathname) -> !pathname.getName().startsWith("."));
      if (flist == null) {
         buffer = new File(System.getProperty("user.home"));
         flist = buffer.listFiles((File pathname) -> !pathname.getName().startsWith("."));
      }
      list.clear();
      for (int i = 0; i < flist.length; ++i) {
         list.add(flist[i].getName());
      }
      Collections.sort(list);
      Arrays.sort(flist);
      menu.refresh(list);
      menu.reset();
   }

   public void render() {
      Scene.p.background(0);
      menu.render(Scene.p.width / 10f, Scene.p.height / 10f);

      if (!fileMode) {
         Scene.p.pushMatrix();
         Scene.p.translate(Scene.p.width / 2f, Scene.p.height / 16f);
         Scene.p.textSize(32);
         Scene.p.text("press m to mark a directory", 0, 0);
         Scene.p.popMatrix();
      }

   }

   public boolean isBrowsing() {
      return working;
   }

   public File getSelected() {
      return selected;
   }

   /* returns true on new file selection / new directory marking */
   public boolean input(VibEvent event) {

      menu.navigate(event);
      File tmp = null;

      try {
         tmp = new File(flist[menu.getIndex()].getCanonicalPath());
      } catch (Exception e) {
         return false;
      }

      if (event == VibEvent.INPUT_MARK_DIR && !fileMode) {
         if (tmp.isDirectory()) {
            try {
               marked = tmp.getCanonicalPath();
               MusicDB.updatePath(tmp.getCanonicalPath());
               working = false;
            } catch (Exception e) {
               System.err.printf("FileBrowser.java: unable to get cannoical marked directory");
               System.exit(-1);
            }
         }

      }

      /* travel down */
      else if (event == VibEvent.INPUT_ACCEPT) {
         try {
            if (tmp.isDirectory()) {
               rebuild(tmp.getCanonicalPath());
            } else {
               if (tmp.isFile() && fileMode) {
                  selected = tmp;
                  working = false;
                  return true;
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      /* travel up */
      else if (event == VibEvent.INPUT_PREVIOUS) {
         try {
            if (buffer.getCanonicalPath() != null)
               rebuild(buffer.getCanonicalFile().getParent());
         } catch (Exception e) {
            /* return to last known good parent */
         }
      }

      return false;
   }

   public void stop() {
      working = false;
   }
}
