/*
    # VibArtist.java

    VibArtist is a small abstract data structure used in this package
    to keep track of a mutual pairing of an Artist and their key ID in the
    Echonest database. I would use a struct but this isn't C.

 */

package src.musicdb;

import java.io.Serializable;

public class VibArtist implements Serializable {

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

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "Artist: " + name + "\nID: " + id;
    }

}
