import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.String;

public class RLE {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int menuSelection = 0;
        int encodeLength;
        int decodeLength;
        String rleString = "";
        String rleArrayToString;
        char[][] encodeArray = new char[0][];
        char[] decodeArray;
        boolean error = false;
        boolean menuRepeat = false;


           while (!error || menuRepeat) {

                System.out.print("What would you like to do? \n" +
                        "1.  Input string to be encoded \n" +
                        "2.  View encoded string \n" +
                        "3.  View decoded string \n" +
                        "4.  Exit program \n" +
                        "Option:");

                try {
                    menuSelection = scan.nextInt();
                    if (menuSelection < 1 || menuSelection > 4) { // if the number input is out of bounds, loop the menu and ask again
                        System.out.println("");
                        System.out.println("Error! Invalid input. Please enter an integer from 1-4");
                        error = false;
                    } else {
                        error = true;
                    }
                } catch (InputMismatchException e) { // if the input is not a number loop the menu and ask again
                    System.out.println("");
                    System.out.println("Error! Invalid input. Please enter an integer from 1-4");
                    scan.nextLine();
                    error = false;
                }

                System.out.println("");

                switch (menuSelection) {

                    case 1: // Input string to be encoded

                        System.out.print("Enter message: ");
                        rleString = scan.next();

                        break;
                    case 2: // View encoded string

                        encodeLength = findEncodeLength(rleString);
                        encodeArray = new char[encodeLength][];
                        encodeArray = encodeRLE(rleString);
                        System.out.print("The encoded data is: ");
                        System.out.println(multiArrayToString(encodeArray));
                        System.out.println("");

                        break;
                    case 3: // View decoded string

                        rleArrayToString = multiArrayToString(encodeArray);
                        decodeLength = findDecodeLength(rleArrayToString);
                        decodeArray = new char[decodeLength];
                        decodeArray = decodeRLE(rleArrayToString);
                        System.out.print("The decoded data is: ");
                        System.out.println(decodeArray);
                        System.out.println("");

                        break;
                    case 4: // Exit the program

                        System.out.print("Program terminated");
                        System.exit(0);
            }
            menuRepeat = true; // loop the menu when all the actions are executed
        }
    }

    public static String multiArrayToString(char[][] array){ // turns a 2d array into a string
        String output = "";

        if(array == null || array.length == 0){
            return null;
        }

        for(int row = 0; row < array.length; row++){ // row count
            for(int col = 0; col < array[row].length; col++){ // column count
                output += array[row][col]; // concatenates string of array characters
            }
        }

        return output; //returns string
    }

    public static String arrayToString(char[] array){ // turns a 1D array into a string
        String output = "";

        if(array == null || array.length == 0){
            return null;
        }

        for(int row = 0; row < array.length; row++){
            output += array[row]; // concatenates string of array characters
        }

        return output; //returns string
    }


    public static int numOfDigits(int num) {

        if (num == 0){ // if the number is 0, return 0
            return 0;
        }

        String string;
        string = Integer.toString(num); // converts integer to string
        return string.length(); // returns the length of the integer string
    }

    public static char[] toCharArray(int charCount, char strchar) {

        String string = ""; // string initializer
        String charLetter; // converts char to string for easier handling
        String charNum; // converts the character count to a string
        int digitLength; // finds the length of the character count string
        char[] array = new char[0]; // array initializer

        charLetter = Character.toString(strchar);

        if(charCount <= 0){ // if the charcount is less than or equal to 0, return null
            return null;
        } else if (charCount > 1) {
            digitLength = numOfDigits(charCount);
            charNum = Integer.toString(charCount);
            array = new char[digitLength + 1]; // sets size of array to count length plus one space for the character
            string = charNum + charLetter; // creates a string of the count number and character
        } else if (charCount == 1) { // if the character count is one, make an array of size one
            array = new char[1];
            string = charLetter;
        }

        for (int i = 0; i < string.length(); i++){ // fill array with string made from count and/or character
            array[i] = string.charAt(i);
        }

        return array; // return the string as an array
    }

    public static int findDecodeLength(String rleString) {

    String numChar = "0"; // string initializer
    int i = 0; // counter initializer
    int numOfChar = 0; // number of characters
    char tempChar = '\0'; // holds a temporary value to compare to the next value in a string

        if(rleString == null || rleString.equals("")){ // if the string is null or empty, return zero
            return 0;
        } else if (rleString.length() == 1 && Character.isLetter(rleString.charAt(i))){ // if it's only one character, return 1
            return 1;
        }

         while (i < rleString.length()) {
             while (Character.isDigit(rleString.charAt(i))) { // loop around and count number of same characters
                 numChar += Character.toString(rleString.charAt(i));
                 tempChar = rleString.charAt(i);
                 i++;
             }
             if (i == rleString.length() - 1) { // if its the second to last string, store number, break the loop
                 numOfChar += Integer.parseInt(numChar);
                 break;
             } else if(!numChar.equals("")) { // if numChars > 0, turn number string into number value
                 numOfChar += Integer.parseInt(numChar); // add amount of counted characters
                 numChar = ""; // resets numChar
             }

             while (Character.isLetter(rleString.charAt(i))) {
                  if (i == rleString.length() - 1 && !Character.isDigit(tempChar)){ // breaks loop so the index does not go out of bounds.
                     numOfChar += 1;
                     i++;
                     break;
                 } else if (!Character.isDigit(tempChar)) { // if the last character is not a number, count the character as one
                     numOfChar += 1;
                     i++;
                     tempChar = rleString.charAt(i);
                 } else if (i < rleString.length()) { // if its a character after a digit, pass over and bump up the counter;
                     i++;
                     tempChar = rleString.charAt(i);
                 }
             }
         }
         return numOfChar; // return the length of the decoded messages
    }

    public static int findEncodeLength(String inputString)
    {
        int uniqueChar = 0; // counts unique characters as they appear
        char tempValue = '\0'; // temporary char value to compare to others in string

        if(inputString == null || inputString.equals("")){ // if the string is empty, return 0
            return 0;
        }

        for (int i = 0; i < inputString.length(); i++) {
            if (i == 0 || inputString.charAt(i) != tempValue) { // if it's the first char or if the i char doesn't equal the last char, count it
                    uniqueChar++;
                    tempValue = inputString.charAt(i);
                }
            }
            return uniqueChar; // returns length of encoded string
    }

    public static char[] decodeRLE(String rleString) {

        int numOfChar; // number value in front of the letter within a string
        int i = 0; // index of the string
        String charVal = ""; // a letter within the string
        String tempCharVal; // a temporary letter to compare within a loop
        String numChar = "0"; // number in string form


        if(rleString.isEmpty() || rleString.equals("")){ // if the string is empty, return null
            return null;
        }

        while (i < rleString.length()){
            while (Character.isDigit(rleString.charAt(i))) { // if the character is a digit loop until string of the entire number is concatenated
                numChar += Character.toString(rleString.charAt(i));
                i++;
            }
            if(!numChar.equals("0")) // if the number is not 0, turn the number string into a number value
                numOfChar = Integer.parseInt(numChar);
            else
                numOfChar = 1; // if number is 0, set it to 1
            if (i < rleString.length() - 1) // if less than the index, reset numChar to 0
                numChar = "0";

            while (Character.isLetter(rleString.charAt(i))) { // after the number is recorded, loop the string
                tempCharVal = Character.toString(rleString.charAt(i));  // value stored to compare within the next loop
                for (int j = 0; j < numOfChar; j++) { // loop and concatenate the amount of letters according to the number that was in front of the letter
                    charVal += tempCharVal;
                }
                numOfChar = 1; // reset the number
                if (i == rleString.length() - 1) { // if the index is at the end, break the loop so it doesn't go out of index
                    i++;
                    break;
                } else
                    i++;
            }
        }
       return charVal.toCharArray(); // return an array
    }

    public static char[][] encodeRLE(String inputString) {

         int encodeLength; // the length of the encoded string
         int charCount = 0; // counts the amount of letters
         int i = 0; // index counter
         int curIndex = 0; // current index of the 2D array
         char tempChar = '\0'; // a temporary value stored to compare to another value
         char[] charArray; // char array from toCharArray
         char[][] encodeArray; // 2D array that will be returned

        if(inputString == null || inputString.equals("")){ // if the string is null or empty, return null
            return null;
        }

         tempChar = inputString.charAt(0); // initializes temp character
         encodeLength = findEncodeLength(inputString);
         encodeArray = new char[encodeLength][];

                 while (i < inputString.length()){
                     while (i < inputString.length() && inputString.charAt(i) == tempChar) { // while the character at the current index is the
                         charCount += 1;                                                     // same as the temp character, continue counting the character
                         tempChar = inputString.charAt(i);
                         i++;
                     }
                     charArray = toCharArray(charCount, tempChar); // store the amount of characters counted and the character into an array

                     if (i < inputString.length()) // if less than the input string, store a new temp char
                         tempChar = inputString.charAt(i);

                     charCount = 0; // reset character count
                     encodeArray[curIndex] = charArray; //store the toCharArray into the 2D array

                     if (i < inputString.length()) // if less than the input length, bump up the index of the encodeArray
                        curIndex++;
                 }
                 return encodeArray; // return 2D array
    }
}

