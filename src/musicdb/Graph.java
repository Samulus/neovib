/*
    Graph.java

    This is a simple unweighted and undirected graph module I wrote to aid in creating
    and traversing a graph of similar related artists in the project. Right now it only
    supports performing two methods, bfs (returns a hashmap of artists with a unique set of
    artists), and accessible (returns a spanning tree of all the nodes acessible from the provided)
*/

package src.musicdb;

import java.io.*;
import java.util.*;

public class Graph<T> implements Serializable {

   private HashMap<T, Set<T>> nodes;
   private boolean loaded = false;

   public Graph() {
      nodes = new HashMap<T, Set<T>>();
   }

   public static void Serialize(Graph g, String fpath) {

      File file = null;

      /* Open 4 Writing */
      try {
         file = new File(fpath);
      } catch (Exception e) {
         e.printStackTrace();
      }

      /* Serialize */
      try {
         FileOutputStream fout = new FileOutputStream(file);
         ObjectOutputStream oout = new ObjectOutputStream(fout);
         oout.writeObject(g);
         oout.close();
         fout.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public static Graph Deserialize(String fpath) {

      File file = null;
      Graph output = null;

      try {
         file = new File(fpath);
      } catch (Exception e) {
         e.printStackTrace();
      }

      if (file.exists() && file.isFile()) {
         try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            output = (Graph) oin.readObject();
            fin.close();
            oin.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      return output;
   }

   public static void main(String[] args) {

      Graph<String> graph = new Graph<String>();

      /*
      graph.addNode("MF DOOM");
      graph.addNode("Percee P");
      graph.addNode("Madlib");
      graph.addNode("Oh No");
      graph.addNode("Georgia Anne Muldrow");
      graph.addNode("Quasimoto");
      graph.addNode("Death Grips");
      graph.addNode("Flying Lotus");
      graph.addNode("Erykah Badu");
      graph.addEdge("MF DOOM", "Percee P");
      graph.addEdge("MF DOOM", "Madlib");
      graph.addEdge("MF DOOM", "Quasimoto");
      graph.addEdge("MF DOOM", "Oh No");
      graph.addEdge("MF DOOM", "Flying Lotus");
      graph.addEdge("Madlib", "Quasimoto");
      System.out.println("# BFS Test");
      HashMap<String, LinkedList<String>> m = graph.bfs("MF DOOM");
      System.out.println(m);
      System.out.println("\n# Accessible Test");
      System.out.println("From MF DOOM you can access:");
      System.out.println(graph.accessible("MF DOOM"));
      System.out.println("\n[OK ✓] Graph.java");
      */

      //Graph.Serialize(graph, "db/test.db");

      Graph<String> g = Graph.Deserialize("db/test.db");
      System.out.println(g.accessible("MF DOOM"));
   }

   public boolean addNode(T element) {
      if (nodes.get(element) != null) return false;
      nodes.put(element, new TreeSet<T>());
      return true;
   }

   public void addEdges(T artist, Set<T> s) {
      if (nodes.get(artist) == null) addNode(artist);
      for (T elem : s) addEdge(artist, elem);
   }

   public boolean addEdge(T a, T b) {
      if (nodes.get(a) == null || nodes.get(b) == null) return false;
      nodes.get(a).add(b);
      nodes.get(b).add(a);
      return true;
   }

   public Set<T> getNode(T query) {
      Set<T> result = nodes.get(query);
      if (result == null) return null;
      return result;
   }

   public Set<T> getNodes() {
      return nodes.keySet();
   }

   /*
      Static IO Serialize / Deserializers
    */

   public boolean hasNode(T node) {
      return (nodes.get(node) != null);
   }

   public HashSet<T> accessible(T start) {

      if (nodes.get(start) == null) return null;

      // housekeeping
      HashSet<T> set = new HashSet<T>();
      HashMap<T, LinkedList<T>> results = bfs(start);
      Queue<T> vq = new LinkedList<T>();
      HashMap<T, Boolean> visited = new HashMap<T, Boolean>();

      // start with source
      vq.offer(start);
      visited.put(start, true);

      T vertex = null;
      while (!vq.isEmpty()) {

         vertex = vq.poll();

         for (T adj : nodes.get(vertex)) {

            if (visited.get(adj) == null || !visited.get(adj)) {
               visited.put(adj, true);
               if (set.contains(adj) == false) set.add(adj);
               vq.offer(adj);
            }

         }
      }

      return set;
   }

   public HashMap<T, LinkedList<T>> bfs(T start) {

      if (nodes.get(start) == null) return null;

      HashMap<T, LinkedList<T>> paths = new HashMap<T, LinkedList<T>>();
      Queue<T> vq = new LinkedList<T>();

      HashMap<T, Boolean> visited = new HashMap<T, Boolean>();
      for (Map.Entry<T, Set<T>> entry : nodes.entrySet()) {
         visited.put(entry.getKey(), false);
      }

      vq.offer(start);
      visited.put(start, true);

      T vertex = null;
      while (!vq.isEmpty()) {

         vertex = vq.poll();

         for (T adj : nodes.get(vertex)) {

            if (paths.get(vertex) == null)
               paths.put(vertex, new LinkedList<T>());

            paths.get(vertex).offer(adj);

            if (visited.get(adj) == null || !visited.get(adj)) {
               visited.put(adj, true);
               vq.offer(adj);
            }

         }
      }

      return paths;
   }

}
