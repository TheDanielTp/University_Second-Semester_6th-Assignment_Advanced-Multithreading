package sbu.cs.Semaphore;

public class Operator extends Thread
{

    public Operator (String name)
    {
        super (name);
    }

    @Override
    public void run ()
    {
        for (int i = 0; i < 10; i++)
        {
            Resource.accessResource (); // critical section - a Maximum of 2 operators can access the resource concurrently
            try
            {
                sleep (500);
            }
            catch (InterruptedException e)
            {
                System.out.println (e.getMessage ());
            }
        }
    }
}
