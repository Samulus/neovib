/*
    VibArtist.java
    --------------

    VibArtist is a small abstract data structure used in this package
    to keep track of a mutual pairing of an Artist and their key ID in the
    Echonest database. I would use a struct but this isn't C.

 */

package musicdb;

import java.io.Serializable;

public class VibArtist implements Serializable, Comparable {

   private static final long serialVersionUID = 1; // only version ;)
   private String name;
   private String id;

   public VibArtist(String name, String id) {
      this.name = name;
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getId() {
      return id;
   }

   public void setID(String id) {
      this.id = id;
   }

   public String toString() {
      return "Artist: " + name + " | ID: " + id;
   }

   @Override
   public boolean equals(Object artist) {
      if (!(artist instanceof VibArtist)) return false;

      VibArtist other = (VibArtist) artist;
      return (this.name.equals(other.name) || this.id.equals(other.id));
   }

   @Override
   public int compareTo(Object artist) {
      if (!(artist instanceof VibArtist)) return -1;
      VibArtist other = (VibArtist) artist;
      return (other.getName().compareTo(this.getName()));
   }

   @Override
   public int hashCode() {
      return this.name.hashCode();
   }

}
