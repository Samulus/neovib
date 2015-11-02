package src.util;

public class Util {
   public static float[] stringToFloat(String[] arr) {
      float[] output = new float[arr.length];
      for (int i=0; i < arr.length; ++i)
        output[i] = Float.parseFloat(arr[i]);
      return output;
    }
}
