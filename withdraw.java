/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/

//professor's consumer code

import java.util.Random;

public class withdraw implements Runnable
{

    //need threads to run at random but can't overpower the other
    //all threads have equal priority
    private static Random random = new Random();
    private Brain sharedLocation;
    String temp;


    public withdraw( Brain shared, String name )
    {
        temp = name;
        sharedLocation = shared;


    }

    public void run()
    {

        //program will run infinitely until manually stopped
        while (true)
        {
            try
            {
                sharedLocation.withdraw(random.nextInt(99+1), temp);


                Thread.sleep( random.nextInt( 100 ) );
            }
            catch ( InterruptedException exception )
            {
                exception.printStackTrace();
            }
        }

    }
}