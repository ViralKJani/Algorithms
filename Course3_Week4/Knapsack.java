package Course3_Week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Knapsack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int knapsackSize = 0;
		int noOfItems = 0;
		int[][] valueWeight = null;

		try{
			BufferedReader br = new BufferedReader(new FileReader("Course3_Week4_knapsack1.txt"));
			String line = br.readLine();
			String[] splitted = line.split(" ");
			knapsackSize = Integer.parseInt(splitted[0]);
			noOfItems = Integer.parseInt(splitted[1]);
			valueWeight = new int[noOfItems + 1][2];
			line = br.readLine();
			int index = 1;
			while(line!=null){
				splitted = line.split(" ");
				valueWeight[index][0] = Integer.parseInt(splitted[0]);
				valueWeight[index++][1] = Integer.parseInt(splitted[1]);
				line = br.readLine();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int maxItems[][] = new int[noOfItems + 1][knapsackSize + 1];
		int maxValue = 0;
		for (int i = 0; i <= knapsackSize; i++) {
			maxItems[0][i] = 0;
		}
		for(int i = 1;i<=noOfItems;i++){
			for(int j = 1;j<=knapsackSize;j++){
				int index2 = j-valueWeight[i][1];
				if(index2<0){
					maxItems[i][j] = maxItems[i-1][j];
				}
				else{
					maxItems[i][j] = Math.max(maxItems[i-1][j], maxItems[i-1][index2]+valueWeight[i][0]);
				}
				maxValue = maxItems[i][j];
			}
		}
		System.out.println("Max value: "+maxValue);
	}
}
