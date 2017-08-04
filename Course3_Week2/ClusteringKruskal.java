package Course3_Week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class ClusteringKruskal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph g = new Graph(500);
		// g.addEdge(1, 2, 4);
		// g.addEdge(1, 8, 8);
		// g.addEdge(2, 3, 8);
		// g.addEdge(2, 8, 11);
		// g.addEdge(8, 7, 1);
		// g.addEdge(8, 9, 7);
		// g.addEdge(3, 9, 2);
		// g.addEdge(3, 4, 7);
		// g.addEdge(3, 6, 4);
		// g.addEdge(9, 7, 6);
		// g.addEdge(7, 6, 2);
		// g.addEdge(6, 4, 14);
		// g.addEdge(6, 5, 10);
		// g.addEdge(4, 5, 9);
		// g.showGraph();
		addEdgesFromFile(g);
		// System.out.println(g.findCycle(g));
		g.kruskalsMST();

	}

	public static void addEdgesFromFile(Graph g) {
		int start;
		String[] splitArr = new String[2];
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course3_Week2_Q1_clustering1.txt"));
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
	long weight;
	Node next;
	boolean visited;

	public Node(int nodeName, long weight) {
		this.nodeName = nodeName;
		this.weight = weight;
		this.visited = false;
	}
}

class AdjList {
	Node head;
	long distance;
}

class Graph {
	AdjList[] vertices;
	int length;
	HashMap<Integer, Integer> vMap;
	HashMap<Integer, Integer> wMap;

	public Graph(int length) {
		this.vertices = new AdjList[length + 1];
		this.length = length;
		for (int iterator = 1; iterator <= length; iterator++) {
			vertices[iterator] = new AdjList();
			vertices[iterator].distance = 1000000;
		}
		vMap = new HashMap<Integer, Integer>();
		wMap = new HashMap<Integer, Integer>();
		vertices[1].distance = 0;
	}

	public void addEdge(int start, int end, int weight) {
		Node edge = new Node(end, weight);
		edge.next = vertices[start].head;
		vertices[start].head = edge;
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

	public void showMinDist() {
		for (int i = 1; i <= length; i++) {
			System.out.println("Weight from 1 to " + i + " is " + vertices[i].distance);
		}
	}

	public void kruskalsMST() {
		Graph G = new Graph(length);
		while (getDistinctNoOfParents(G) != 4) {

			// find minimum distance edge
			int min = Integer.MAX_VALUE;
			int start = 0, end = 0;
			for (int i = 1; i <= length; i++) {
				Node pointer = vertices[i].head;
				while (pointer != null) {
					if (!pointer.visited && pointer.weight < min) {
						min = (int) pointer.weight;
						start = i;
						end = pointer.nodeName;
					}
					pointer = pointer.next;
				}
			}
			Node pointer = vertices[start].head;
			while (pointer != null) {
				if (pointer.nodeName == end) {
					pointer.visited = true;
					break;
				}
				pointer = pointer.next;
			}
			G.addEdge(start, end, min);

			if (findCycle(G)) {
				G.removeEdge(start, end);
			}
		}
		G.showGraph();
		findMaximumSpacedCluster(G);
	}

	public void removeEdge(int start, int end) {
		Node pointer = vertices[start].head, prevNode;
		if (pointer == null) {
			return;
		} else if (pointer.nodeName == end) {
			vertices[start].head = vertices[start].head.next;
			return;
		} else {
			prevNode = pointer;
			pointer = pointer.next;
			while (pointer != null) {
				if (pointer.nodeName == end) {
					prevNode.next = pointer.next;
					return;
				}
				prevNode = pointer;
				pointer = pointer.next;
			}
		}
	}

	// Find parent for union-find data structure
	public int find(int[] parent, int vertex) {
		if (parent[vertex] == -1) {
			return vertex;
		}
		return find(parent, parent[vertex]);
	}

	// Union
	public void union(int[] parent, int src, int dest) {
		int parentSrc = find(parent, src);
		int parentDest = find(parent, dest);

		parent[parentSrc] = parentDest;
	}

	public boolean findCycle(Graph g) {
		Scanner sc = new Scanner(System.in);
		int[] parent = new int[g.length + 1];
		for (int i = 1; i <= length; i++) {
			parent[i] = -1;
		}

		for (int i = 1; i <= length; i++) {
			Node pointer = g.vertices[i].head;
			while (pointer != null) {

				int x = find(parent, i);
				int y = find(parent, pointer.nodeName);

				if (x == y && x != -1) {
					return true;
				}

				union(parent, x, y);

				pointer = pointer.next;
			}
		}
		return false;
	}

	public int getDistinctNoOfParents(Graph g) {
		int count = 0;
		int[] parent = new int[g.length + 1];
		for (int i = 1; i <= length; i++) {
			parent[i] = -1;
		}
		for (int i = 1; i <= length; i++) {
			Node pointer = g.vertices[i].head;
			while (pointer != null) {

				int x = find(parent, i);
				int y = find(parent, pointer.nodeName);

				union(parent, x, y);

				pointer = pointer.next;
			}
		}

		for (int i = 0; i < parent.length; i++) {
			if (parent[i] == -1)
				count++;
		}
		return count;
	}

	public void findMaximumSpacedCluster(Graph g) {
		int count = 0;
		int[] parent = new int[g.length + 1];
		int fourP[] = new int[4];
		int parentIndex = 0;
		for (int i = 1; i <= length; i++) {
			parent[i] = -1;
		}
		for (int i = 1; i <= length; i++) {
			Node pointer = g.vertices[i].head;
			while (pointer != null) {

				int x = find(parent, i);
				int y = find(parent, pointer.nodeName);

				union(parent, x, y);

				pointer = pointer.next;
			}
		}

		for (int i = 0; i < parent.length; i++) {
			if (parent[i] == -1) {
				System.out.println("Cluster Parents:" + (i + 1));
				fourP[parentIndex++] = i + 1;
			}
		}

	}
}
