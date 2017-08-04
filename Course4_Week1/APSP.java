package Course4_Week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;

public class APSP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Test with smaller graph
		
//		Graph g = new Graph(5);
//		g.addEdge(1, 2, 2);
//		g.addEdge(1, 3, 4);
//		g.addEdge(2, 3, 1);
//		g.addEdge(2, 4, 2);
//		g.addEdge(3, 5, 4);
//		g.addEdge(4, 5, 2);
//		
//		g.showGraph();
//		
//		if(g.bellmanFord())
//			System.out.println("G contains negative cycle");
//		else
//			System.out.println("G does not contain negative cycle");
		
		Graph g1 = getGraphFromFile("Course4_Week1_g1.txt");
		if(g1.bellmanFord())
			System.out.println("G1 Contains negative cycle");
		else
			System.out.println("G1 doesn't contain negative cycle");
		
		Graph g2 = getGraphFromFile("Course4_Week1_g2.txt");
		if(g2.bellmanFord())
			System.out.println("G2 Contains negative cycle");
		else
			System.out.println("G2 doesn't contain negative cycle");
		
		Graph g3 = getGraphFromFile("Course4_Week1_g3.txt");
		if(g3.bellmanFord())
			System.out.println("G3 Contains negative cycle");
		else
			System.out.println("G3 doesn't contain negative cycle");
		g3.ASAP();
		
	}
	
	public static Graph getGraphFromFile(String fileName){
		try{
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			String[] splitted = line.split(" ");
			Graph g = new Graph(Integer.parseInt(splitted[0]));
			line = br.readLine();
			while(line!=null){
				splitted = line.split(" ");
				g.addEdge(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
				line = br.readLine();
			}
			return g;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

class Node{
	int nodeName;
	Node next;
	int weight;
	boolean visited;
	public Node(int nodeName,int weight){
		this.nodeName = nodeName;
		this.weight = weight;
	}
}

class AdjList{
	Node head;
}

class Graph{
	int length;
	AdjList[] vertices;
	public Graph(int length){
		this.length = length;
		vertices = new AdjList[length];
		for(int i = 0;i<length;i++){
			vertices[i] = new AdjList();
		}
	}
	
	public void addEdge(int start,int end,int weight){
		Node n = new Node(end-1,weight);
		n.next = vertices[start-1].head;
		vertices[start-1].head = n;
	}
	
	public void showGraph(){
		for(int i = 0;i<length;i++){
			Node pointer = vertices[i].head;
			System.out.print(i+1);
			while(pointer!=null){
				System.out.print("->"+pointer.nodeName);
				pointer = pointer.next;
			}
			System.out.println();
		}
	}
	
	public boolean bellmanFord(){
		int dist[][] = new int[length+1][length+1];
		
		for(int i = 0;i<dist.length;i++){
			dist[0][i] = 2000000000;
		}
		dist[0][0] = 0;
		int check[] = new int[length];
		for(int i=1;i<length-1;i++){
			for(int j = 0;j<length;j++){
				int minW = 2000000000;
				for(int k = 0;k<length;k++){
					Node pointer = vertices[k].head;
					while(pointer != null){
						if(pointer.nodeName == j && (dist[i-1][k]+pointer.weight)<minW){
							minW = dist[i-1][k]+pointer.weight;
						}
						pointer = pointer.next;
					}
				}
				if(minW<dist[i-1][j]){
					dist[i][j] = minW;
				}
				else{
					dist[i][j] = dist[i-1][j];
				}
				check[j] = dist[i][j];
			}
			System.out.println(i);
		}
		
		
		for(int i=length-1;i<=length-1;i++){
			for(int j = 0;j<length;j++){
				int minW = 2000000000;
				for(int k = 0;k<length;k++){
					Node pointer = vertices[k].head;
					while(pointer != null){
						if(pointer.nodeName == j && (dist[i-1][k]+pointer.weight)<minW){
							minW = dist[i-1][k]+pointer.weight;
						}
						pointer = pointer.next;
					}
				}
				if(minW<dist[i-1][j]){
					dist[i][j] = minW;
				}
				else{
					dist[i][j] = dist[i-1][j];
				}
			}
			System.out.println(i);
		}
		boolean similar = true;
		for(int i = 0;i<length;i++){
			if(check[i]!=dist[length-1][i]){
				similar = false;
				break;
			}
		}
		return similar;
	}
	
	public void ASAP(){
		int dist[][] = new int[length+1][length+1];
		int minShortestPath = Integer.MAX_VALUE;
		for(int v = 1;v<length-1;v++){
			for(int i = 0;i<dist.length;i++){
				dist[0][i] = 2000000000;
			}
			dist[0][v-1] = 0;
			Integer check[] = new Integer[length];
			for(int i=1;i<length-1;i++){
				for(int j = 0;j<length;j++){
					int minW = 2000000000;
					for(int k = 0;k<length;k++){
						Node pointer = vertices[k].head;
						while(pointer != null){
							if(pointer.nodeName == j && (dist[i-1][k]+pointer.weight)<minW){
								minW = dist[i-1][k]+pointer.weight;
							}
							pointer = pointer.next;
						}
					}
					if(minW<dist[i-1][j]){
						dist[i][j] = minW;
					}
					else{
						dist[i][j] = dist[i-1][j];
					}
					check[j] = dist[i][j];
				}
				Arrays.sort(check,Collections.reverseOrder());
				if(check[0]<minShortestPath)
					minShortestPath = check[0];
				System.out.println(i);
			}
		}
		System.out.println("Shortest of shortest path: "+minShortestPath);
	}
	
}
