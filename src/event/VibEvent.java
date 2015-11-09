package src.event;

public enum VibEvent {

   ERROR_CODE(-1),

   /* Scenes (0-99) */
   SCENE_TITLE(0, "Title"),
   SCENE_GAME(1, "Game"),
   SCENE_SETTINGS(2, "Settings"),
   SCENE_BROWSER(3, "Browser"),
   SCENE_AUDIO_LAG(4, "Audio Lag"),
   SCENE_VIDEO_LAG(5, "Video Lag"),

   /* Navigation (100-199) */
   INPUT_UP(100),
   INPUT_DOWN(101),
   INPUT_LEFT(102),
   INPUT_RIGHT(103),

   /* Traversal (200-299) */
   INPUT_ACCEPT(200),
   INPUT_PREVIOUS(201),

   /* Camera (300 - 399) */
   /*
   CAMERA_PAN_FORWARD(300),
   CAMERA_PAN_BACKWARD(301),
   CAMERA_PAN_LEFT(302),
   CAMERA_PAN_RIGHT(303),
   CAMERA_UP()
   */

   /* Game Actions (400 - 499)*/
   PLAYER_DODGE(400);

   private final int code;
   private String str = "";

   VibEvent(int code, String str) {
      this.code = code;
      this.str = str;
   }

   VibEvent(int code) {
      this.code = code;
      this.str = "";
   }

   public boolean isScene() {
      return (this.code >= 0 && this.code <= 99);
   }

   public boolean isNavigate() {
      return (this.code >= 100 && this.code <= 199);
   }

   public boolean isTraversal() {
      return (this.code >= 200 && this.code <= 299);
   }

   public boolean isPlayer() {
      return (this.code >= 400 && this.code <= 499);
   }

   public int getCode() {
      return code;
   }

   public String getStr() {
      return this.str;
   }


}