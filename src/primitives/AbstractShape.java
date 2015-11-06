package src.primitives;

public abstract class AbstractShape {

    protected float dst;
    protected float speed;
    protected float vibrate;

    public AbstractShape(float dst, float speed) {
        this.dst = dst;
        this.vibrate = 0.0f;
        this.speed = speed;
    }

    public void advance() {
        this.dst -= this.speed;
    }

    public float getDistance() {
        return this.dst;
    }

    public float getVibrate() {
        return this.vibrate;
    }

    public void setVibrate(float vibrate) {
        this.vibrate = vibrate;
    }

    public abstract void render();

}
