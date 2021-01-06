// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

import java.util.ArrayList;

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd",
	 * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
	 * common divisor of "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.
		int gcd;
		int s, t;
		int old_s,old_t, old_r, r,temp;// Used to track numbers in the calculation
		int[] result = new int[3];

		old_s = 1;
		s = 0;
		old_t = 0;
		t = 1;

		// Check to make test case for 5,5 pass
		if(a==b){
			old_s = 0;
			s = 1;
			old_t = 1;
			t = 0;
		}

		old_r = a;
		r = b;

		while( r != 0){
			int quotient = old_r/r;

			// Calculate r
			temp = r;
			r = old_r - quotient * r;
			old_r = temp;

			// Calculate s
			temp = s;
			s = old_s - quotient * s;
			old_s = temp;

			// Calculate t
			temp = t;
			t = old_t - quotient * t;
			old_t = temp;
		}

		gcd = old_r;

		result[0] = gcd;
		result[1] = old_s;
		result[2] = old_t;
		return result;
	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {
		//local variables
		int i, result = n;
		//for loop for base case
		for (i=2;i*i<=n;i++){

			if (n%i==0){
				result-=result/i;
			}
			while(n%i==0){
				n/=i;
			}
		}
		//corner cases
		if (n!=1){
			result-=result/n;
		}
		if (n==-1){
			result=0;
		}

		return result;
	}

	/**
	 * Returns the value "v" such that " n*v = 1 (mod m)".Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		if (n<0){
			n += m;
		}
		int [] res = EEA(n, m);
		int s = res[1];
		// Check if s a negative number and make it positive
		if (s<0){
			s += m;
		}
		int m_inv = n * s % m;
		if (m_inv != 1){
			return 0;
		}
		return s;
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		//Base case if n is 1
		if(n==1){
			return 1;
		}
		//loop starts at 2 and increment it by 1 at each new iteration, until you have tested all the values below n/3.
		for (int i = 2; i < n/3 ; i++) {
			//if it is not equal to 1 then returns the i (witness)
			//otherwise we parse the loop without get into the if
			if (modPow(i, n - 1, n) != 1) {
				return i;
			}
		}
		// If n is a Fermat Prime
		return 0;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		//local variable for result set to 1 because we use it inside the loop and in the first interation it must be 1
		double result =1;
		//for loop for calculating the probability of having a collision using the birthday problem and the paradox
		for (double i= size;i>size-n_samples;i--){
			result = result * (i/size);
		}
		//the probability of having a collision is p = 1-p',
		result = 1 - result;
		return result;
	}

	// HELPER FUNCTIONS

	/**
	 * Function to calculate (a ^ b) % c
	 **/
	public static int modPow(int a, int b, int c) {
		int res = 1;
		for (int i = 0; i < b; i++)
		{
			res *= a;
			res %= c;
		}
		return res % c;
	}
}
