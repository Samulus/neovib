package src.primitives;

import src.scene.Scene;

public class Line extends AbstractShape {

    public Line() {
        super(0.0f, 0.0f);
    }

    public void render() {
        Scene.p.pushMatrix();
        Scene.p.translate(0, Scene.p.height / 2f, 20);
        Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.width + Scene.p.random(super.vibrate), Scene.p.random(super.vibrate));
        Scene.p.popMatrix();
    }

}