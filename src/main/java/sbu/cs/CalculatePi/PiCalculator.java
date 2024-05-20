package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.Executors;
import java.math.RoundingMode;
import java.util.Scanner;

public class PiCalculator
{
    private static final int NUM_THREADS = Runtime.getRuntime ().availableProcessors (); //Set number of threads according to available processors

    public String calculate (int floatingPoint)
    {
        ExecutorService executor = Executors.newFixedThreadPool (NUM_THREADS); //Creates an ExecutorService to manage multiple threads

        AtomicReference <BigDecimal> sum = new AtomicReference <> (BigDecimal.ZERO); //Initializes an AtomicReference variable sum with a value of zero to accumulate the sum of terms in the series

        BigDecimal threshold = BigDecimal.ONE.scaleByPowerOfTen (- floatingPoint); //Defines a threshold value representing the desired precision

        for (int i = 0; i < NUM_THREADS; i++)
        {
            final int threadIndex = i;
            executor.execute (() ->
            {
                BigDecimal localSum = BigDecimal.ZERO;

                //Starts a loop that iterates over the terms in the series
                for (int k = threadIndex; ; k += NUM_THREADS)
                {
                    BigDecimal term = computeTerm (k, floatingPoint); //Calculates the value of the term at index k using the computeTerm method
                    localSum = localSum.add (term); //Adds the calculated term to the localSum

                    //Checks if the magnitude of the term is smaller than the threshold
                    if (term.abs ().compareTo (threshold) < 0)
                    {
                        break; //Stops the loop if the threshold is met
                    }
                }
                synchronized (sum)
                {
                    BigDecimal finalLocalSum = localSum;
                    sum.updateAndGet (currentSum -> currentSum.add (finalLocalSum)); //Adds the localSum to the global sum in a thread-safe manner
                }
            });
        }

        executor.shutdown ();

        try
        {
            //Waits for all threads to finish, up to 10 minutes
            if (! executor.awaitTermination (10, TimeUnit.MINUTES))
            {
                executor.shutdownNow (); //Forces shutdown if not all threads finish in time
            }
        }
        catch (InterruptedException e)
        {
            executor.shutdownNow ();
        }

        //Calculates the final value of π by rounding the sum to the specified number of digits using the setScale method
        BigDecimal pi = sum.get ().setScale (floatingPoint, RoundingMode.HALF_DOWN);
        return pi.toString ();
    }

    private BigDecimal computeTerm (int k, int floatingPoint)
    {
        //Computes the digit at index k of the term using the Bailey–Borwein–Plouffe formula
        BigDecimal numerator = BigDecimal.valueOf (4).divide (BigDecimal.valueOf (8L * k + 1), floatingPoint + 5, RoundingMode.HALF_DOWN)
                .subtract (BigDecimal.valueOf (2).divide (BigDecimal.valueOf (8L * k + 4), floatingPoint + 5, RoundingMode.HALF_DOWN))
                .subtract (BigDecimal.ONE.divide (BigDecimal.valueOf (8L * k + 5), floatingPoint + 5, RoundingMode.HALF_DOWN))
                .subtract (BigDecimal.ONE.divide (BigDecimal.valueOf (8L * k + 6), floatingPoint + 5, RoundingMode.HALF_DOWN));

        BigDecimal denominator = BigDecimal.valueOf (16).pow (k); //Computes the denominator of the term
        return numerator.divide (denominator, floatingPoint + 5, RoundingMode.HALF_EVEN); //Computes the value by dividing the numerator by the denominator
    }

    public static void main (String[] args)
    {
        Scanner      scanner    = new Scanner (System.in);
        PiCalculator calculator = new PiCalculator ();

        while (true)
        {
            System.out.print ("Enter the number of Digits after the Floating Point: ");

            int digits = scanner.nextInt ();
            if (digits == 0)
            {
                return; //Closes the program if the entered input is 0
            }

            long   startTime = System.nanoTime ();
            String pi        = calculator.calculate (digits); //Calculates π
            long   endTime   = System.nanoTime ();

            System.out.println (pi);
            System.out.println ("Time taken: " + (endTime - startTime) / 1_000_000 + " ms"); //Prints the time taken to calculate π
            System.out.println ();
        }
    }
}
