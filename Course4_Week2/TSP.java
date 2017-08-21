package Course4_Week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TSP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		float[][] inputArray = readFile();
		TSP tsp = new TSP();
		ArrayList<Float> output = tsp.calculateDist(inputArray, inputArray.length);
		for(int i = 0;i<output.size();i++){
			System.out.println(output.get(i));
		}
	}
	
	public static float[][] readFile(){
		float points[][] = null;
		int c = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course4_Week2_tsp.txt"));
			String line = br.readLine();
			int length = Integer.parseInt(line);
			line = br.readLine();
			String[] splitted;
			points = new float[length][2];
			int index = 0;
			while (line != null && c<=10) {
				splitted = line.split(" ");
				points[index][0] = Float.parseFloat(splitted[0]);
				points[index++][1] = Float.parseFloat(splitted[1]);
				line = br.readLine();
				c++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return points;
	}
	
	private ArrayList<Float> outputArray = new ArrayList<Float>();
	private float arr1[][], arr2[][], arr3[][];
	int cols, N;
	

	public ArrayList<Float> calculateDist(float[][] points, int len) {
		
		N = len;
		cols = (int) Math.pow(2, len);
		arr1 = new float[len][cols];
		arr2 = new float[len][cols];
		arr3 = points;
		int i, j;
		for (i = 0; i < len; i++) {
			for (j = 0; j < cols; j++) {
				arr1[i][j] = -1;
				arr2[i][j] = -1;
			}
		}
		
		for (i = 0; i < len; i++) {
			arr1[i][0] = points[i][0];
		}
		float res = tsp(0, cols - 2);
		outputArray.add(0f);
		getPath(0, cols - 2);
		outputArray.add(res);
		
		return outputArray;
	}

	private float tsp(int start, int set) {
		int masked, mask;
		float temp, result = -1;
		if (arr1[start][set] != -1) {
			return arr1[start][set];
		} else {
			for (int x = 0; x < N; x++) {
				mask = cols - 1 - (int) Math.pow(2, x);
				masked = set & mask;
				if (masked != set) {
					temp = arr3[start][x] + tsp(x, masked);
					if (result == -1 || result > temp) {
						result = temp;
						arr2[start][set] = x;
					}
				}
			}
			arr1[start][set] = result;
			return result;
		}
	}
	
	public static float calculateEuclideanDistance(float[][] points, int point1Index, int point2Index) {

		float a, b;
		a = (float) Math.pow(points[point1Index][0] - points[point2Index][0], 2);
		b = (float) Math.pow(points[point1Index][1] - points[point2Index][1], 2);

		return (float) Math.sqrt(a + b);
	}

	private void getPath(int start, int set) {
		if (arr2[start][set] == -1) {
			return;
		}
		int x = (int) arr2[start][set];
		int mask = cols - 1 - (int) Math.pow(2, x);
		int masked = set & mask;
		outputArray.add((float) x);
		getPath(x, masked);
	}


	public static Graph createGraphFromFile() {
		Graph g = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course4_Week2_tsp.txt"));
			String line = br.readLine();
			int length = Integer.parseInt(line);
			g = new Graph(length);
			line = br.readLine();
			String[] splitted;
			float points[][] = new float[length][2];
			int index = 0;
			while (line != null) {
				splitted = line.split(" ");
				points[index][0] = Float.parseFloat(splitted[0]);
				points[index++][1] = Float.parseFloat(splitted[1]);
				line = br.readLine();
			}
			float distance = 0;
			for (int i = 0; i < length - 1; i++) {
				for (int j = i + 1; j < length; j++) {
					distance = calculateEuclideanDistance(points, i, j);
					g.addEdge(i + 1, j + 1, distance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}

	
}

class Node {
	int nodeName;
	float weight;
	Node next;
	boolean visited;

	public Node(int nodeName, float distance) {
		this.nodeName = nodeName;
		this.weight = distance;
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

	public void addEdge(int start, int end, float distance) {
		Node edge = new Node(end, distance);
		edge.next = vertices[start].head;
		vertices[start].head = edge;

	}

	public void showGraph() {
		Node pointer;
		for (int i = 1; i <= length; i++) {
			pointer = vertices[i].head;
			System.out.print(i);
			while (pointer != null) {
				System.out.print("->" + pointer.nodeName + "(" + pointer.weight + ")");
				pointer = pointer.next;
			}
			System.out.println();
		}
	}
}