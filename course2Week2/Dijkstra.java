package course2Week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Dijkstra {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph(200);
		addEdgesFormFile(g);
		g.dijkstra();
		g.showMinDist();
	}
	
	public static void addEdgesFormFile(Graph g){
		int start;
		String[] splitArr = new String[2];
		try{
			BufferedReader br = new BufferedReader(new FileReader("Course2_Module2_dijkstraData.txt"));
			String line = br.readLine();
			while(line != null){
				String[] numbers = line.split("\t");
				start = Integer.parseInt(numbers[0]);
				for(int i = 1;i<numbers.length;i++){
					splitArr = numbers[i].split(",");
					g.addEdge(start, Integer.parseInt(splitArr[0]), Integer.parseInt(splitArr[1]));
				}
				
				line = br.readLine();
			}
			br.close();
		}
		catch(Exception e){
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
	
	public void showMinDist(){
		for(int i = 1; i <= length;i++){
			System.out.println("Weight from 1 to " + i + " is "+vertices[i].distance);
		}
	}
	int v[];
	int w[];
	int vLength = 0, wLength = 0;
	public void dijkstra() {
		
		v = new int[length];
		w = new int[length];
		v[vLength++] = 1;
		vMap.put(1, 1);
		for (int i = 2; i <= length; i++) {
			wMap.put(i, 1);
			w[wLength++] = i;
		}
		while (wLength > 0) {
			addFromWtoV();
		}
	}
	public void addFromWtoV() {
		int start = 0,end = 0;
		long minWeight = Long.MAX_VALUE;
		Node pointer;
		
		// find minimum weight edge from v to w
		for (int i = 0; i < vLength; i++) {
			pointer = vertices[v[i]].head;
			while(pointer!=null){
				if(!pointer.visited && pointer.weight < minWeight && !vMap.containsKey(pointer.nodeName) ){
					minWeight = pointer.weight;
					start = v[i];
					end = pointer.nodeName;
				}
				pointer = pointer.next;
			}
		}
		// mark edge visited
		pointer = vertices[start].head;
		if(vertices[end].distance > vertices[start].distance + minWeight){
			vertices[end].distance = vertices[start].distance + minWeight;
		}
		while(pointer!=null){
			if(pointer.nodeName == end && pointer.weight == minWeight){
				pointer.visited = true;
			}
			pointer = pointer.next;
		}
		// add in v;
		if(!vMap.containsKey(end)){
			v[vLength++] = end;
			vMap.put(end, 1);
		}
		
		// calculate distance from newly added vertex
		pointer = vertices[end].head;
		while(pointer!=null){
			if( vertices[end].distance+pointer.weight < vertices[pointer.nodeName].distance){
				vertices[pointer.nodeName].distance = vertices[end].distance + pointer.weight;
			}
			pointer = pointer.next;
		}
		
		// remove edge from w		
		for(int i = 0;i<wLength;i++){
			if(w[i] == end){
				while(i<wLength-1){
					w[i] = w[i+1];
					i++;
				}
				wLength--;
				break;
			}
		}
	}
}