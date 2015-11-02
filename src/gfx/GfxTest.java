package src.gfx;

import processing.core.PApplet;
import src.scene.Scene;

public class GfxTest extends PApplet {

      
      Square square;
      Circle circle;
      Triangle triangle;
      Cross cross;
      Line line;
      boolean toggle = false;
      
      float speed; 
      float vibrate;

      public void settings() {
         size(1280, 720, P3D);

      }

      public void setup() {
         Scene.setContext(this); /* always set context */
         background(0);
         noFill();
         stroke(255);
         square = new Square(width/2, 30);
         circle = new Circle(width/2 + 64, 60);
         triangle = new Triangle(width/2f + 120, 120);
         cross = new Cross(width/2 + 180, 180);
         line = new Line(); /* always renders center of screen */
      }

      public void draw() {
         background(0);
         square.render();
         triangle.render();
         cross.render();
         circle.render();
         line.render();
         
         // should probably use an array
         square.advance(speed);
         triangle.advance(speed);
         cross.advance(speed);
         circle.advance(speed);
      }
      
      public void toggleV(int vibrate) {
         square.setVibrate(vibrate);
         circle.setVibrate(vibrate);
         triangle.setVibrate(vibrate);
         cross.setVibrate(vibrate);
         line.setVibrate(vibrate);
      }
      
      public void keyPressed() {
         
         if (key == 'k') speed++;
         if (key == 'j') speed--;
         
         if (key == 't' && !toggle) {
            println("Toggle On");
            toggleV(20);         
            toggle = true;
         }
         
         else if (key == 't' && toggle) {
            println("Toggle Off");
            toggle = false;
            toggleV(0);

         }
         
      }

   
   public static void main(String[] args) {
      PApplet.main(GfxTest.class.getName());
   }
}

// PApplet.main(Test.class.getName());
