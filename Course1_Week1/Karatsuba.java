package Course1_Week1;

import java.math.BigInteger;

public class Karatsuba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String a = "3141592653589793238462643383279502884197169399375105820974944592";
		String b = "2718281828459045235360287471352662497757247093699959574966967627";

		String ans = karatsuba(a, b);
		System.out.println("Ans: " + ans);
	}

	public static String karatsuba(String term1, String term2) {
		int length1 = term1.length();
		int length2 = term2.length();
		int maxLength = length1 > length2 ? length1 : length2;
		String a, b, c, d;
		String step1, step2, step3, step4;
		maxLength = (maxLength / 2);

		if (maxLength <= 1) {
			return (Integer.valueOf(term1) * Integer.valueOf(term2)) + "";
		}

		if (length1 % 2 == 0) {
			a = term1.substring(0, length1 / 2);
			b = term1.substring(length1 / 2);
		} else {
			a = term1.substring(0, (length1 / 2) + 1);
			b = term1.substring((length1 / 2) + 1);
		}
		if (length2 % 2 == 0) {
			c = term2.substring(0, length2 / 2);
			d = term2.substring(length2 / 2);
		} else {
			c = term2.substring(0, (length2 / 2) + 1);
			d = term2.substring((length2 / 2) + 1);
		}

		step1 = karatsuba(a, c);
		step2 = karatsuba(b, d);
		step3 = karatsuba(((new BigInteger(a).add(new BigInteger(b))) + ""),
				(new BigInteger(c).add(new BigInteger(d)) + ""));
		step4 = (new BigInteger(step3).subtract(new BigInteger(step1))).subtract(new BigInteger(step2)) + "";
		BigInteger power1 = new BigInteger("1");
		for (int i = 0; i < (maxLength * 2); i++) {
			power1 = power1.multiply(new BigInteger("10"));
		}
		BigInteger power2 = new BigInteger("1");
		for (int i = 0; i < maxLength; i++) {
			power2 = power2.multiply(new BigInteger("10"));
		}

		return ((power1.multiply(new BigInteger(step1))).add(new BigInteger(step2))
				.add(new BigInteger(step4).multiply(power2)) + "");
	}
}
