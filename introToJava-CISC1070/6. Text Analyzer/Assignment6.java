/**
 *
 * @author Mikhail Nikulin
 * @since 8-16-2023
 * @verson 1.0.0
 *
 * description: this program can process a string, store it as an array and then
 * produce the following statistics: find frequency of each letter in the text.
 * Sort the data by the frequency of each letter, count the number of words that
 * have the same length (not longer than 10) and store them in array. The result
 * is finally printed to a text file with appropriate format. The input is 
 * also taken from a text file.
 *
 * Steps taken: 1) write pseudo code, 2) Write method for reading from file, 
 * 3) write a method for calculation of the number of letters
 * 4) Write bubble sort, 5) write code for word count and frequency count. 
 * 6) Write print methods and test each other method. 7) Put everything together
 * in the main method
 * 
 * 
 * Opinion: this program seems easier than the last one. I got to practice 
 * modular design and word processing, which I liked. Overall an interesting 
 * piece of code to work on. I only had some problems with counting the word
 * frequencies.
 *
 */

package assignment6;

import java.io.File;
import java.util.Scanner;
import java.io.PrintStream;


public class Assignment6 {

    public static PrintStream ps;

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        ps = new PrintStream(new File("output.txt"));

        int[] letterFrequency = new int[26];
        int[] letterIndex = new int[26];
        //int [] words = new int[10];
        final int MAXWORDLENGTH = 10;

        String text = readFile("input.txt");
        countLetters(text, letterFrequency, letterIndex);

        ps.println("Letter Frequency\n----------------");
        displayArray(letterIndex, letterFrequency);

        // can be used for any number of arrays
        int[][] trailArrays = {letterIndex};
        bubbleSort(trailArrays, letterFrequency);

        ps.println("\n\n\nLetter Frequency (Ascending)\n----------------"
                + "------------");
        displayArray(letterIndex, letterFrequency);

        int length = countWords(text);
        String[] words = parseWords(text, length);

        ps.println("Total words in text: " + words.length);

        int[] wordFreq = calcLengthFreq(words, MAXWORDLENGTH);

        ps.printf("\n %9s | %9s\n -----------------------\n",
                "Word Length", "Frequency");
        for (int i = 1; i < wordFreq.length; i++) {
            ps.printf(" %9d   | %6d\n", i, wordFreq[i]);
        }

        ps.println("\n\n\n TEXT 10 Words Per Line:"
                + "\n -----------------------");
        printText(text, 10, length);
    } // end main()

    // reads text file and populates String
    public static String readFile(String fileName) throws Exception {
        Scanner sc = new Scanner(new File(fileName));
        String text = "";

        while (sc.hasNext()) {
            text += sc.nextLine() + "\n";
        }

        return text;
    } // end readFile

    // counts how many times each letter occurs in text
    public static void countLetters(String text, int[] letters,
            int[] letterIndex) {
        for (int i = 0; i < letters.length; i++) {
            letterIndex[i] = i + 65;
        }

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                int currentLetter = Character.toUpperCase
                                                (text.charAt(i)) - 'A';
                letters[currentLetter]++;
            }
        }
    } // end countLetters()

    // prints the array
    public static void displayArray(int[] letterIndex, int[] letters) throws
            Exception {

        for (int i = 0; i < letters.length; i++) {
            ps.printf("%4s | %4d\n",
                    (char) (letterIndex[i]),
                    letters[i]);
        }
        ps.println();
    } // end displayArray

    // duplicates an array
    public static int[] copyArray(int[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    } // end copyArray()

    // sorts any number of arrays using reference array in ascending oreder
    public static void bubbleSort(int[][] arrays, int[] reference) {

        boolean swapped = true;

        for (int passes = 0; passes < reference.length - 1 && swapped; passes++) 
        {
            swapped = false;

            for (int j = 0; j < reference.length - 1 - passes; j++) {
                if (reference[j] > reference[j + 1]) {
                    int temp = reference[j];
                    reference[j] = reference[j + 1];
                    reference[j + 1] = temp;

                    for (int k = 0; k < arrays.length; k++) {
                        temp = arrays[k][j];
                        arrays[k][j] = arrays[k][j + 1];
                        arrays[k][j + 1] = temp;
                    }

                    swapped = true;
                }
            }

        }
    } // end bubbleSort()

    // Counts how many words are in a string
    public static int countWords(String text) {
        int wordCount = 0;
        boolean insideWord = false;

        for (char c : text.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                if (!insideWord) {
                    insideWord = true;
                    wordCount++;
                }
            } else {
                insideWord = false;
            }
        }

        return wordCount;
    } // end countWords()

    // parses words from String into an array
    public static String[] parseWords(String text, int wordsCount) {
        String[] words = new String[wordsCount];
        String word = "";
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                word += text.charAt(i);

            } else if (word.length() != 0) {
                words[count] = word;
                word = "";
                count++;
            }
        }

        return words;
    } // end parseWords()

    // Calculates how often each word length appears in text
    public static int[] calcLengthFreq(String[] words, int MAX) {
        //index is word length, value is frequency
        int[] frequency = new int[MAX + 1];

        for (int i = 0; i < words.length; i++) {
            frequency[words[i].length()]++;
        }

        return frequency;
    } // calcLengthFreq()

    // prints text certain amount of words per line
    public static void printText(String text, int wordsPerLine, int totalWords) 
            throws Exception {

        String word = "";
        String textFormatted = " " + text.replaceAll("\\n", " ");
        int count = 0;
        int lastIndex = 0;

        for (int i = 0; i < textFormatted.length(); i++) {
            if (Character.isLetter(textFormatted.charAt(i))) {
                word += textFormatted.charAt(i);

            } else if (word.length() != 0) {
                word = "";
                count++;
            }

            if ((count == wordsPerLine || i == textFormatted.length() - 1)
                    && Character.isWhitespace(textFormatted.charAt(i))) 
            {
                ps.printf("%s\n", textFormatted.substring
                                                (lastIndex, i));
                lastIndex = i;

                totalWords -= count;

                count = 0;
            }
        }
    } // end printText
}
