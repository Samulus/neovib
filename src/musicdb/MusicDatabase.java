package src.musicdb; // ??? no .musicdb?

import java.io.File;
import java.util.*;
import src.clock.Clock;

// checkout : https://github.com/square/moshi for json

public class MusicDatabase {

   // http://stackoverflow.com/a/11658192
   public static Collection<File> listFileTree(File dir) {

      Set<File> fileTree = new HashSet<File>();
      for (File entry : dir.listFiles()) {
         if (entry.isFile()) fileTree.add(entry);
         else fileTree.addAll(listFileTree(entry));
      }
      return fileTree;
   }

   public static void main(String[] args) {

      Clock c = new Clock();
      File f = new File("/home/sam/music/");
      Collection<File> paths = listFileTree(f);
      ArrayList<String> strpaths = new ArrayList<String>();

      String query = "Quasimoto";

      for (File ff : paths) {
         strpaths.add(ff.toString());
      }

      Collections.sort(strpaths);

      for (String p : strpaths) {

         for (String crap : p.split("/")) {
            if (!crap.equals("") && crap.toLowerCase().replaceAll("\\s", "").contains(query.toLowerCase().replaceAll("\\s", ""))) { // hahahah
               System.out.println(p);
               break;
            }
         }
      }

      System.out.println(c.elapsedTime());

   }

}
