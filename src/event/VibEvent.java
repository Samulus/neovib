package src.event;

public enum VibEvent {
   
   ERROR_CODE(-1),
   
   /* Scenes */
   SCENE_TITLE(0),
   SCENE_GAME(1),
   SCENE_SETTINGS(2),
   SCENE_BROWSER(3),
   SCENE_AUDIO_LAG(4),
   SCENE_VIDEO_LAG(5),
   SCENE_DEBUG(6),
   
   /* Input */
   INPUT_UP(10),
   INPUT_DOWN(11),
   INPUT_LEFT(12),
   INPUT_RIGHT(13),
   INPUT_ACCEPT(14),
   INPUT_CANCEL(15);
   
   private final int code;
   
   private VibEvent(int code) {
      this.code = code;
   }
   
   public int get() {
      return code;
   }
   
}