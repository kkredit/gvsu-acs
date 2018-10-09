package client;

import compute.Task;
import java.io.Serializable;
import java.util.Vector;

public class Primes implements Task<Vector<Integer>>, Serializable {

    private static final long serialVersionUID = 228L;

    private final int calcRangeMin;
    private final int calcRangeMax;

    /**
     * Construct a task to calculate primes in the specified
     * range.
     */
    public Primes(int min, int max) {
        this.calcRangeMin = min;
        this.calcRangeMax = max;
    }

    /**
     * Calculate primes.
     */
    public Vector<Integer> execute() {
        return computePrimes(calcRangeMin, calcRangeMax);
    }

    /**
     * Compute the primes in the range MIN <= primes <= MAX.
     */
    public static Vector<Integer> computePrimes(int min, int max) {
        Vector<Integer> primes = new Vector<Integer>();

        for (int i = min; i <= max; i++) {
            if (isPrime(i)) {
                primes.addElement(i);
            }
        }

        return primes;
    }

    /**
     * Compute if a given number is prime.
     */
    public static boolean isPrime(int number) {
        boolean rv = true;

        for (int i = 2; i <= number / 2; i++) {
            if (0 == number % i) {
                rv = false;
                break;
            }
        }

        return rv;
    }
}
