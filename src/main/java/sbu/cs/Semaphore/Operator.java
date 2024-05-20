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
            Resource.accessResource ();

            //Critical section
            LocalTime currentTime = LocalTime.now ();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("HH:mm:ss");
            System.out.println (Thread.currentThread ().getName () + " accessed the resource at " + currentTime.format (formatter));

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
