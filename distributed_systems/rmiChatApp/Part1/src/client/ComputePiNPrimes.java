package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import compute.Compute;
import java.util.Scanner;
import java.util.Vector;

public class ComputePiNPrimes {

    private static Compute comp;

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Register remote compute engine
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            comp = (Compute) registry.lookup(name);

            // Enter control loop
            controlLoop();
        } catch (Exception e) {
            System.err.println("ComputePiNPrimes exception:");
            e.printStackTrace();
        }
    }

    private static void controlLoop() throws RemoteException {
        Scanner reader = new Scanner(System.in);
        boolean more = true;
        printUsage();

        while (more) {
            System.out.println("Enter a selection: ");
            int opt = reader.nextInt();
            switch (opt) {
                case 1:
                    System.out.println("Enter number of digits of pi to compute: ");
                    int digits = reader.nextInt();
                    doCalcPi(digits);
                    break;
                case 2:
                    System.out.println("Enter min of primes range: ");
                    int min = reader.nextInt();
                    System.out.println("Enter max of primes range: ");
                    int max = reader.nextInt();
                    doCalcPrimes(min, max);
                    break;
                case 3:
                    more = false;
                    break;
                default:
                    printUsage();
                    break;
            }
        }

        reader.close();
    }

    private static void printUsage() {
        System.out.println("Usage:\n"+
                           "\tEnter 1 to compute pi\n"+
                           "\tEnter 2 to compute primes\n"+
                           "\tEnter 3 to exit\n");
    }

    private static void doCalcPi(int digits) throws RemoteException {
        Pi task = new Pi(digits);
        BigDecimal pi = comp.executeTask(task);
        System.out.println(pi);
    }

    private static void doCalcPrimes(int min, int max) throws RemoteException {
        Primes task = new Primes(min, max);
        Vector<Integer> primes = comp.executeTask(task);

        System.out.println("Primes in this range include:");

        for (int prime : primes) {
            System.out.println("\t" + prime);
        }
    }
}