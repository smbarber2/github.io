package assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

/**
 * @author Sarah Barber Assignment 4.
 *
 * This program reads the information from a file and uses the data to create and traverse a digraph.
 * @since March 13, 2023. I have followed the UNCG Academic Integrity policy on
 * this assignment.
 */
public class Assignment4 {      //Class used for the digraph. I didn't want to rename it and risk breaking something

    private int Vertices;               //Variable for number of vertices
    private List<Integer>[] adjacent;   //Adjacency list for graph

    public Assignment4(int Vertice) {       //This is the digraph
        this.Vertices = Vertice;
        adjacent = new ArrayList[Vertice];         //Creates an adjacency list with V
        for (int i = 0; i < Vertice; i++) {
            adjacent[i] = new ArrayList<>(); //Makes each vertex's list of neighbors as an empty list
        }
    }

    /**
     * Method adds the edge between vertexes
     */
    public void addEdge(int a, int b) {
        this.adjacent[a].add(b); //Add a directed edge from vertex a to vertex b
    }


    /**
     * Method prints out the neighbors for the vertices
     */
    public void printNeighbors() {
        for (int i = 0; i < Vertices; i++) { //Iterates through all vertices
            System.out.print(i + ": ");
            for (int j : adjacent[i]) {      //Iterates through all neighbors of the vertex
                System.out.print(j + " ");
            }
            System.out.println();       //Prints out the results
        }
    }

    /**
     * Method tracks vertices to see if the graph has a cycle in it
     */
    public boolean hasCycleTracker() {
        boolean[] visited = new boolean[Vertices];      //Makes an array to keep track of visited vertices
        boolean[] recursiveStack = new boolean[Vertices];     //Makes an array to keep track of vertices in the recursion stack

        for (int i = 0; i < Vertices; i++) {                  //Goes through all vertices
            if (hasCycle(i, visited, recursiveStack)) {   //Checks if there is a cycle starting from vertex i
                return true;
            }
        }
        return false;
    }

    /**
     * Method searches to see if the graph has a cycle in it
     */
    private boolean hasCycle(int b, boolean[] visited, boolean[] recursiveStack) {
        visited[b] = true;          //Marks vertex b as visited
        recursiveStack[b] = true;   //Marks vertex b as being in the recursion stack

        for (int i : adjacent[b]) { //Goes through all neighbors of vertex b
            if (!visited[i] && hasCycle(i, visited, recursiveStack)) {  //If neighbor i is not visited and there is a cycle starting from i, return true
                return true;
            } else if (recursiveStack[i]) { // if neighbor i is already in the recursion stack, there is a cycle, return true
                return true;
            }
        }

        recursiveStack[b] = false; //Makes vertex b as no longer being in the recursion stack
        return false;               //Returns false if there isn't a cycle
    }

    /**
     * Method tracks the vertices to help sort them topologically
     */
    public List<Integer> topologicalSortTracker() {
        boolean[] visited = new boolean[Vertices];      //Make an array to keep track of visited vertices
        List<Integer> result = new ArrayList<>();       //Makes a list to store the topological sort
        for (int i = 0; i < Vertices; i++) {            //Goes through all the vertices
            if (visited[i] == false) { // if vertex i is not visited, perform a DFS to add all its descendants to the result list
                topologicalSort(i, visited, result);
            }
        }
        return result; // return the topological sort
    }

    /**
     * Method goes through the vertices and sorts them topologically
     */
    private void topologicalSort(int a, boolean[] visited, List<Integer> result) {
        visited[a] = true;// Mark the current vertex as visited

        for (int i : adjacent[a]) {
            if (visited[i] == false) {
                topologicalSort(i, visited, result);        //Recursively visit all the neighbors of the current vertex
            }
        }
        //Once all the neighbors have been visited, it adds the current vertex to the front of the list
        result.add(0, a);
    }

    /**
     * Main method reads the file, makes the diagraph, and prints out the
     * results
     */
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("tinydigraph.txt")); //Reads the file for info
            int Vertices = scanner.nextInt();
            int Edges = scanner.nextInt();
            Assignment4 digraph = new Assignment4(Vertices);      //new digraph
            for (int i = 0; i < Edges; i++) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                digraph.addEdge(a, b);                //Adds an edge from vertex a to vertex b
            }
            scanner.close();

            System.out.println("Graph adjacency of the Directed Graph:");   //Prints the adjacency
            digraph.printNeighbors();

            System.out.println("__________________________");
            System.out.println("Does the graph have a cycle?");

            if (digraph.hasCycleTracker()) {
                System.out.println("- The graph has a cycle.");       //If the graph does have a cycle, prints out yes
            } else {
                System.out.println("- The graph does not have a cycle.");     //If the graph doesn't have a cycle, prints out no
                List<Integer> topoSort = digraph.topologicalSortTracker();
                System.out.println("__________________________");
                System.out.println("Topological sort of graph:");
                for (int v : topoSort) {
                    System.out.print(v + " ");      //Prints out the topological sort of the graph
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {//Error in case file isn't found
            e.printStackTrace();
            System.out.println("File not found.");
        }
    }
}
