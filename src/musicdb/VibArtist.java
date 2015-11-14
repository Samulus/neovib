package src.musicdb;

import java.io.Serializable;
import java.util.ArrayList;

public class VibArtist implements Serializable {

   /* Root Artist */
   private String name;
   private String id;

   /* Similar Artists */
   private ArrayList<String> similar;
   private ArrayList<String> simids;

   public VibArtist() {
      name = "";
      similar = new ArrayList<String>();
      id = "";
      simids = new ArrayList<String>();
   }

   /* Getters */
   public String getName() {
      return name;
   }

   /* Setters */
   public void setName(String name) {
      this.name = name;
   }

   public ArrayList<String> getSimilar() {
      return similar;
   }

   public void setSimilar(ArrayList<String> similar) {
      this.similar = similar;
   }

   public ArrayList<String> getSimID() {
      return simids;
   }

   public void setID(String id) {
      this.id = id;
   }

   public void setSimids(ArrayList<String> ids) {
      this.simids = ids;
   }

   /* Add */
   public void addSimilar(String s) {
      this.similar.add(s);
   }

   public void addSimID(String id) {
      this.simids.add(id);
   }


}
