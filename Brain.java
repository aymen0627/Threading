/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
//Do NOT use own locks
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.time.LocalDateTime;
//must use locks for this project
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.io.IOException;
import java.io.FileWriter;
public class Brain implements transactions{

    //look at professors synchronizedbuffer code
    private final Lock controlLock = new ReentrantLock();

    //lets withdraw know that a deposit has been made
    private final Condition despositable = controlLock.newCondition();

    //flagged transactions if desposit over 350 and withdraw over 75
    //using variables for code readability, really don't need them though
    private static int Flag_Withdraw = 75;
    private static int Flag_Deposit= 350;




    //running balance through program, starts at 0
    private int Balance = 0;

    //counters for running # of transactions and # transactions after each audit
    private int auditorCounter = 0;
    private int transactionCounter = 0;

    //for flagged log file
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a");
    private final LocalDateTime now = LocalDateTime.now();
    String dateTimeString = now.format(formatter);

    //depositor thread
    @Override
    public void deposit(int amount, String name) {


        controlLock.lock();

        //display to terminal thread info
        try
        {
            //increase counters to keep track of transactions
            transactionCounter++;
            auditorCounter++;

            Balance += amount;


            //display deposit amount
            System.out.println( name + " Deposited: $" + amount + ".00 USD" +
                    "\t\t\t\t\t\t\t\t\t\t\t(+)Account Balance: $" + Balance + ".00 USD\t\t\t\t\t\t\t\t\t\t" + transactionCounter);


            //if deposit amount is over the set val of 350, display to terminal of flag & report to log file
            if(amount > Flag_Deposit)
            {
                //keep track of that trandaction number
                int flaggedCounter = transactionCounter;

                System.out.println("*** Flagged Transaction - " + name + "Made A Deposit In Excess of $" + amount + ".00 USD - See Flagged Transaction Log ***");

                //send to flagged file for deposit
                Report(amount, name, "D", flaggedCounter);

            }

            //wakes up all threads
            despositable.signalAll();
        }
        catch(Exception e)
        {
            System.out.println("error in deposit thread");
        }
        finally
        {
            //unlock thread
            controlLock.unlock();
        }

    }

    //withdraw thread
    @Override
    public void withdraw(int amount, String name) {

        controlLock.lock();

        //display to terminal thread info
        try
        {
            //print to terminal if withdraw over account balance
            if(amount > Balance)
            {

                System.out.println("\t\t\t\t\t\t\t\t\t" + name + "Withdraws $" + amount + "\t\t\t(******)WITHDRAW BLOCKED - INSUFFICIENT FUNDS :(");

            }
            else
            {

                transactionCounter++;

                auditorCounter++;
                //update running balance
                Balance -= amount;

                System.out.println( "\t\t\t\t\t\t\t\t\t" + name + " Withdraws: $" + amount + ".00 USD"
                 + "\t\t     (-)Account Balance: $" + Balance + ".00 USD\t\t\t\t\t\t\t\t\t" + transactionCounter);

                //if withdraw amount is over the flagged 75 set value, display to terminal and send to report log
                if(amount > Flag_Withdraw)
                {

                    //track that transaction number that's flagged and display to report
                    int flaggedCounter = transactionCounter;

                    System.out.println("*** Flagged Transaction - " + name + "Made A Withdrawal In Excess of $" + amount + ".00 USD - See Flagged Transaction Log ***");

                    //send to report function
                    Report(amount, name, "W", flaggedCounter);

                }
            }
            despositable.signalAll();
        }
        catch(Exception e)
        {
            System.out.print("error in withdraw thread");
        }
        finally
        {
            //remember to unlock when finished
            controlLock.unlock();
        }

    }

    //auditor thread
    @Override
    public void auditor(int value, String name) {
        //simply track and display running account balance at random intervals.
        //also tracks number of transactions since last audit

        controlLock.lock();

        System.out.println("**********************************************************************************************************************************************************************\n");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\tAUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE:");


        System.out.println("\t\t\t\t\t\t\t\t\t\t\tAccount Balance: $" + Balance + ".00 USD");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\tNumber of transactions since last audit: " + auditorCounter + "\n");

        System.out.println("**********************************************************************************************************************************************************************\n");
        auditorCounter = 0;


        controlLock.unlock();

    }

    //function to print flagged deposits and withdraws to a logged file
    public void Report(int amount, String thread, String flaggedType, int flaggedCounter) throws IOException
    {



        FileWriter report = new FileWriter("transactions.txt",true);


        try
        {

            //if transaction type was a withdrawal, print as
            if(flaggedType.equals("W"))
            {


                report.write("Withdrawal " + thread + " issued a withdrawal of $" + amount + ".00 at " + dateTimeString + " EST" + " Transaction Number : " + flaggedCounter + "\n");
            }
            //else print as deposit
            else
            {


                report.write("Depositor " + thread + " issued a deposit of $" + amount + ".00 at " + dateTimeString + " EST" + " Transaction Number : " + flaggedCounter + "\n");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error at log file");
        }
        finally
        {
            report.close();
        }
    }

}
