/**
 *
 * @author Mikhail Nikulin
 * @since 8-5-2023
 * @verson 1.0.0
 *
 * description: this program is used to process the results of four tests taken
 * by a class and produce sorted views and statistics reflecting averages of 
 * each student, mean, mode, median, and standard deviation of test results per
 * class. The output is then formatted to be sent to a text file.
 *
 * Steps taken: 1) made a data file. 2) Make a method to populateArrays. 3) make
 * a method to get averages. 4) make a print method to display class grades.
 * 5) make a method to sort all results by its average. 6) write methods for
 * getting mode,standard deviation, median, and mean. 7) make a method for
 * dropping the lowest grade. 8) Write all methods and additional headings in
 * the main function. 9) Test and make necessary adjustments to functions.
 *
 * Opinion: I believe this program requires more knowledge of algorithms than 
 * we currently have. Biggest issues were getting the mode if there is two or 
 * more highest occurring numbers, and dropping the lowest score. I spent a lot
 * of time trying to figure these out, and I think there is a better way to do
 * that, we just don't know the best approach yet.
 *
 */
package assignment5;

import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;


public class Assignment5 {
    
    public static PrintStream ps;

    public static void main(String[] args) throws Exception {
        
        ps = new PrintStream(new File("output.txt"));
        final int CLASSMAXSIZE = 11;
        
        
        String[] names = new String[CLASSMAXSIZE];
        int[] test1 = new int[CLASSMAXSIZE];
        int[] test2 = new int[CLASSMAXSIZE];
        int[] test3 = new int[CLASSMAXSIZE];
        int[] test4 = new int[CLASSMAXSIZE];
        double[] average = new double[CLASSMAXSIZE];
        int[] fake = {1, 1, 2, 2, 5, 6, 8};
        populateArrays(names, test1, test2, test3, test4, CLASSMAXSIZE);
        calcMedian(fake);
        average = getAverage(test1, test2, test3, test4, CLASSMAXSIZE);
        printArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);
        
        int studentCount = names.length;
        
