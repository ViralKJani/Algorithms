package Course2_Week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class HashTable_2SUM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Long, Long> map = new HashMap<Long, Long>();
		// for(int i = -10000;i<=10000;i++){
		// map.put((long)i, (long)0);
		// }
		long val = 0;
		long number;
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader("Course2_Week4_algo1-programming_prob-2sum.txt"));
			line = br.readLine();
			while (line != null) {
				number = Long.parseLong(line);
				if (map.containsKey(number)) {
					val = map.get(number);
					map.put(number, (long) val + 1);
				} else {
					map.put(number, (long) 1);
				}
				line = br.readLine();
			}
			long i = 0;
			long a = 0;
			long b = 0;
			long t = 0;
			for (Long key : map.keySet()) {
				System.out.println(key);
				a = -10000 - key;
				b = 10000 - key;
				for(i = a;i<=b;i++){
					if(map.containsKey(i)){
						map.remove(i);
						t++;
					}
				}
				map.remove(key);
			}
			System.out.println("Total entries: " + i);
			System.out.println("Ans: "+t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
