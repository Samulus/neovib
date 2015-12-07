/*
   Echonest.java
   ----------------------------------------------
   The EchoNest module is responsible for providing an interface to query
   the awesome Echonest API. Right now it consists of one public method
   to get artists similar to a given artist for use in the game.

   The free API Key Provided is limited to 25 requests per minute. We use the time and
   lastTime variables to ensure that we don't accidentally go over. I have to email
   the nice people at Echonest and ask them for a special API key soon ;)

   Note that we're currently capped at 25 requests per minute. To help alleviate
   this limit all artists are cached in the cache folder. This way when you do
   Echonest.similar("Portishead"); we can look up if we already cached Portishead
   and get the artists similar to it.

   A big thanks to Echonest for providing such an awesome API and easy to use java library!
 */

package musicdb;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import clock.Clock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Echonest {

   private static final int maxRequest = 25;
   private static EchoNestAPI Echo;
   private static Clock time;
   private static double lastTime;
   private static int requests;
   private static boolean connected = false;

   public static void Connect(String apiKey) {
      Echo = new EchoNestAPI(apiKey);
      lastTime = 0;
      time = new Clock();
      connected = true;
   }

   public static void Connect() {
      Connect(Secret.ECHOHONEST_KEY);
   }

   public static void ConnectFromFile() {

      File file = new File("apikey.txt");
      String key = "";
      if (file.exists()) {
         try {
            FileReader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            key = br.readLine();
         } catch (Exception e) {
         }
      }

      Connect(key);
   }

   private static boolean isLimit() {
      if (time == null) {
         System.err.println("Echonest: Connect to Echonest first");
         throw new RuntimeException();
      }

      double elapsed = time.elapsedTime();
      if (elapsed - lastTime > 60000) {
         lastTime = elapsed;
         requests = 0;
      }

      return requests >= maxRequest;
   }

   public static String getID(String artist) {

      if (!connected) return null;

      String result = null;
      try {
         // TODO: CAN CRASH HERE
         result = Echo.searchArtists(artist).get(0).getID();
         if (result == null) return null;
      } catch (Exception e) {
         e.printStackTrace();
         return null; /* we couldn't get the ID,
                         probably not connected */
      }

      Echonest.requests++;
      return result;
   }

   public static Set<VibArtist> getSimilar(String artist) {

      if (!connected) return null;

      Set<VibArtist> output = new HashSet<VibArtist>();
      List<Artist> results = null;

      // currently at maximum request limit
      if (isLimit()) return null;

      try {
         results = Echo.searchArtists(artist);
      } catch (EchoNestException e) {
         System.err.println("EchoNest: couldn't query Artist " + results);
         return null;
      }

      Echonest.requests++;

      // zero results
      if (results == null) return null;
      if (results.size() == 0) return null;

      Artist root = results.get(0);
      ;

      try {
         for (Artist sim : root.getSimilar(15)) {
            VibArtist tmp = new VibArtist(sim.getName(), sim.getID());
            output.add(tmp);
         }
      } catch (EchoNestException e) {
         System.err.println("EchoNest: couldn't get similar artists ");
         return null;
      }

      return output;
   }

   // api test
   public static void main(String[] args) {
      ConnectFromFile();
      Set<VibArtist> sim = getSimilar("Death Grips");
      for (VibArtist s : sim) {
         System.out.println(s.getName());
         System.out.println(s.getId());
         System.out.println("-------");
      }

   }

   public static boolean isConnected() {
      return connected;
   }
}
