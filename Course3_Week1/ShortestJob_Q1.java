package Course3_Week1;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ShortestJob_Q1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] jobs = new int[5][5];
		jobs[1][1] = 1;
		jobs[2][1] = 2;
		jobs[3][1] = 3;
		jobs[4][1] = 4;
		
		jobs[1][2] = 10;
		jobs[2][2] = 15;
		jobs[3][2] = 20;
		jobs[4][2] = 0;
		
		jobs[1][3] = 20;
		jobs[2][3] = 15;
		jobs[3][3] = 10;
		jobs[4][3] = 10;
		
		int arr[] = new int[5];
		String prevJob = "";
		HashMap<Integer,String> distJobMap = new HashMap<Integer,String>();
		for(int i = 1;i<=4;i++){
			jobs[i][4] = jobs[i][2] - jobs[i][3];
			arr[i] = jobs[i][4];
			System.out.println(arr[i]);
			if(distJobMap.containsKey(arr[i])){
				prevJob = distJobMap.get(arr[i]);
				distJobMap.put(arr[i], prevJob+""+i);
			}
			else{
				distJobMap.put(arr[i],i+"");
			}
		}
		Arrays.sort(arr);
		// Reverse array
		int temp,length = arr.length;
		for(int i = 1;i<=arr.length/2;i++){
			temp = arr[i];
			arr[i] = arr[length-i];
			arr[length-i]=temp;
		}
		
		for(int i = 1;i<arr.length;i++){
			prevJob = distJobMap.get(arr[i]);
			Integer weights[] = new Integer[prevJob.length()];
			for(int j = 0;j<prevJob.length();j++){
				weights[j] = jobs[Integer.parseInt(prevJob.charAt(j)+"")][2];
			}
			Arrays.sort(weights, Collections.reverseOrder());
		}
	}
}
