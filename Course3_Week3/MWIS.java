package Course3_Week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class MWIS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] weights;
		int index = 0;
		long[] mwis;
		int[] check = {1,2,3,4,17,117,517,997};
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("Course3_Week3_Q3_mwis.txt"));
			int size = Integer.parseInt(br.readLine());
			weights = new int[size+1];
			mwis = new long[size+1];
			HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
			
			String line = br.readLine();
			weights[1] = Integer.parseInt(line);
			line = br.readLine();
			index = 2;
			mwis[0] = 0;
			mwis[1] = weights[1];
			while(line!=null){
				weights[index] = Integer.parseInt(line);
				mwis[index] = Math.max(mwis[index-1], mwis[index-2]+weights[index]);
				line = br.readLine();
				index++;
			}
			
			for(int i = size;i>=2;){
				if(mwis[i-1]>mwis[i-2]+weights[i]){
					System.out.println("Selected Index: "+(i-1));
					map.put(i-1,1);
					i--;
				}
				else{
					map.put(i-2,1);
					System.out.println("Selected Index: "+(i-2));
					i-=2;
				}
			}
			System.out.println("Final result");
			for(int i = 0;i<check.length;i++){
				if(map.containsKey(check[i])){
					System.out.println(check[i]+"\t:\t 1");
				}
				else{
					System.out.println(check[i]+"\t:\t 0");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
