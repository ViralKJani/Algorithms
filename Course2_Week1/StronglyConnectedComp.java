package Course2_Week1;

import java.io.BufferedReader;
import java.io.FileReader;


public class StronglyConnectedComp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph(875714);
		addEdgesFormFile(g, false);
		System.out.println("Added egdes for graph1");
//		g.addEgde(1, 2);
//		g.addEgde(2, 3);
//		g.addEgde(3, 1);
//		g.addEgde(2, 4);
//		g.addEgde(4, 5);
//		g.addEgde(5, 6);
//		g.addEgde(6, 4);
//		g.addEgde(5, 7);
//		g.addEgde(7, 8);
//		g.addEgde(8, 9);
//		g.addEgde(9, 7);
//		System.out.println("Edges added");
//		g.showGraph();
		
		Graph gRev = new Graph(875714);
		addEdgesFormFile(g, true);
		System.out.println("Added edges for graph rev");
//		gRev.addEgde(2, 1);
//		gRev.addEgde(3, 2);
//		gRev.addEgde(1, 3);
//		gRev.addEgde(4, 2);
//		gRev.addEgde(5, 4);
//		gRev.addEgde(6, 5);
//		gRev.addEgde(4, 6);
//		gRev.addEgde(7, 5);
//		gRev.addEgde(8, 7);
//		gRev.addEgde(9, 8);
//		gRev.addEgde(7, 9);
//		Graph copy = gRev.copyGraph(gRev);
//		copy.showGraph();
//		gRev.sortByTopoOrder(g, gRev);
//		gRev.showGraph();
//		gRev.copyFinishingTime(g);
//		System.out.println("\n\n");
		gRev.DFSLoop();
		System.out.println("Grev DFS LOOP Completed");
		g.copyFinishingTime(gRev);
		g.DFSLoopSorted();
//		g.showFinishTime();
		
//		gRev.showLeader();
		System.out.println("SCC: "+g.sccCount);
	}
	
	public static void addEdgesFormFile(Graph g,boolean isRev){
		int start,end;
		String[] numbers = new String[2];
		try{
			BufferedReader br = new BufferedReader(new FileReader("data_scc.txt"));
			String line = br.readLine();
			while(line != null){
				numbers = line.split(" ");
				start = Integer.parseInt(numbers[0]);
				end = Integer.parseInt(numbers[1]);
//				System.out.println("line is: "+line + " start is " +start + " end is "+end);
				if(isRev){
					g.addEgde(end, start);
				}
				else{
					g.addEgde(start, end);
				}
				
				line = br.readLine();
			}
//			String everything = sb.toString();
//			System.out.print(everything);
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}


class Node{
	int nodeName;
	Node next;
	public Node(int nodeName){
		this.nodeName = nodeName;
		next = null;
	}
}

class AdjList{
	Node head;
	boolean visited;
	int finishTime;
	int leader;
}

class Graph{
	AdjList[] vertices;
	int t,s;
	int sccCount;
	int sccNodeCount;
	public Graph(int nodes){
		vertices = new AdjList[nodes + 1];
		for(int i = 1;i <= nodes;i++){
			vertices[i] = new AdjList();
			vertices[i].visited = false;
		}
		t = 0;
		sccCount = 0;
	}
	
	public void showGraph(){
		for(int i = 1; i < vertices.length;i++){
			System.out.print(i);
			Node n = vertices[i].head;
			while(n != null){
				System.out.print( "->" + n.nodeName );
				n = n.next;
			}
			System.out.println("");
		}
	}
	
	public void copyFinishingTime(Graph g){
		for(int i = 1; i < vertices.length;i++){
//			System.out.println("Finish time of :"+ i + " is " +g.vertices[i].finishTime);
			this.vertices[i].finishTime = g.vertices[i].finishTime;
		}
	}
	
	public void addEgde(int start,int end){
		Node endNode = new Node(end);
		endNode.next = vertices[start].head;
		vertices[start].head = endNode;
	}
	
	public void showFinishTime(){
		for(int i = 1 ; i < vertices.length; i++){
			System.out.println("Finish time of " + i + " is " +vertices[i].finishTime);
		}
	}
	
	public void showLeader(){
		for(int i = 1 ; i < vertices.length; i++){
			System.out.println("Leader of " + i + " is " +vertices[i].leader);
		}
	}
	
	public void DFSLoopSorted(){
		t = 0;
		for(int i = vertices.length-1;i>=1;i--){
			for(int j = 1;j < vertices.length;j++){
				if(vertices[j].finishTime == i && vertices[j].visited == false){
//					System.out.println("IN IF");
					//System.out.println("Going to visit "+j);
					s = j;
					sccCount++;
					sccNodeCount = 0;
//					System.out.println("set leader to : "+j);
					DFS(j);
					System.out.println("Nodes in SCC "+sccCount+": "+sccNodeCount);
					sccNodeCount = 0;
				}
//				else{
//					System.out.println("IN ELSE " + vertices[j].finishTime + " " + i);
//				}
			}
		}
	}
	
	public void DFSLoop(){
		t = 0;
		for(int i = 1; i < vertices.length;i++){
			if(!vertices[i].visited){
				s = i;
				//System.out.println("Going to visit "+i);
				DFS(i);
			}
		}
//		System.out.println("All nodes visited");
	}
	
	public void DFS(int start){
//		System.out.println("vertex "+start+" visited");
		vertices[start].visited = true;
		sccNodeCount++;
		Node pointer = vertices[start].head;
		while(pointer != null){
			if(!vertices[pointer.nodeName].visited){
				vertices[pointer.nodeName].leader = s;
				System.out.println("DFS called via "+pointer.nodeName);
				DFS(pointer.nodeName);
			}
			pointer = pointer.next;
		}
		t++;
		vertices[start].finishTime = t;
	}
	
	public Graph copyGraph(Graph G){
		Graph copyG = new Graph(G.vertices.length);
		for(int i = 1;i<G.vertices.length;i++){
			Node pointer = G.vertices[i].head;
			while(pointer != null){
				copyG.addEgde(i, pointer.nodeName);
				copyG.vertices[i].finishTime = G.vertices[i].finishTime;
//				copyG.vertices[i].visited = false;
				pointer = pointer.next;
			}
		}
		return copyG;
	}
	
	public void sortByTopoOrder(Graph G1,Graph G2){
		Node temp;
		Graph G2Copy = G2.copyGraph(G2);
//		System.out.println("In sort by topo order");
		for(int i = 1; i < G1.vertices.length ; i++){
			temp = G2.vertices[G1.vertices[i].finishTime].head;
			G2.vertices[G1.vertices[i].finishTime].head = G2Copy.vertices[i].head;
			G2Copy.vertices[i].head = temp;
			//System.out.println("Swapped "+i+" with "+G1.vertices[i].finishTime);
		}
	}
}