package sbu.cs.Semaphore;
import java.util.concurrent.Semaphore;

public class Controller
{
    public static final Semaphore semaphore = new Semaphore(2);

    public static void main (String[] args)
    {
        Operator operator1 = new Operator ("operator1");
        Operator operator2 = new Operator ("operator2");
        Operator operator3 = new Operator ("operator3");
        Operator operator4 = new Operator ("operator4");
        Operator operator5 = new Operator ("operator5");

        operator1.start ();
        operator2.start ();
        operator3.start ();
        operator4.start ();
        operator5.start ();
    }
}
