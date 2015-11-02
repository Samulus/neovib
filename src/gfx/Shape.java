package src.gfx;
import processing.core.PConstants;
import src.scene.Scene;

// TODO: linewrap long methods in Shape.java

public class Shape {
   
   public class Triangle extends AbstractShape {

      public Triangle(float dst, float maxDistX) {
        super(dst, maxDistX);
      }
      

      public void render() {
        Scene.p.pushMatrix();
        Scene.p.translate(super.dst, Scene.p.height/2f - 60, 20);
        Scene.p.triangle(Scene.p.random(super.vibrate),       // x1
                         60 + Scene.p.random(super.vibrate),  // y1
                         30 + Scene.p.random(super.vibrate),  // x2
                         0f,                                  // y2
                         60 + Scene.p.random(super.vibrate),  // x3
                         60 + Scene.p.random(super.vibrate)); // y3
        Scene.p.popMatrix();
      }
   }
   
   public class Square extends AbstractShape {

      public Square(float dst, float maxDistX) {
        super(dst, maxDistX);
      }

      public void render() {
        Scene.p.pushMatrix();
        Scene.p.translate(dst, Scene.p.height/2f - 60, 20);
       
        Scene.p.line(0  + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 0  + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate));
        Scene.p.line(0  + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate),  0 + Scene.p.random(super.vibrate));
        Scene.p.line(60 + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate));
        Scene.p.popMatrix();
      }

    }
   
   public class Cross extends AbstractShape {

      public Cross(float dst, float maxDistX) {
        super(dst, maxDistX);
      }

      public void render() {
        Scene.p.pushMatrix();
        Scene.p.translate(dst, Scene.p.height/2f - 60, 20);
        Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.random(super.vibrate) + 60, Scene.p.random(super.vibrate) + 60);
        Scene.p.line(Scene.p.random(super.vibrate) + 60, Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.random(super.vibrate) + 60);
        Scene.p.popMatrix();
      }
    }
   
   public class Circle extends AbstractShape {

      public Circle(float dst, float maxDistX) {
        super(dst, maxDistX);
      }

      public void render() {
        Scene.p.ellipseMode(PConstants.CORNER);
        Scene.p.pushMatrix();
        Scene.p.translate(dst, Scene.p.height/2f - 60, 20);
        Scene.p.ellipse(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), 60, 60);
        Scene.p.popMatrix();
      }

    }
   
   public class Track extends AbstractShape {
      
      public Track() {
         super(0.0f, 0.0f);
      }

      public void render() {
        Scene.p.pushMatrix();
        Scene.p.translate(0, Scene.p.height/2f, 20);
        Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.width + Scene.p.random(super.vibrate), Scene.p.random(super.vibrate));
        Scene.p.popMatrix();
      }

    }


}
