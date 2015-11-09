package src.util;

import java.util.ArrayList;

public class Util {

   public static float[] stringToFloat(String[] arr) {
      float[] output = new float[arr.length];
      for (int i = 0; i < arr.length; ++i)
         output[i] = Float.parseFloat(arr[i]);
      return output;
   }

   public static ArrayList<String> toArrayList(String[] arr) {

      ArrayList<String> output = new ArrayList<String>();
      for (int i = 0; i < arr.length; ++i) {
         output.add(arr[i]);
      }

      return output;
   }
}
