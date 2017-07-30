package Course1_Week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class KargerMinCut {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// Create new graph and add edges from file
			Graph g = new Graph(200);
			BufferedReader br = new BufferedReader(new FileReader("Course1_Week4_kargerMinCut.txt"));
			String line = br.readLine();
			String[] splitArray;
			while (line != null) {
				splitArray = line.split("\t");
				for (int i = 1; i < splitArray.length; i++) {
					g.addEdge(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[i]));
				}
				line = br.readLine();
			}
			
			// Find minimum edges cut
			g.kargerMinCut();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Used for testing
		// Graph g = new Graph(4);
		// g.addEdge(1, 3);
		// g.addEdge(1, 2);
		// g.addEdge(2, 3);
		// g.addEdge(2, 4);
		// g.addEdge(3, 4);
		
		// g.showGraph();
		// g.kargerMinCut();
	}
}

class Node {
	int nodeName;
	Node next;

	public Node(int nodeName) {
		this.nodeName = nodeName;
		next = null;
	}
}

class AdjList {
	Node head;
}

class Graph {
	ArrayList<AdjList> vertices;
	HashMap<Integer, Integer> map;
	int length;
	int originalLength;
	int[] indexes;
	int indexLength;

	public Graph(int length) {
		vertices = new ArrayList<AdjList>();
		map = new HashMap<Integer, Integer>();
		indexes = new int[length];
		for (int i = 1; i <= length; i++) {
			vertices.add(new AdjList());
			map.put(i, i - 1);
			indexes[i-1] = i;
		}
		this.length = length;
		originalLength = length;
		indexLength = length;
	}

	public void addEdge(int start, int end) {
		Node n = new Node(end);
		n.next = vertices.get(map.get(start)).head;
		vertices.get(map.get(start)).head = n;

		// Used for own test program
		// Node n2 = new Node(start);
		// n2.next = vertices.get(map.get(end)).head;
		// vertices.get(map.get(end)).head = n2;
	}

	public void showGraph() {
		for (int i = 1; i <= originalLength; i++) {
			if (map.containsKey(i)) {
				Node n = vertices.get(map.get(i)).head;
				System.out.print(i);
				while (n != null) {
					System.out.print("->" + n.nodeName);
					n = n.next;
				}
				System.out.println("");
			}
		}
	}

	public void kargerMinCut() {
		Random r = new Random();
		int mergedNode;
		while (length > 2) {
			int i;
			do {
				i = 1 + r.nextInt(length);
				i = indexes[i-1];
			} while (!map.containsKey(i));

			// Testing
			// i = 2;
			int j = 0;
			j = findNoOfEdge(i);
			j = 1 + r.nextInt(j-1);
			
			// Testing
			// j = 2;
			Node n = vertices.get(map.get(i)).head;
			for (int v = 1; v < j; v++) {
				n = n.next;
			}
			mergedNode = n.nodeName;
			n = vertices.get(map.get(i)).head;
			
			Node n1 = vertices.get(map.get(mergedNode)).head;
			removeEdge(i, mergedNode);
			
			// Merge nodes
			while (n1 != null) {
				Node add = new Node(n1.nodeName);
				add.next = vertices.get(map.get(i)).head;
				vertices.get(map.get(i)).head = add;
				n1 = n1.next;
			}

			// Remove vertex from graph
			removeVertex(mergedNode);
			
			// Remove edges for adjacent vertex of random edge and replace it with i
			removeEdgesForMergedNode(mergedNode, i);
			
			// Remove self loops if there is any
			removeSelfLoop(i);
		}
		System.out.println("Min cut edges: "+findAns());
	}

	public void removeEdgesForMergedNode(int vertex, int replace) {
		for (int i = 1; i <= originalLength; i++) {
			if (map.containsKey(i)) {
				Node n = vertices.get(map.get(i)).head;
				while (n != null) {
					if (n.nodeName == vertex) {
						n.nodeName = replace;
					}
					n = n.next;
				}
			}
		}
	}

	public int findAns() {
		int length = 0;
		for (int i = 0; i <= originalLength; i++) {
			if (map.containsKey(i)) {
				Node n = vertices.get(map.get(i)).head;
				while (n != null) {
					length++;
					n = n.next;
				}
				break;
			}
		}
		return length;
	}

	public void removeVertex(int vertex) {
		map.remove(vertex);
		for(int i = 0;i<indexLength;i++){
			if(indexes[i] == vertex){
				while(i<indexLength-1){
					indexes[i] = indexes[i+1];
					i++;
				}
				break;
			}
		}
		indexLength--;
		length--;
	}
	
	public void showEdges(int vertex){
		if(map.containsKey(vertex)){
			Node n = vertices.get(map.get(vertex)).head;
			while(n!=null){
				System.out.print(n.nodeName + " ");
				n = n.next;
			}
			System.out.println("");
		}
		
	}

	public void removeSelfLoop(int vertex) {
		if(!map.containsKey(vertex))
			return;
		Node n = vertices.get(map.get(vertex)).head, prev;
		if (n != null) {
			while (n.nodeName == vertex && n != null) {
				n = vertices.get(map.get(vertex)).head.next;
				vertices.get(map.get(vertex)).head = vertices.get(map.get(vertex)).head.next;
			}

			prev = vertices.get(map.get(vertex)).head;
			n = prev.next;
			while (n != null) {
				if (n.nodeName == vertex) {
					prev.next = n.next;
					if(n.next!=null){
						n = n.next.next;
					}
					else{
						n = null;
					}
						
				} else {
					prev = n;
					n = n.next;
				}
			}
		}
		
	}

	public int findNoOfEdge(int i) {
		int length = 0;
		Node n = vertices.get(map.get(i)).head;
		while (n != null) {
			length++;
			n = n.next;
		}
		return length;
	}

	public void removeEdge(int start, int end) {
		Node n = vertices.get(map.get(start)).head;
		if (n == null)
			return;
		else if (n.nodeName == end) {
			n = n.next;
		} else {
			Node prev = n;
			n = n.next;
			while (n.nodeName != end) {
				prev = n;
				n = n.next;
			}
			prev.next = n.next;
		}
	}
}