        ps.println("Total students in class: "+studentCount+"\n\n\n\n");
        ps.println("SORTED BY AVERAGE");
        ps.println("-----------------\n");
        sortArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);
        printArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);

        // statistics
        ps.println("\n\n\n\n\n\nCLASS STATISTICS");
        ps.println("-----------------\n");
        printStats(test1, test2, test3, test4);

        ps.println("SORTED BY AVERAGE (ORIGINAL)");
        ps.println("----------------------------\n");
        printArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);

        

        // drop score
        for(int i = 0; i < test1.length; i++){
            int [] studentCard = {test1[i],test2[i],test3[i],test4[i]};
            
            dropLowest(studentCard);
            
            test1[i] = studentCard[0];
            test2[i] = studentCard[1];
            test3[i] = studentCard[2];
            test4[i] = studentCard[3];
        }
        
        // recalculate average
        average = getAverage(test1, test2, test3, test4, CLASSMAXSIZE);
        
        sortArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);
       
        ps.println("LOWEST SCORE DROPPED");
        ps.println("-----------------\n");
        printArrays(names, test1, test2, test3, test4, average, CLASSMAXSIZE);
        
        

    }

    // reads data from file and populates the arrays
    public static void populateArrays(
            String[] names, int[] test1, int[] test2,
            int[] test3, int[] test4, int MAX) throws Exception {
        
        Scanner sc = new Scanner(new File("data.txt"));
        ps.println("Starting to read the data file...\n");
        int count = 0;
        for (int i = 0; i < MAX; i++) {
            names[i] = sc.next() + " " + sc.next();
            test1[i] = sc.nextInt();
            test2[i] = sc.nextInt();
            test3[i] = sc.nextInt();
            test4[i] = sc.nextInt();
            count+= 1;
        }
        
        ps.println("Reached the end of the data file. Total rows read: "
        +count+"\n");

    } // end populateArrays()

    // prints the table with the data for students
    public static void printArrays(
            String[] names, int[] test1, int[] test2,
            int[] test3, int[] test4, double[] average, int MAX) throws Exception {
        
        ps.printf("%-20s | %-11s %-11s %-11s %-11s %-11s\n"
                + "%-20s | %-11s %-11s %-11s %-11s %-11s\n",
                "Name", "Test1", "Test2", "Test3",
                "Test4", "Average",
                "----", "----", "----",
                "----", "----", "----");
        // prints names
        for (int i = 0; i < MAX; i++) {
            ps.printf("%-20s | ",
                    names[i]);
            
          
                ps.printf("%-11s %-11s %-11s %-11s %-11.2f\n",
                        test1[i] == 0 ? "***" : Integer.toString(test1[i]),
                        test2[i] == 0 ? "***" : Integer.toString(test2[i]), 
                        test3[i] == 0 ? "***" : Integer.toString(test3[i]),
                        test4[i] == 0 ? "***" : Integer.toString(test4[i]), 
                        average[i]);
            
        }
        ps.print("\n\n\n");
    } // end printArrays()

    // calculates and returns an array of averages per student
    public static double[] getAverage(int[] test1, int[] test2,
            int[] test3, int[] test4, int MAX) {
        double[] average = new double[MAX];

        for (int i = 0; i < MAX; i++) {
            if (test1[i] == 0 || test2[i] == 0 || test3[i] == 0
                    || test4[i] == 0) {
                average[i] = (test1[i] + test2[i] + test3[i] + test4[i]) / 3.0;
            } else {
                average[i] = (test1[i] + test2[i] + test3[i] + test4[i]) / 4.0;
            }

        } // end i loop

        return average;
    } // end getAverage()

    // sorts an array in descending order based on average value
    public static void sortArrays(
            String[] names, int[] test1, int[] test2,
            int[] test3, int[] test4, double[] average, int MAX) {

        boolean swapped = true;

        for (int passes = 0; (passes < MAX - 1) && swapped; passes++) {
            swapped = false;

            for (int index = 0; index < MAX - 1 - passes; index++) {
                if (average[index] < average[index + 1]) {
                    int tempInt;
                    double tempDouble;
                    String tempString;

                    // Swap averages
                    tempDouble = average[index];
                    average[index] = average[index + 1];
                    average[index + 1] = tempDouble;

                    // Swap names
                    tempString = names[index];
                    names[index] = names[index + 1];
                    names[index + 1] = tempString;

                    // Swap test 1 results
                    tempInt = test1[index];
                    test1[index] = test1[index + 1];
                    test1[index + 1] = tempInt;

                    // Swap test 2 results
                    tempInt = test2[index];
                    test2[index] = test2[index + 1];
                    test2[index + 1] = tempInt;

                    // Swap test 3 results
                    tempInt = test3[index];
                    test3[index] = test3[index + 1];
                    test3[index + 1] = tempInt;

                    // Swap test 4 results
                    tempInt = test4[index];
                    test4[index] = test4[index + 1];
                    test4[index + 1] = tempInt;

                    swapped = true;
                }
            } // end index loop
        } // end passes loop

    } // end sortArrays()

    //calculates and returns a mean value of test results
    public static double calcMean(int[] testScores) {
        double accumulate = 0;

        for (int i = 0; i < testScores.length; i++) {
            accumulate += testScores[i];
        } 
     
        return accumulate / testScores.length;
    } // end calcMean()

    // calculates and returns a mode value of test results
    public static double calcMode(int[] dataset) {
        // Find the maximum value in the dataset
        int max = dataset[0];
        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i] > max) {
                max = dataset[i];
            }
        }

        // Create an array to store the frequency of each number
        // array subscript will be the number and value is its frquency
        int[] frequencies = new int[max + 1];

        // Find the frequency of each number
        for (int i = 0; i < dataset.length; i++) {

            frequencies[dataset[i]]++;
        }

        int maxFrequency = 0;
        int count = 0;

        // Find the maximum frequency
        for (int frequency : frequencies) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                count = 1;
            } else if (frequency == maxFrequency) {
                count++;
            }
        }

        // Create an array to store the modes
        int[] modes = new int[count];
        count = 0;

        // Find the modes
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] == maxFrequency) {
                modes[count] = i;
                count++;
            }
        }

        double average = 0;

        for (int mode : modes) {
            average += mode;
        }
        average = average / modes.length;

        return average;

    } // end calcMode()

    // calculates median of an array
    public static double calcMedian(int[] scores) {
        int[] sortedArr = medianSort(scores, scores.length);

        if (sortedArr.length % 2.0 == 0) {
            return (sortedArr[sortedArr.length / 2]
                    + sortedArr[sortedArr.length / 2 - 1]) / 2.0;

        } else {
            return sortedArr[sortedArr.length / 2];
        }

    } // eend calcMedian()

    // calculates and returns Standard deviation of test results 
    public static double calcStandardDev(int[] scores) {
        int[] sortedArr = medianSort(scores, scores.length);

        double average = 0;
        double accumulate = 0;

        for (int i = 0; i < sortedArr.length; i++) {
            average += sortedArr[i];
        }

        average = average / sortedArr.length;

        for (int i = 0; i < sortedArr.length; i++) {
            accumulate += Math.pow((sortedArr[i] - average), 2);
        }

        return Math.sqrt(accumulate / sortedArr.length);
    }

    // print a table of statistics (mean, mode, median, standard dev) per test
    public static void printStats(int[] test1, int[] test2,
            int[] test3, int[] test4) {
        ps.printf("%10s |%10s |%10s |%10s |%10s |\n",
                "Stat", "Mean", "Mode", "Median", "SD");
        ps.print("------------------------------------------------------------\n");
        ps.printf("%10s |%10.2f |%10.0f |%10.0f |%10.4f |\n",
                "Test 1", calcMean(test1),
                calcMode(test1),
                calcMedian(test1),
                calcStandardDev(test1));
        ps.printf("%10s |%10.2f |%10.0f |%10.0f |%10.4f |\n",
                "Test 2", calcMean(test2),
                calcMode(test2),
                calcMedian(test2),
                calcStandardDev(test2));
        ps.printf("%10s |%10.2f |%10.0f |%10.0f |%10.4f |\n",
                "Test 3", calcMean(test3),
                calcMode(test3),
                calcMedian(test3),
                calcStandardDev(test3));
        ps.printf("%10s |%10.2f |%10.0f |%10.0f |%10.4f |\n\n\n\n",
                "Test 4", calcMean(test4),
                calcMode(test4),
                calcMedian(test4),
                calcStandardDev(test4));
    } // end printStats()

    // Sorts a single array in descending order (bubble sort)
    public static int[] medianSort(int[] scores, int n) {
        int[] sortedArray = new int[scores.length];
 
        
        for (int i = 0; i < n; i++) {
            sortedArray[i] = scores[i];
        }

        boolean swapped = true;
        for (int passes = 0; (passes < n - 1) && swapped; passes++) {
            swapped = false;
            for (int index = 0; index < n - 1 - passes; index++) {
                if (sortedArray[index] > sortedArray[index + 1]) {
                    int temp = sortedArray[index];
                    sortedArray[index] = sortedArray[index + 1];
                    sortedArray[index + 1] = temp;
                    swapped = true;
                }
            }
        }

        return sortedArray;
    } // end medianSort()

    // takes in an array and replaces the lowest score in it with a 0
    public static void dropLowest(int[] studentScores) {
        int indexLow = 0;
        int scoreLow = studentScores[0];

        for (int i = 1; i < studentScores.length; i++) {
            if (studentScores[i] < scoreLow) {
                indexLow = i;
                scoreLow = studentScores[i];
            }
        }
        studentScores[indexLow] = 0;
    } // end dropLowest()
} // end of Class
