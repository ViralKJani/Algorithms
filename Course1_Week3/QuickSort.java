package Course1_Week3;

import java.io.BufferedReader;
import java.io.FileReader;

// First element as pivot
public class QuickSort {
	
	static long totalComparisons = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long[] input = new long[10000];
		String line = "";
		int j = 0;
		try{
			BufferedReader br = new BufferedReader(new FileReader("Course1_Week3_Q1_QuickSort.txt"));
			line = br.readLine();
			while(line != null){
				input[j++] = Long.parseLong(line);
				line = br.readLine();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		quickSort(input, 0, 9999);
		System.out.println("Total Comparisons: " + totalComparisons);
	}

	public static void quickSort(long[] input, int start, int end) {
		if(end-start > 0){
			totalComparisons += (end-start);
			int pivotIndex = pivot(input,start,end);
			quickSort(input, start, pivotIndex-1);
			quickSort(input, pivotIndex + 1, end);
		}
	}
	
	public static int pivot(long[] input,int start,int end) {
		long elem = input[start];
		long temp = 0;
		int pivotIndex = start + 1;
		for (int i = start+1; i <= end; i++) {
			if (input[i] < elem) {
				temp = input[i];
				input[i] = input[pivotIndex];
				input[pivotIndex] = temp;
				pivotIndex++;
			} else {
				// Do nothing
			}
		}
		temp = elem;
		input[start] = input[pivotIndex - 1];
		input[pivotIndex-1] = temp;
		return pivotIndex-1;
	}
}
