/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/

//professor's producer code

import java.util.Random;

public class deposit implements Runnable
{

    private static Random generator = new Random();
    private Brain sharedLocation;
    String tname;

    public deposit( Brain shared, String name )
    {
        sharedLocation = shared;

        tname = name;
    }

    public void run()
    {
        //program will run infinitely until manually stopped

        while ( true )
        {
            try
            {
                sharedLocation.deposit(generator.nextInt(500+1), tname);

                Thread.sleep( generator.nextInt( 600 ) );
            }
            catch ( InterruptedException exception )
            {
                exception.printStackTrace();
            }
        }

    }
}