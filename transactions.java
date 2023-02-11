/* Name: Aymen Hasnain
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/

//buffer code from professor's Q&A

//interface vs class?
public interface transactions
{
    public void deposit(int value, String name);

    public void withdraw(int value, String name);

    public void auditor(int value, String name);
}
