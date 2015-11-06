package src.event;

import java.util.Queue;
import java.util.LinkedList;

public class EQ {

   private static Queue<VibEvent> queue = new LinkedList<VibEvent>();

   public static void enqueue(VibEvent e) {
      queue.add(e);
   }

   public static VibEvent dequeue() {
      return queue.poll();
   }

   public static VibEvent peek() {
      return queue.peek();
   }

}