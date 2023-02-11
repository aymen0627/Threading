/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/

import java.util.Random;

/*
The auditor agent
will also have normal priority and will simply run less frequently than the
depositor and withdrawal threads
 */

//randomly audits for balance and tracks # of transactions since last audit
public class auditor implements Runnable{
    private Brain tshared;
    String tname;

    private static Random generator = new Random();

    //may not need this
    public auditor(Brain shared, String name)
    {
        tname = name;
        tshared = shared;
    }


    public void run() {

        while(true)
        {
            try
            {

                tshared.auditor(generator.nextInt(1000+1), tname);
                //stays asleep longer than deposit and withdraw threads
                Thread.sleep( generator.nextInt( 720 ) );
            }
            catch ( InterruptedException exception )
            {
                exception.printStackTrace();
            }

        }

    }
}
