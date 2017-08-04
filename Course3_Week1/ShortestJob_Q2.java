package Course3_Week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ShortestJob_Q2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		float[][] jobs = null;
		String[] weight_length;

		String line = "";
		int index = 1;
		int noOfJobs = 0;
		
		// Read from file and calculate weighted sum of completion times
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course3_Module1_Q1_jobs.txt"));
			line = br.readLine();
			noOfJobs = Integer.parseInt(line);
			jobs = new float[noOfJobs + 1][5];
			line = br.readLine();
			while (line != null) {
				weight_length = line.split(" ");
				jobs[index][1] = index;
				jobs[index][2] = Integer.parseInt(weight_length[0]);
				jobs[index++][3] = Integer.parseInt(weight_length[1]);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// arr stores weight/length for each job
		float arr[] = new float[noOfJobs + 1];
		String prevJob = "";

		// HashMap stores mapping of difference(weight-length) and array of jobs
		// having same difference
		HashMap<Float, String> distJobMap = new HashMap<Float, String>();

		// Create mapping
		for (int i = 1; i <= noOfJobs; i++) {
			jobs[i][4] = jobs[i][2] / jobs[i][3];
			arr[i] = jobs[i][4];
			if (distJobMap.containsKey(arr[i])) {
				prevJob = distJobMap.get(arr[i]);
				distJobMap.put(arr[i], prevJob + " " + i);
			} else {
				distJobMap.put(arr[i], i + "");
			}
		}

		// Sort differences in decreasing order
		Arrays.sort(arr);
		float temp;
		int length = arr.length;
		for (int i = 1; i <= arr.length / 2; i++) {
			temp = arr[i];
			arr[i] = arr[length - i];
			arr[length - i] = temp;
		}
		long jobCompletionTime = 0;
		long weightedSumComplTime = 0;

		String[] prevJobs;
		float[] arr2 = new float[noOfJobs + 1];
		int arr2Index = 0;
		HashMap<Float, Integer> map = new HashMap<Float, Integer>();

		// Create a distinct array of difference and remove duplicate
		// differences
		for (int i = 1; i <= noOfJobs; i++) {
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], 1);
				arr2[arr2Index++] = arr[i];
			}
		}

		// For each difference value get the jobs associated with difference
		// Calculate cumulative completion time and weighted sum of completion
		// times
		for (int i = 0; i <= arr2Index; i++) {
			if (!distJobMap.containsKey(arr2[i])) {
				continue;
			}

			prevJob = distJobMap.get(arr2[i]);
			prevJobs = new String[prevJob.split(" ").length];
			prevJobs = prevJob.split(" ");
			Long weights[] = new Long[prevJobs.length];
			for (int j = 0; j < prevJobs.length; j++) {
				weights[j] = (long) jobs[Integer.parseInt(prevJobs[j])][2];
			}

			// In case of tie select jobs in decreasing order of their weights
			Arrays.sort(weights, Collections.reverseOrder());
			for (int j = 0; j < weights.length; j++) {
				for (int k = 0; k < prevJobs.length; k++) {
					if ((!prevJobs[k].equalsIgnoreCase("A")) && weights[j] == jobs[Integer.parseInt(prevJobs[k])][2]) {
						jobCompletionTime += (long) jobs[Integer.parseInt(prevJobs[k] + "")][3];
						weightedSumComplTime += (weights[j] * jobCompletionTime);
						prevJobs[k] = "A";
					}
				}
			}
			distJobMap.remove(arr2[i]);
		}
		System.out.println("Weighted sum of completion times: " + weightedSumComplTime);
	}

}
