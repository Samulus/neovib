/*
    VibEvent.java
    -------------

    VibEvent is the main module responsible for handling
    all user input in the application. It consists of several
    ranges of enumerations that the rest of the program should use
    to keep track of events / trigger events. It's sister module EQ.java
    is a similar module that all other modules should pump events through
    to modify the game.

    All input integer enums 0-50 are reserved for system input. This
    way the Input.java module can simply generate an array of booleans
    to keep track of the state of each button. I could use like hashing
    or something but that's unnecessary overhead.

 */

package event;

public enum VibEvent {

   ERROR_CODE(-1),

   /* User Input (0-100) */
   INPUT_UP(0),
   INPUT_DOWN(1),
   INPUT_LEFT(2),
   INPUT_RIGHT(3),

   INPUT_ACCEPT(4),
   INPUT_PREVIOUS(5),
   INPUT_CANCEL(6),
   INPUT_MARK_DIR(7),

   INPUT_DODGE_CROSS(8, "Cross"),
   INPUT_DODGE_SQUARE(9, "Square"),
   INPUT_DODGE_CIRCLE(10, "Circle"),
   INPUT_DODGE_TRIANGLE(11, "Triangle"),

   INPUT_LENGTH(12), // how large

   /* Scenes (100-150) */
   SCENE_TITLE(100, "Title"),
   SCENE_GAME(101, "Game"),
   SCENE_SOUND(102, "Sound"),
   SCENE_BROWSER(103, "Browser"),
   SCENE_PAUSE(104, "Pause");

   private final int code;
   private String str;

   VibEvent(int code, String str) {
      this.code = code;
      this.str = str;
   }

   VibEvent(int code) {
      this.code = code;
      this.str = "";
   }

   public boolean isScene() {
      return (this.code >= 100 && this.code <= 105);
   }

   public boolean isNavigate() {
      return (this.code >= 0 && this.code <= 12);
   }

   public int getCode() {
      return code;
   }

   public String getStr() {
      return this.str;
   }
}