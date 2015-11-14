/*
   Cache.java
   -------------------------------------------------
   The cache module accepts Aritst and serializes them into the
   cache/ folder in the project directory.

 */
package src.musicdb;

import java.io.*;

public class Cache {

   private static final String cpath = "cache/";

   public static VibArtist loadArtist(String artist) {

      // normalize string so that slightly different strings are still detected
      String normalized = artist.replaceAll("\\s+", "").toLowerCase();

      VibArtist output = null;
      File file = null;

      /* Read File */
      try {
         file = new File(cpath + normalized);
      } catch (Exception e) {
         System.err.printf("Cache.java | exception occured opening %s\n", artist);
      }

      try {
         System.out.println(file.getCanonicalPath());
      } catch (Exception e) {
         e.printStackTrace();
      }

      /* Deserialize */
      if (file.exists() && file.isFile()) {
         try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            output = (VibArtist) oin.readObject();
            fin.close();
            oin.close();
         } catch (Exception e) {
            System.err.printf("Cache.java | exception occured deserialzing %s\n", normalized);
         }

      }

      return output;
   }

   public static boolean saveArtist(String artist, VibArtist obj) {
      if (artist == null || obj == null) return false;

      String normalized = artist.replaceAll("\\s+", "").toLowerCase();
      System.out.println(normalized);

      File file = null;

      /* Write File */
      try {
         file = new File(cpath + normalized);
      } catch (Exception e) {
         System.err.printf("Cache.java | exception occured opening %s", artist);
         return false;
      }

      /* Serialize */
      if (!file.exists()) {
         try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(obj);
            oout.close();
            fout.close();
         } catch (Exception e) {
            System.err.printf("Cache.java | exception occured serializing %s", artist);
            return false;
         }
      }

      return true;
   }

}
