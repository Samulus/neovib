/*
   Util.java
   ---------
   The Utility package consists of a hodgepoge of various functionality.

 */

package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Util {

   public static float[] stringToFloat(String[] arr) {
      float[] output = new float[arr.length];
      for (int i = 0; i < arr.length; ++i)
         output[i] = Float.parseFloat(arr[i]);
      return output;
   }

   public static ArrayList<String> toArrayList(String[] arr) {

      ArrayList<String> output = new ArrayList<String>();
      for (int i = 0; i < arr.length; ++i) {
         output.add(arr[i]);
      }

      return output;
   }

   // Recursively visit all directories
   // Borrowed From: http://stackoverflow.com/a/11658192
   public static Collection<File> listFileTree(File dir) {

      if (dir == null) {
         System.err.println("MusicDatabase.java: " + dir + "is a nonexistent or invalid directory");
         return null;
      }

      Set<File> fileTree = new HashSet<File>();
      for (File entry : dir.listFiles((File pathname) -> !pathname.getName().startsWith("."))) {
         if (entry.isFile()) fileTree.add(entry);
         else fileTree.addAll(listFileTree(entry));
      }
      return fileTree;
   }

   public static ArrayList<String> find(ArrayList<String> pool, String query) {

      ArrayList<String> results = new ArrayList<String>();

      for (String path : pool) {
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
