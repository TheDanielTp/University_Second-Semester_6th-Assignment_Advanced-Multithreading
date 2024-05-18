package sbu.cs.Semaphore;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Operator extends Thread
{

    public Operator (String name)
    {
        super (name);
    }

    @Override
    public void run ()
    {
        try
        {
            //Acquire a permit before entering the critical section
            Controller.semaphore.acquire ();

            //Critical section
            LocalTime currentTime = LocalTime.now ();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("HH:mm:ss");
            System.out.println (Thread.currentThread ().getName () + " accessed the resource at " + currentTime.format (formatter));

            //Simulate some work with the resource
            Thread.sleep ((int) (Math.random () * 1000));

        }
        catch (InterruptedException e)
        {
            System.out.println (e.getMessage ());
        }
        finally
        {
            //Release the permit after leaving the critical section
            Controller.semaphore.release ();
        }
    }
}
