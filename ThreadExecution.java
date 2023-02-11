/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ThreadExecution {
    //16 total threads for this project
    public static final int MAX_AGENTS = 16;

    public ThreadExecution()
    {
        //threads needed:
        // 5 deposit threads
        // 10 withdraw
        //1 auditor

    }


    //use fixedthreadpool() & exectutor objects to control threads
    public static void main(String[] args) {

        //Use a FixedThreadPool() and an Executor object to control the threads.
        ExecutorService threads = Executors.newFixedThreadPool( MAX_AGENTS );


        Brain sharing = new Brain();

        //prob best to put everything under a try catch for threads

        try {
            //labeling for top of console
            System.out.println("Deposit threads\t\t\t\t\t\t\t   Withdraw threads \t\t\t\t\t\t  Balance  \t\t\t\t\t\t\t        Transaction Number");

            System.out.println("--------------------\t\t\t\t\t ------------------- \t\t\t\t\t -------------------\t\t\t\t\t\t   --------------------");

            threads.execute( new deposit( sharing, "Agent D1 " ) ); //deposit1

            threads.execute( new withdraw( sharing, "Agent W1 " ) ); // withdraw 1

            threads.execute( new withdraw( sharing, "Agent W2 " ) ); // withdraw 2

            threads.execute( new deposit( sharing, "Agent D2 " ) ); //deposit 2

            threads.execute( new withdraw( sharing, "Agent W3 " ) ); //withdraw 3

            threads.execute( new withdraw( sharing, "Agent W4 " ) );

            threads.execute( new deposit( sharing, "Agent D3 " ) ); //deposit 3

            threads.execute( new withdraw( sharing, "Agent W5 " ) );

            threads.execute( new withdraw( sharing, "Agent W6 " ) );

            threads.execute( new deposit( sharing, "Agent D4 " ) ); //deposit 4

            threads.execute( new withdraw( sharing, "Agent W7 " ) );

            threads.execute( new withdraw( sharing, "Agent W8 " ) );

            threads.execute( new deposit( sharing, "Agent D5 " ) ); //deposit 5

            threads.execute( new withdraw( sharing, "Agent W9 " ) );

            threads.execute( new withdraw( sharing, "Agent W10 " ) );

            //1 auditor thread
            threads.execute(new auditor (sharing, "AUDITOR"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //end program
        threads.shutdown();


    }
}