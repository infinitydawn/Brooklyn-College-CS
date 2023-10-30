/**
 *
 * @author Mikhail Nikulin
 * @since 7-27-2023
 * @verson 1.0.0
 *
 * description: this program calculates the body mass index (BMI) of an adult
 * and provides the weight status of the patient (underweight, normal,
 * overweight, obese). The user has to provide the height (in feet & inches), as
 * well as the weight (in pounds). The height is then converted to inches using
 * "convertToInches" method. Then the converted height is used in the
 * "bmiCalculator" method to produce a BMI. The BMI value is then used in the
 * "weightStatus" method to classify the BMI value. The results are shown to
 * user in the Console as well as saved to a text file.
 *
 * Steps taken: first, I have written convertToInches, bmiCalculator,
 * weightStatus methods according to the strategy. Then I have tested each
 * method and ensured they were working properly. The next step was writing
 * combining all the methods together in main methods, and outputting the
 * prompts and results to the console and text file.
 *
 * Opinion: this program helped me practice using the trailing method and using
 * "if" statements. I liked that this program is something practical, that is
 * used in the real world. I had some errors along the way but they were mostly
 * typos or wrong variables, so it didn't take me long to fix those.
 *
 */
package assignment2;

import java.util.Scanner;
import java.io.PrintStream;

public class Assignment2 {

    public static void main(String[] args) throws Exception {
        int feet, inches, weight;
        double BMI;

        Scanner sc = new Scanner(System.in);
        PrintStream ps = new PrintStream("output.txt");

        System.out.println("BMI Calculator");
        ps.println("BMI Calculator");

        System.out.print("Enter the patient's height (in ft and inches - "
                + "Enter 0 0 to stop): ");
        ps.print("Enter the patient's height (in ft and inches - "
                + "Enter 0 0 to stop): ");
        feet = sc.nextInt();
        inches = sc.nextInt();
        ps.print(feet + " " + inches + "\n");

        while (feet > 0) {
            System.out.print("Enter the patient's weight (in pounds): ");
            ps.print("Enter the patient's weight (in pounds): ");
            weight = sc.nextInt();
            ps.print(weight + "\n");

            while (weight < 50 || weight > 500) {
                System.out.println("Entered weight is invalid. Try Again");
                System.out.print("Enter the patient's weight (in pounds): ");
                ps.println("Entered weight is invalid. Try Again");
                ps.print("Enter the patient's weight (in pounds): ");
                weight = sc.nextInt();
                ps.print(weight + "\n");
            }

            BMI = bmiCalculator(convertToInches(feet, inches), weight);
            String status = weightStatus(BMI);
            System.out.printf("\nHeight: %d feet, %d inches",
                    feet, inches);
            System.out.printf("\nWeight: %d pounds", weight);
            System.out.printf("\nYour BMI is %.1f, indicating your weight"
                    + " is in the %s category for adults of your height\n",
                    BMI, status);
            ps.printf("\nHeight: %d feet, %d inches",
                    feet, inches);
            ps.printf("\nWeight: %d pounds", weight);
            ps.printf("\nYour BMI is %.1f, indicating your weight "
                    + "is in the %s category for adults of your height\n",
                    BMI, status);

            ps.print("---------------------------------------------------\n");
            System.out.print("-----------------------------------------------"
                    + "----\n");
            System.out.print("Enter the patient's height (in ft and inches - "
                    + "Enter 0 0 to stop): ");
            ps.print("\nEnter the patient's height (in ft and inches - "
                    + "Enter 0 0 to stop): ");
            feet = sc.nextInt();
            inches = sc.nextInt();
            ps.print(feet + " " + inches + "\n");
        }
    } // end main

    // converts from feet and inches to inches. Returns the result.
    public static int convertToInches(int feet, int inches) {
        return feet * 12 + inches;
    } // end convertToInches

    // calculates and returns BMI index value based on the height and weight
    public static double bmiCalculator(int height, int weight) {
        return (weight / Math.pow(height, 2)) * 703;
    } // end bmiCalculator

    // returns a string with weight status based on the BMI Value
    public static String weightStatus(double BMI) {
        String status = "";
        if (BMI < 18.5) {
            status = "underweight";
        } else if (BMI >= 18.5 && BMI <= 24.9) {
            status = "normal";
        } else if (BMI >= 25.0 && BMI <= 29.9) {
            status = "overweight";
        } else if (BMI >= 30) {
            status = "obese";
        }

        return status;

    } // end weightStatus()

}
