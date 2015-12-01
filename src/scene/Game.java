package src.scene;

import src.audio.Audio;
import src.audio.BeatKonducta;
import src.audio.Detector;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.primitives.AbstractShape;
import src.primitives.Player;
import src.primitives.Track;

import java.io.File;
import java.util.LinkedList;

public class Game extends AbstractScene {

    /* Audio */
    public Audio audio;
    public LinkedList<Double> beats;
    public BeatKonducta konducta;
    /* Graphics */
    Player player = new Player();
    Track track = new Track();
    LinkedList<AbstractShape> hit;
    private File song;

    public Game() {
        player = new Player();
        track = new Track();
        hit = new LinkedList<AbstractShape>();
    }

    public void pass(Object songPath) {
        this.song = (File) songPath;
    }

    public void setup() {

        String fpath = "";

        /* Load Browser Song */
        try {
            fpath = this.song.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Game.java: unable to open file " + this.song.getPath());
        }

        audio = new Audio(fpath);
        beats = Detector.load(new File(fpath));

        if (beats.size() < 5) {
            EQ.enqueue(VibEvent.SCENE_BROWSER);
            return;
        }

        konducta = new BeatKonducta(audio, beats);
        audio.play();

    }

    public void render() {
        Scene.p.background(255);
        Scene.p.stroke(0);
        Scene.p.strokeWeight(3);

        /* Onscreen Elements */
        player.render();
        track.render(konducta.getList());

        /* Debug */
        Scene.p.pushMatrix();
        Scene.p.translate(Scene.p.width / 1.5f, Scene.p.height / 16f);


        double pos = audio.getPosition() / 1000;
        Scene.p.popMatrix();

        // remove hit obstacles
        if (!konducta.getList().isEmpty()) {
            LinkedList<AbstractShape> q = konducta.getList();
            AbstractShape s = q.peekFirst();
            if (s.getDistance() <= Scene.p.width / 7) {
                hit.add(s);
                s.setState("hit");
                for (AbstractShape repeat : konducta.getList()) repeat.setVibrate(1);
                s.setVibrate(50);
                q.pollFirst();
                track.setVibrate(20);
                konducta.impeding.poll();
            }
        }

        for (AbstractShape s : hit) {
            // TODO: modifying something we're iterating over = bad
            if (s.getDistance() <= -60) hit.pollFirst();
            else s.render();
        }

        track.setVibrate(Scene.p.constrain(track.getVibrate() * 0.95f, 1, 20));

        for (AbstractShape s : konducta.getList()) {
            s.render();
        }
    }

    public void logic() {

        if (Input.HAPPENING && !konducta.impeding.isEmpty()) {

            double pos = audio.getPosition() / 1000;
            double btime = konducta.impeding.peek();
            double delta = btime - pos;

            System.out.println("Hit @ " + delta);


        }

        konducta.deleteOld();
        konducta.nextReady();

        for (AbstractShape s : konducta.getList()) {
            player.logic(s.getDistance());
            s.advance();
        }

        for (AbstractShape s : hit) {
            s.advance();
            s.setVibrate(Scene.p.constrain(s.getVibrate() * 0.95f, 1, 20));
        }

    }

    public void input(VibEvent event) {

        if (event == VibEvent.INPUT_PREVIOUS) {
            audio.stop();
            Scene.pass(VibEvent.SCENE_SIMILAR, this.audio.getArtist());
            Scene.focus(VibEvent.SCENE_PAUSE);
        }
    }


}
