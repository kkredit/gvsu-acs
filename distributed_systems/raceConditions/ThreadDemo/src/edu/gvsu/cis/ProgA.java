
package edu.gvsu.cis;
import java.util.*;
import java.lang.IllegalArgumentException;

public class ProgA implements Runnable {

	private Counter cnt;

	public ProgA(Counter cnt)
	{
		this.cnt = cnt;
	}

	@Override
	public void run() {
		for(int i=0; i<1000000; i++) {
			cnt.increment();
		}
	}

	private static void checkArg(Boolean cond, String s) {
		if (!cond) {
			throw new IllegalArgumentException(s);
		}
	}

	private static int getNumThreads(String[] args) {
		checkArg(1 == args.length,
				 "Pass in 1 arg: the number of threads to create.");
		int rv = Integer.parseInt(args[0]);
		checkArg(0 < rv && 32 >= rv,
				"The number of threads must be 0 < N <= 32.");
		return rv;
	}

	/**
	 * @param args should have 1 arg: the number of threads to spawn
	 */
	public static void main(String[] args) {

		// Setup
		int numThreads = 0;
		try {
			numThreads = getNumThreads(args);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		Vector<Thread> threads = new Vector<>();
		Counter cnt = new Counter();
		long startTime = System.currentTimeMillis();

		// Create, run, and join threads
		for (int i = 0; i < numThreads; i++)
		{
			threads.add(new Thread(new ProgA(cnt)));
		}

		for (Thread t : threads)
		{
			t.start();
		}

		try {
			for (Thread t : threads)
			{
				t.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Analysis
		long stopTime = System.currentTimeMillis();
		System.out.println("For " + numThreads + " thread(s):");
		System.out.println("Final Counter = " + cnt.getCounter());
		System.out.println("Runtime: " + (stopTime - startTime) + " ms");
	}
}
