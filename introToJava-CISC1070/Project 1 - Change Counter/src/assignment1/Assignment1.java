/** 
 * @author Mikhail Nikulin
 * @since 7-23-2023
 * @version 1.0
 * 
 * description: this program collects info about the user's coins and
 * then calculates total number of coins, total dollar value, 
 * its penny equivalent. The result is then displayed in console and saved
 * to a text file.
 * 
 * Steps taken: first, import required libraries, create variables,
 * create method to get input, then create a method to print results in console,
 * then create a method to save result to a text file. Finally, call all methods
 * in the main function and troubleshoot any errors.
 * 
 * Opinion: this program was interesting to make and practice Java. I had
 * a couple syntax errors but quickly found solutions. I also had to change
 * my approach to the entire program. Overall great experience. I give 5 stars.
 * 
 */
package assignment1;

import java.util.Scanner;
import java.io.PrintStream;

public class Assignment1 {
    public static int halfDollars, quarters, dimes,
                      nickels, pennies, total;
    public static double value;
    public static int nextEntry = 0;
    public static Scanner sc = new Scanner(System.in);
    public static PrintStream ps;
    
    
    public static void main(String[] args) throws Exception {
        
        ps = new PrintStream("output.txt");
        getInput();
        
        
        
        while(nextEntry > -1){
          getInput();
        }
        
        total = halfDollars + quarters + dimes + nickels + pennies;
        value = halfDollars * 0.5 + quarters * 0.25 + dimes * 0.1 + 
                nickels * 0.05 + pennies * 0.01;
        
        printResult();
        saveResult();
        
    }
    
    // collect data from user
    public static void getInput(){
      System.out.println("The value of your change will be computed.");
      System.out.println("Enter -1 now to stop.");
      
      System.out.println("\nHow Many half dollars do you have? ");
      nextEntry = sc.nextInt();
      
      if(nextEntry > -1){
          halfDollars += nextEntry;
          System.out.println("\nHow Many quarters do you have? ");
          quarters += sc.nextInt();
          System.out.println("\nHow Many dimes do you have? ");
          dimes += sc.nextInt();
          System.out.println("\nHow Many nickels do you have? ");
          nickels += sc.nextInt();
          System.out.println("\nHow many pennies do you have? ");
          pennies = sc.nextInt();
        }
    }
      
      
      
    // Display results in console
    public static void printResult() {
        System.out.println("You entered:    "        +halfDollars+" half dollars\n" +
                           "                "+quarters+" quarters\n"+
                           "                "+dimes+" dimes\n"+
                           "                "+nickels+" nickels\n"+
                           "                "+pennies+" pennies\n");
       
        System.out.printf("The value of your %d coins is $%.2f which "
                + "is equivalent to %.0f pennies.\n", 
                    total, value,value*100);
    }
    
    // Save results to "output.txt"
    public static void saveResult() throws Exception {
        ps.println("You entered:    "        +halfDollars+" half dollars\n" +
                           "                "+quarters+" quarters\n"+
                           "                "+dimes+" dimes\n"+
                           "                "+nickels+" nickels\n"+
                           "                "+pennies+" pennies\n");
        
        ps.printf("The value of your %d coins is $%.2f which "
                + "is equivalent to %.0f pennies.\n", 
                    total, value,value*100);
    }
}
