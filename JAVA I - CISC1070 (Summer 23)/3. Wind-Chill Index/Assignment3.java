/**
 *
 * @author Mikhail Nikulin
 * @since 7-27-2023
 * @verson 1.0.0
 *
 * description: this program is used to read data set of temperatures from a
 * file to calculate the wind chill index per each data set, and then calculate
 * the averages of temperature, wind speed, and wind chill index. If temperature
 * is presented in celcius it is converted to fahrenheight. Data sets with 
 * airspeed over 120mph and/or temperature higher than 50F will be rejected. 
 * Each data set is printed out to a file along with it's number and wind chill
 * index. Once there are no more data sets in the file, the program calculates 
 * and displays the average values of all data sets.
 *
 * Steps taken: Firstly, I wrote the Pseudo code in main function. Then I 
 * wrote my import statements and setup the while loop to read from file. 
 * The next step was writing the functions and testing them one by one to ensure
 * smooth operation. Finally, I updated my code to save results to a text file.
 *
 * Opinion: I believe it's an interesting approach to calculate what the 
 * temperature feels like. I wonder how accurate is it in the real world. 
 * For the most part, this assignment was completed without any problems except
 * I made some minor adjustments to the variable types as calculations were off
 * when I calculated the averages.
 * 
 */
package assignment3;

import java.io.File;
import java.util.Scanner;
import java.io.PrintStream;

public class Assignment3 {

    public static void main(String[] args) throws Exception {
        String tempType;
        double temperature;
        double windChillIndex;
        double airSpeed;
        int counter = 0;
        double sumAirSpeed = 0;
        double sumTemperature = 0;
        double sumWindChillIndex = 0;

        Scanner sc = new Scanner(new File("weather.txt"));
        PrintStream ps = new PrintStream("output.txt");

        while (sc.hasNext()) {
            temperature = sc.nextInt();
            tempType = sc.next();
            airSpeed = sc.nextInt();

            if (tempType.equals("C")) {
                temperature = convertToFahrenheight(temperature);
            }

            if (isValid(temperature,airSpeed)) {
                counter += 1;
                windChillIndex = getWindChillIndex(temperature, airSpeed);

                sumAirSpeed += airSpeed;
                sumTemperature += temperature;
                sumWindChillIndex += windChillIndex;

                ps.printf("Dataset #%d\n", counter);
                ps.printf("| %-11s | %-9s | %-15s \n",
                    "Temperature", "Air Speed", "Wind Chill Index");
                ps.printf("| %8.1f °F | %5.0f mph | %5.2f °F \n\n",
                        temperature, airSpeed, windChillIndex);
            } else {
                if (temperature > 50) {
                    ps.printf("Data set (%.0fF %.0fmph) was rejected "
                            + "because the temperature is higher than 50F\n\n",
                            temperature, airSpeed);
                }
                if (airSpeed > 120) {
                    ps.printf("Data set (%.0fF %.0fmph) was rejected "
                            + "because the air speed is higher than 120mph\n\n",
                            temperature, airSpeed);
                }
                
            }
        }
        double averageTemperature = sumTemperature / (double)counter;
        double averageAirSpeed = sumAirSpeed / (double)counter;
        double averageWindChill = sumWindChillIndex / (double)counter;

        ps.println("There is no more data in the file.\n");

        ps.printf("| %-15s | %-13s | %-15s \n",
        "Avg Temperature", "Avg Air Speed", "Avg Wind Chill Index");
        ps.printf("| %12.2f °F | %9.1f mph | %14.2f °F\n",
             averageTemperature, averageAirSpeed, averageWindChill);

    } // end main

    // converts from celcius value to fahrenheight
    public static double convertToFahrenheight(double degreesCelsius) {
        return degreesCelsius * 9 / 5 + 32;
    }
    
    // returns true if the values are valid
    public static boolean isValid(double temperature, double airSpeed){
        return !(temperature > 50 || airSpeed > 120);
    }

    // calculates the wind chill index using temperature and air speed
    public static double getWindChillIndex(double temperature, double airSpeed) 
    {
        return 35.74 + 0.6215 * temperature
                - 35.75 * Math.pow(airSpeed, 0.16)
                + 0.4275 * temperature * Math.pow(airSpeed, 0.16);
    }

}
