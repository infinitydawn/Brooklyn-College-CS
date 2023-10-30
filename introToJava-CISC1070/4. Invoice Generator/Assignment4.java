/**
 *
 * @author Mikhail Nikulin
 * @since 8-5-2023
 * @verson 1.0.0
 *
 * description: this program is used to generate an invoice for a tile store.
 * It uses multiple variables to store area, prices, discounts, taxes, etc...
 * This program uses modular design, so different functions are called by 
 * different methods. The final output is sent to a text file.
 *
 * Steps taken: 1) I wrote pseudo code and planned what the loop would be like.
 * 2) Created variables and imported required libraries. 3) Wrote all required
 * methods and tested them one by one. 4) Put everything together in the main 
 * method and finalize output formatting. 5) Write a method for robust input
 *
 * Opinion: I liked the concept of modular design, I believe it makes it easier
 * to write complex programs like that as different parts of the code will be
 * handled by different methods. One of the biggest issues I had was formatting
 * the output, as well as implement a robust input method to eliminate invalid
 * entries.
 * 
 */
package assignment4;
import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;


public class Assignment4 {
    public static PrintStream ps; 
    
    public static void main(String[] args) throws Exception {
        ps = new PrintStream(new File("output.txt"));
        final double LABOR = 0.30;
        final double TAX = 0.08875;
        double length;
        double width;
        double discount;
        double tileCost;
        
        double tilesPrice;
        double laborPrice;
        double installedPrice;
        double discountValue;
        double subTotal;
        double taxValue;
        double finalTileCost;
        
        
        System.out.print("\nTo stop enter 0 for length and width.");
        System.out.print("\nLength of room (feet)? ");
        length = checkInput();
        System.out.print("\nWidth of room (feet)? ");
        width = checkInput();
        System.out.print("\nCustomer discount (percent)? ");
        discount = checkInput()/100.0;
        System.out.print("\nCost per tile (xxx.xx)? ");
        tileCost = checkInput();
        
        while(length != 0 || width != 0){
            tilesPrice = calcPrice(length, width, tileCost);
            laborPrice = calcLaborCost(length, width, LABOR);
            installedPrice = calcInstalledPrice(laborPrice, 
                    tilesPrice);
            discountValue = calcDiscount(installedPrice, discount);
            subTotal = calcSubTotal(installedPrice, discountValue);
            taxValue = calcTax(subTotal, TAX);
            finalTileCost = subTotal + taxValue;
            printMeasurements(length, width);
            printCharges(tileCost,tilesPrice,
                    LABOR,laborPrice,
                    installedPrice, discount,discountValue,
                    subTotal, taxValue, finalTileCost);
            System.out.print("\nTo stop enter 0 for length and width.");
            System.out.print("\nLength of room (feet)? ");
            length = checkInput();
            System.out.print("\nWidth of room (feet)? ");
            width = checkInput();
            System.out.print("\nCustomer discount (percent)? ");
            discount = checkInput()/100.0;
            System.out.print("\nCost per tile (xxx.xx)? ");
            tileCost = checkInput();
        }
            
    } // end main
    
    // checks input from user: rejects negative values and strings
    public static double checkInput(){
        Scanner sc = new Scanner(System.in);
        double value;

        do {
            while (!sc.hasNextDouble()) {
                String input = sc.next();
                System.out.println("Invalid input: " + input + 
                        " is not a valid number.");
                System.out.print("Enter a positive number: ");
            }
            value = sc.nextDouble();

            if (value < 0) {
                System.out.println("Invalid input: "
                        + "Please enter a positive number or 0.");
            }
        } while (value < 0);

        return value;
    } // end checkInput
    
    // calculates the price of material used
    public static double calcPrice(double length, double width, double tileCost)
    {
        return Math.ceil(length) * Math.ceil(width) * tileCost;
    }
    
    // calculates the cost of labor required
    public static double calcLaborCost(double length, double width, 
            double laborRate){
        return Math.ceil(length) * Math.ceil(width) * laborRate;
    }
    
    // calculates labor + materials cost
    public static double calcInstalledPrice(double laborCost, double tilesCost){
        return laborCost + tilesCost;
    }
    
    // calculates discount amount given
    public static double calcDiscount(double installedPrice, double discount){
        return installedPrice * discount;
    }
    
    // calculates subtotal (material and labor, minus discount amount)
    public static double calcSubTotal(double installedPrice, 
            double discounted){
        return installedPrice - discounted;
    }
    
    // calculates tax amount
    public static double calcTax(double subTotal, double tax){
        return subTotal * tax;
    }
    
    public static void printMeasurements(double length, double width){
        ps.printf("\t\tTHE BROOKLYN COLLEGE TILE STORE\n"
                + "\t\t\tMikhail, Owner\n\n"
                + "\t\t\t MEASUREMENT\n\n"
                + "\t\t   Length \t %-5.1f feet\n"
                + "\t\t   Width \t %-5.1f feet\n"
                + "\t\t   Tiles \t %-5.0f tiles\n\n\n\n\n",
                length, width, Math.ceil(length)*Math.ceil(width));
    } // end printMeasurements()
    
    public static void printCharges(double tileCost, double tilesPrice, 
            double laborRate, double laborCost, double installedPrice, 
            double discountRate, double discountValue, double subTotal,
            double taxValue, double total){
        ps.print("\t\t\t    CHARGES\n\n"
                + "\tDESCRIPTION\t   COST/TILE\tCHARGE/ROOM\n");
        ps.printf("\tTiles    \t   %6.2f \t  $%7.2f\n"
                + "\tLabor    \t   %6.2f \t   %7.2f\n",
                tileCost,tilesPrice,laborRate, laborCost);
        ps.println("\t\t\t\t\t-----------");
        ps.printf("\tINSTALLED PRICE\t\t\t  $%7.2f\n"
                + "\tDiscount    \t  %6.1f%%\t    -%5.2f\n",
                installedPrice,discountRate*100,discountValue);
        ps.println("\t\t\t\t\t-----------");
        ps.printf("\tSUBTOTAL\t\t\t  $%7.2f\n",subTotal);
        ps.printf("\tTax\t\t\t\t   %7.2f\n",taxValue);
        ps.printf("\tTOTAL\t\t\t\t  $%7.2f\n",total);
        ps.print("\n\n\n\n\n\t----------------------"
                + "---------------------\n\n\n\n\n");
    } // end printCharges()
    
} // end class
