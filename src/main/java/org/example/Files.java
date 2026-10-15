package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Files {



    public void ExtractionText(String Path ){

        int numLines = 0 ;
        int numWords = 0;
        int numChars = 0;
        int strilength = 0;


        Scanner myFile = null;
        try {
            Scanner file = new Scanner(new File(Path));
            myFile = file;


        }
        catch (FileNotFoundException j) {

            System.out.println("Your file does not exist");
            return;
        }

        while(myFile.hasNextLine()) {
            String curLine = myFile.nextLine();

            int size = curLine.length();
            boolean foundDiv = true; //No words on this line
            boolean foundChar = false; // NO CHARacters yet
            //Current line for words
            for(int i = 0; i < size; i++){

                if (curLine.charAt(i) == ' '){
                    foundDiv = true;
                    foundChar = false; // since last division no chars
                }
                else{
                    // We found cahr
                    foundChar= true;
                    numChars++;


                }
                if (foundChar && foundDiv){
                    numWords++;
                    foundDiv = false; // no divisions since last char


                }

            }
            numLines++;
        }

        // Now we have al information lets print
        myFile.close();
        System.out.println("Number of words is ==  "+ numWords);
        System.out.println("Number of lines is ==  "+ numLines);
        System.out.println("Number of characters is ==  "+numChars);
        System.out.println("Number of stringlenth is ==  "+ strilength);

    }
}
