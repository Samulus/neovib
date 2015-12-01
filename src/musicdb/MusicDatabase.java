package src.musicdb;

import java.io.File;
import java.util.Set;

public class MusicDatabase {

    public static File currentSong;
    public static String currentArtist;
    public static Set<String> similarArtists;
    //private ArrayList<String> db
    public static String libraryPath = "(not set)";

    /*
    public MusicDatabase(String fpath) {
        Collection<File> paths = Util.listFileTree(new File(fpath));
        db = new ArrayList<String>();
        for (File f : paths) {
            db.add(f.toString());
        }
        Collections.sort(db);
    }

    public static void main(String[] args) {

        Clock c = new Clock();

        String query = "Death Grips";
        MusicDatabase mdb = new MusicDatabase("/home/sam/music/");

        if (mdb == null) return;

        for (String s : mdb.find(query)) {
            System.out.println(s);
        }

        System.out.println(c.elapsedTime()); // 3-4s initially, 500ms after cacheing

    }
    */

}
