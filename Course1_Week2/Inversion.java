package Course1_Week2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Inversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long[] input = new long[100000];
		String line = "";
		long index = 0;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("Course1_Week2_IntegerArray.txt"));
			line = br.readLine();
			while (line != null) {
				input[(int) index++] = Long.parseLong(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		temp = new long[input.length];
		long splitInversionCount = sort(input, 0, input.length - 1);
		System.out.println("Split Inversion count: " + splitInversionCount);
	}

	static long[] temp;

	public static long sort(long[] input, int start, int end) {
		long splitInvCnt = 0;
		
		if (end > start) {
			int mid = (start + end) / 2;
			splitInvCnt = sort(input, start, mid);
			splitInvCnt += sort(input, mid + 1, end);
			splitInvCnt += merge(input, temp, start, mid + 1, end);
		}

		return splitInvCnt;
	}

	public static long merge(long[] input, long[] temp, int start, int mid, int end) {
		int arr1Index = 0, arr2Index = 0, tempIndex = 0;
		long inversionCount = 0;

		arr1Index = start;
		arr2Index = mid;
		tempIndex = start;
		
		while ((arr1Index <= mid - 1) && (arr2Index <= end)) {
			if (input[arr1Index] <= input[arr2Index]) {
				temp[tempIndex++] = input[arr1Index++];
			} else {
				temp[tempIndex++] = input[arr2Index++];
				inversionCount += (mid - arr1Index);
			}
		}

		while (arr1Index <= mid - 1) {
			temp[tempIndex++] = input[arr1Index++];
		}

		while (arr2Index <= end) {
			temp[tempIndex++] = input[arr2Index++];
		}

		for (int i = start; i <= end; i++) {
			input[i] = temp[i];
		}

		return inversionCount;
	}
}