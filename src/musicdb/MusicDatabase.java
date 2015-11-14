package src.musicdb;

import src.clock.Clock;

import java.io.File;
import java.util.*;

public class MusicDatabase {

   private ArrayList<String> db;

   public MusicDatabase(String fpath) {
      Collection<File> paths = listFileTree(new File(fpath));
      db = new ArrayList<String>();
      for (File f : paths) {
         db.add(f.toString());
      }
      Collections.sort(db);
   }

   // Taken from: http://stackoverflow.com/a/11658192
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

      String query = "Death Grips";
      MusicDatabase mdb = new MusicDatabase("/home/sam/music/");
      for (String s : mdb.find(query)) {
         System.out.println(s);
      }

      System.out.println(c.elapsedTime()); // 3-4s initially, 500ms after cacheing

   }

   public ArrayList<String> find(String query) {

      ArrayList<String> results = new ArrayList<String>();

      for (String path : db) {
         for (String token : path.split("/")) {
            if (token.toLowerCase().replaceAll("\\s", "").contains(query.toLowerCase().replaceAll("\\s", ""))) { // hahahah
               results.add(path);
               break; // move on to next path
            }
         }
      }

      return results;
   }

}
