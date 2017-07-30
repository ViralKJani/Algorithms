package Course2_Week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;

public class Heap {

	static int length = 1;
	static int length2 = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] input = new int[10001];
		long sumMedian = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course2_Week3_Median.txt"));
			String line = br.readLine();
			
			int mid = 0;
			while (line != null) {
				maxHeapInsert(input, Integer.parseInt(line));
				line = br.readLine();
				
				mid = length2/2;
				ArrayList<Integer> a = new ArrayList<Integer>();
				while(length2>1&&length2!=mid){
					a.add(extractMax(input));
				}
				sumMedian += a.get(a.size()-1);
				
				while(a.size()!=0){
					maxHeapInsert(input, a.get(a.size()-1));
					a.remove(a.size()-1);
				}
			}
			
			System.out.println("Sum of median:" + sumMedian);
			
			System.out.println("Last four digits:" + (sumMedian%10000));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void printHeap(int[] input) {
		System.out.print("Printing heap: ");
		for (int i = 1; i < length2; i++) {
			System.out.print(input[i] + " ");
		}
		System.out.println();
	}

	public static void maxHeapInsert(int[] input, int elem) {
		int index = length2, temp;
		int mid;
		input[length2++] = elem;
		if (index == 1) {
			return;
		}
		if (index % 2 == 0) {
			mid = index / 2;
		} else {
			mid = (index - 1) / 2;
		}

		while (index != 1 && input[mid] < input[index]) {
			temp = input[mid];
			input[mid] = input[index];
			input[index] = temp;
			index = mid;
			mid = mid / 2;
		}
	}

	public static void heapInsert(int[] input, int elem) {
		int index = length, temp;
		int mid;
		input[length++] = elem;
		if (index == 1) {
			return;
		}
		if (index % 2 == 0) {
			mid = index / 2;
		} else {
			mid = (index - 1) / 2;
		}

		while (index != 1 && input[mid] > input[index]) {
			temp = input[mid];
			input[mid] = input[index];
			input[index] = temp;
			index = mid;
			mid = mid / 2;
		}
		// printHeap(input);
	}

	public int getMedian(int[] arr) {
		return arr[length / 2];
	}

	public static int extractMin(int[] arr) {
		int min = arr[1];
		int parentIndex = 1;
		int parent = arr[parentIndex];

		int temp, leftChild = -1, rightChild = -1;
		arr[parentIndex] = arr[length - 1];
		parent = arr[parentIndex];
		length--;
		if (parentIndex * 2 <= length - 1) {
			leftChild = arr[parentIndex * 2];
		} else {
			leftChild = -1;
		}
		if ((parentIndex * 2) + 1 <= length - 1) {
			rightChild = arr[(parentIndex * 2) + 1];
		} else {
			rightChild = Integer.MAX_VALUE;
		}
		if (length < 1)
			return -1;
		else if (length == 1)
			return min;

		while (leftChild != -1 && (leftChild < parent || rightChild < parent)) {
			if (leftChild < rightChild) {
				temp = arr[parentIndex * 2];
				arr[parentIndex * 2] = arr[parentIndex];
				arr[parentIndex] = temp;

				parentIndex = parentIndex * 2;
			} else {
				temp = arr[(parentIndex * 2) + 1];
				arr[(parentIndex * 2) + 1] = arr[parentIndex];
				arr[parentIndex] = temp;
				parentIndex = (parentIndex * 2) + 1;
			}
			if (parentIndex * 2 <= length - 1) {
				leftChild = arr[parentIndex * 2];
			} else {
				leftChild = -1;
			}
			if (((parentIndex * 2) + 1) <= length - 1) {
				rightChild = arr[(parentIndex * 2) + 1];
			} else {
				rightChild = -1;
			}
		}
		return min;
	}

	public static int extractMax(int[] arr) {
		int max = arr[1];
		int parentIndex = 1;
		int parent = arr[parentIndex];

		int temp, leftChild = -1, rightChild = -1;
		arr[parentIndex] = arr[length2 - 1];
		parent = arr[parentIndex];
		length2--;
		if (parentIndex * 2 <= length2 - 1) {
			leftChild = arr[parentIndex * 2];
		} else {
			leftChild = -1;
		}
		if ((parentIndex * 2) + 1 <= length2 - 1) {
			// System.out.println("right child and length:" + (parentIndex * 2)
			// + 1 +" " + (length2-1) );
			rightChild = arr[(parentIndex * 2) + 1];
		} else {
			rightChild = -1;
		}
		if (length2 < 1)
			return -1;
		else if (length2 == 1)
			return max;

		// printHeap(arr);
		// System.out.println("length:"+length2);
		while (leftChild != -1 && (leftChild > parent || rightChild > parent)) {
			// System.out.println("left right:" + leftChild + " "+rightChild);
			if (leftChild > rightChild) {
				temp = arr[parentIndex * 2];
				arr[parentIndex * 2] = arr[parentIndex];
				arr[parentIndex] = temp;

				parentIndex = parentIndex * 2;
			} else {
				temp = arr[(parentIndex * 2) + 1];
				arr[(parentIndex * 2) + 1] = arr[parentIndex];
				arr[parentIndex] = temp;
				parentIndex = (parentIndex * 2) + 1;
			}
			if (parentIndex * 2 <= length2 - 1) {
				leftChild = arr[parentIndex * 2];
			} else {
				leftChild = -1;
			}
			if (((parentIndex * 2) + 1) <= length2 - 1) {
				rightChild = arr[(parentIndex * 2) + 1];
			} else {
				rightChild = -1;
			}
		}
		return max;
	}

}
