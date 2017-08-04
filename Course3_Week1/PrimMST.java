package Course3_Week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class PrimMST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Create a graph of with 500 vertices
		Graph g = new Graph(500);
		
		// Add edges from given input file
		addEdgesFormFile(g);
		
		// Calculate overall cost of minimum spanning tree
		g.primsMST();
	}

	public static void addEdgesFormFile(Graph g) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course3_Module1_Q3_edges.txt"));
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] numbers = line.split(" ");
				g.addEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Node {
	int nodeName;
	int weight;
	Node next;
	boolean visited;

	public Node(int nodeName, int weight) {
		this.nodeName = nodeName;
		this.weight = weight;
		this.visited = false;
	}
}

class AdjList {
	Node head;
	int distance;
	boolean visited;
}

class Graph {
	AdjList[] vertices;
	int length;

	public Graph(int length) {
		this.vertices = new AdjList[length + 1];
		this.length = length;
		for (int iterator = 1; iterator <= length; iterator++) {
			vertices[iterator] = new AdjList();
			vertices[iterator].distance = Integer.MAX_VALUE;
		}
		vertices[1].distance = 0;
	}

	public void addEdge(int start, int end, int weight) {
		Node edge = new Node(end, weight);
		edge.next = vertices[start].head;
		vertices[start].head = edge;

		Node edge2 = new Node(start, weight);
		edge2.next = vertices[end].head;
		vertices[end].head = edge2;
	}

	public void showGraph() {
		Node pointer;
		for (int i = 1; i <= length; i++) {
			pointer = vertices[i].head;
			System.out.print(i);
			while (pointer != null) {
				System.out.print("->" + pointer.nodeName);
				pointer = pointer.next;
			}
			System.out.println();
		}
	}

	public void primsMST() {
		int minWeightV = 0;
		int coverIndex = 0;
		while (coverIndex < length) {
			
			// Pick the vertex which has minimum weight and add mark that vertex as visited
			minWeightV = pickMinWeightVertex();
			vertices[minWeightV].visited = true;
			coverIndex++;
			
			// Update weight of adjacent vertices from minimum weight vertex
			updateAdjacentVertexWeights(minWeightV);
		}
		System.out.println("Minimum weight: " + getMSTDistance());
	}

	public int pickMinWeightVertex() {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 1; i <= length; i++) {
			if (vertices[i].distance <= min && !vertices[i].visited) {
				min = vertices[i].distance;
				minIndex = i;
			}
		}
		return minIndex;
	}

	public int getMSTDistance() {
		int totalDist = 0;
		for (int i = 1; i <= length; i++) {
			totalDist += vertices[i].distance;
		}
		return totalDist;
	}

	public void updateAdjacentVertexWeights(int vertex) {
		Node pointer = vertices[vertex].head;
		while (pointer != null) {
			if (!vertices[pointer.nodeName].visited && pointer.weight <= vertices[pointer.nodeName].distance) {
				vertices[pointer.nodeName].distance = pointer.weight;
			}
			pointer = pointer.next;
		}
	}
}
