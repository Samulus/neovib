/*
   Echonest.java
   ----------------------------------------------
   The EchoNest module is responsible for providing an interface to query
   the awesome EchoNest website. Right now it consists of one public method
   to get artists similar to a given artist for use in the game.

   Note that we're currently capped at 25 requests per minute. To help alleviate
   this limit all artists are cached in the cache folder. This way when you do
   Echonest.similar("Portishead"); we can look up if we already cached Portishead
   and get the artists similar to it.

   A big thanks to EchoHonest for providing such an awesome API and library!
 */

package src.musicdb;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;

import java.util.LinkedList;
import java.util.List;


public class Echonest {

   /* API / Cache Test */
   public static void main(String[] args) {

      EchoNestAPI echo = new EchoNestAPI(Secret.ECHOHONEST_KEY);
      List<Artist> results = null;

      LinkedList<Artist> list = new LinkedList<Artist>();

      String query = "FatBoy Slim";

      VibArtist martist = Cache.loadArtist(query);

      if (martist != null) {
         System.out.println("Cached\n-------");
         System.out.println(martist.getName());
         System.out.println(martist.getSimilar());
         System.out.println(martist.getSimID());
         return;
      }

      //return;

      martist = new VibArtist();

      try {
         results = echo.searchArtists(query);

         // Store Root Artist
         martist.setName(results.get(0).getName());
         martist.setID(results.get(0).getID());

         // Store Similar Artists
         for (Artist sim : results.get(0).getSimilar(15)) {
            martist.addSimilar(sim.getName());
            martist.addSimID(sim.getID());
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      System.out.println(martist.getName());
      System.out.println(martist.getSimilar());
      System.out.println(martist.getSimID());

      Cache.saveArtist(query, martist);
   }
}
