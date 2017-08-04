package Course3_Week3;

import java.io.BufferedReader;
import java.io.FileReader;

public class HuffmanCodes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int weights[] = { 3, 2, 6, 8, 2, 6 };
		String weights2[][] = null;
				
		Node[] nodeArr = null;
		try{
			BufferedReader br = new BufferedReader(new FileReader("Course3_Week3_Q1_huffman.txt"));
			int size = Integer.parseInt(br.readLine());
			weights2 = new String[size][2];
			nodeArr = new Node[weights2.length];
			arrLen = weights2.length;
			int index = 0;
			String line = br.readLine();
			while(line!=null){
				weights2[index][0] = (int)(index+1)+"";
				weights2[index][1] = line;
				line = br.readLine();
				index++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
		t.root = null;
		generateHuffmanCodes(weights2, nodeArr, t.root);
//		preorder(t.root);
		System.out.println("Maximum length of a codeword: "+(levels(t.root)-1));
		System.out.println("Minimum length of a codeword: "+(levels2(t.root)-1));
	}
	
	public static int levels(Node n){
		if(n.right == null && n.left == null)
			return 1;
		return(Math.max(levels(n.left), levels(n.right)) + 1);
	}
	
	public static int levels2(Node n){
		if(n.right == null && n.left == null)
			return 1;
		return(Math.min(levels2(n.left), levels2(n.right)) + 1);
	}

	public static void preorder(Node n) {
		if (n == null)
			return;
		System.out.println(n.nodeName);
		preorder(n.left);
		preorder(n.right);
	}

	static Tree t = new Tree();
	static int arrLen = 0;

	public static void generateHuffmanCodes(String[][] weightsArr, Node[] nodeArr, Node root) {

		int weights[] = new int[weightsArr.length];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Integer.parseInt(weightsArr[i][1]);
		}

		// find minimum weights
		int min1 = Integer.MAX_VALUE;
		int min2 = min1;
		int min1Index = -1, min2Index = -1;
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] < min1) {
				min1 = weights[i];
				min1Index = i;
			}
		}
		weights[min1Index] = Integer.MAX_VALUE;
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] < min2) {
				min2 = weights[i];
				min2Index = i;
			}
		}
		weights[min2Index] = Integer.MAX_VALUE;
		String mergedNodes = weightsArr[min1Index][0] + " " + weightsArr[min2Index][0];
		weightsArr[min1Index][0] = mergedNodes;
		weightsArr[min1Index][1] = (int) (min1 + min2) + "";

		weightsArr[min2Index][1] = Integer.MAX_VALUE + "";

		arrLen--;
		Node n1, n2;
		if (nodeArr[min1Index] == null) {
			n1 = new Node();
			n1.nodeName = (min1Index + 1) + "";
			n1.left = null;
			n1.right = null;
		} else {
			n1 = nodeArr[min1Index];
		}

		if (nodeArr[min2Index] == null) {
			n2 = new Node();
			n2.nodeName = (min2Index + 1) + "";
			n2.left = null;
			n2.right = null;
		} else {
			n2 = nodeArr[min2Index];
		}

		Node n3 = new Node();
		n3.nodeName = mergedNodes;
		n3.left = n1;
		n3.right = n2;

		nodeArr[min1Index] = n3;
		t.root = n3;
		
		if (arrLen > 1)
			generateHuffmanCodes(weightsArr, nodeArr, root);
		
	}

	public static Node findNode(String nodeName, Node root) {
		if (root == null) {
			return null;
		}
		System.out.println(root.nodeName);
		if (root.nodeName.equals(nodeName)) {
			return root;
		}
		Node resultLeft = findNode(nodeName, root.left);
		Node resultRight = findNode(nodeName, root.right);

		if (resultLeft != null && resultRight != null) {
			return null;
		} else {
			if (resultLeft == null) {
				return resultRight;
			} else {
				return resultLeft;
			}
		}
	}
}

class Node {
	String nodeName;
	Node left;
	Node right;
}

class Tree {
	Node root;

}