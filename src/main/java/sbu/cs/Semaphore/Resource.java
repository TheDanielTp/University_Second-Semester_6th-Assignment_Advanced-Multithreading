package sbu.cs.Semaphore;

public class Resource
{
    public static void accessResource ()
    {
        try
        {
            Thread.sleep (1000); //Sleep for 1 second
        }
        catch (InterruptedException e)
        {
            System.out.println (e.getMessage ());
        }
    }
}
