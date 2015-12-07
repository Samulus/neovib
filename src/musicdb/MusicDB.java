/*
   MusicDatabase.java
   ------------------
   The MusicDatabase module is the main glue between the Graph,
   Echonest, and VibArtist modules

 */

package src.musicdb;

import src.util.Util;

import java.io.File;
import java.util.*;

public class MusicDB {

   private static String libraryPath = "(not set)";
   private static ArrayList<String> db = new ArrayList<>();
   private static Graph<VibArtist> graph = new Graph<>();

   public static void updatePath(String fpath) {
      libraryPath = fpath;
      Collection<File> paths = Util.listFileTree(new File(fpath));
      if (paths == null) {
         System.err.printf("MusicDB.java: Could not recursively iterate over %s\n", fpath);
         return;
      }
      db.clear();
      for (File f : paths) db.add(f.toString());
   }

   public static String getLibraryPath() {
      return libraryPath;
   }

   public static void loadDB(String fpath) {
      MusicDB.graph = Graph.Deserialize(fpath);
      if (MusicDB.graph == null) MusicDB.graph = new Graph<VibArtist>();
   }

   public static String getRandom() {

      Collections.shuffle(MusicDB.db);

      for (String fpath : MusicDB.db) {
         String p = fpath.toLowerCase();
         if (p.endsWith(".mp3") || p.endsWith(".flac")
                 || p.endsWith(".m4a")
                 || p.endsWith("ogg")) {
            return fpath;
         }
      }

      return null;
   }

   public static TreeSet<VibArtist> getSimilar(VibArtist query) {
      if (!Echonest.isConnected()) return null;
      Set<VibArtist> results = graph.getNode(query);
      if (results == null || results.size() < 15) {
         results = Echonest.getSimilar(query.getName());
         query.setID(Echonest.getID(query.getName()));
         graph.addNode(query);
         if (results == null) return null;
         for (VibArtist name : results) {
            graph.addNode(name);
            graph.addEdge(query, name);
         }
         Graph.Serialize(graph, "db/similar.db");
      }
      return new TreeSet(results);
   }

   public static void main(String[] args) {

      Echonest.Connect();
      MusicDB.loadDB("db/similar.db");
      for (VibArtist artist : MusicDB.getSimilar(new VibArtist("Martina Topley-Bird", null))) {
         System.out.println(artist);
      }
   }

}
