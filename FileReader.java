import java.util.*;
import java.io.*;
/**
 * FileReader is designed to output a sorted comparison of files in a directory. A frequency, tolerance, and directory is required
 * 
 * @Wyatt Dahlenburg 
 * @5/27/13
 */
public class FileReader
{
    FileInput file = new FileInput(); //Create a FileInput variable
    HashTable files; //Create a hashTable
    ArrayList<Integer> percents; //Create an array that keeps track of the percentages
    int n; //Hold the frequency
    ArrayList<String> fileString; //Hold the file names
    public Data[] reader(int num, int tol, File d){ //Take a frequency, tolerance, and directory
        String directory = d.getName(); //Get the name of the directory
        File dir = new File(directory); //Create a file out of the directory
        String[] temp = dir.list(); //List all of the files
        files = new HashTable(dir.list().length*(dir.list().length - 1)); //Create a hashTable that will be N(N-1) files large
        fileString = new ArrayList<String>();
        percents = new ArrayList<Integer>();
        n = num; 
        for(int i = 0; i < temp.length; i++)
        {
            String[] prev = null; //Create an array to show the first compared file
            if(temp[i].endsWith(".txt")){
                File start = new File(dir + "\\" + temp[i]); //Get the file of the first file to compare
                File startCopy = new File(temp[i]); //Get the name without the directory
                prev = file.read(start).split("\\s+"); //Set prev to an array that is the string split up by spaces

                for(int b = i +1; b < temp.length;b++){ //Loop through the array from this spot and onward
                    if(temp[b].endsWith(".txt")){
                        File curr = new File(dir + "\\" + temp[b]); //Create a second file to be compared too
                        String s = file.read(curr); //Have the second file's name without the directory
                        int tempNum = compare(prev,s); //Get the number of hits between the files

                        if(tempNum >= tol){ //Check to see if the number of hits is greater than or equal to the tolerance
                            percents.add(getNumWords(prev)); //Add the amount of words to percents (Note data is manipulated later)
                            fileString.add("[" + startCopy + "]" + ", " + "[" + temp[b] + "]" + ": "); //A String of the files compared is stored
                            files.put("[" + startCopy + "]" + ", " + "[" + temp[b] + "]" + ": ",tempNum); //The information is stored in the hashTable with the String being the key
                                                                                                          //If the hits are used as the key, collisions occur
                        }
                    }
                }
            }
        }
        Data[] data = new Data[fileString.size()]; //Create an array of Data objects
        for(int b = 0; b < fileString.size(); b++){ //Loop through the files
            String s = fileString.get(b); //Get the current string
            double p = ((int)files.get(s) * 1.00 / percents.get(b)) * 100; //Take the amount of hits divided by the total number of words multiplied by 100 to get the percent copied
            data[b] = new Data(s,(int)files.get(s),p); //Add the new data into the array
        }
        Arrays.sort(data,new revComparator()); //Sort the array data with a custom comparator
        return data; //Return the sorted data
    }

    public int getNumWords(String[] s){
        int index = -1; //Assume the string "words:" can not be found
        for(int i = 0; i < s.length; i++) //Loop through the words
            if(s[i].equals("words:")) //Check to see if the string "words:" can be found
                index = i;  //Set the index
        if(index == -1){ //If "words:" is not found
            int wordCount = s.length; //Set the number of words to the length of the array
            return wordCount;
        }
        else{
            try{ 
                int c = Integer.parseInt(s[index+7]); //Use the total amount of words given
                return c;
            }
            catch(NumberFormatException ex){
                System.out.println("Error 12038: You appear to have two " +
                    "word counts\nCheck files and try again for best results."); //Display an error message if the count was not found
                return -1; //Return -1 to show that the word count was not found
            }
        }
    }

    public int compare(String[] s1, String s2){ //Compare an array of words with a string
        int total = 0; //Create the total hits
        for(int i = 0; i < s1.length-1; i++){ //Loop through all of the words in the array
            String sub1 = ""; //Create a temporary string
            for(int count = i; count < (n + i)  && count < s1.length; count++){ //Go through the array and add the next n-words to be checked 
                if(count != 1) //Check to see if there isn't another word to be added
                    sub1 += s1[count] + " "; //Add the word plus a space
                else //If it is the last word
                    sub1+= s1[count]; //Add the word without a space
            }
            if(s2.indexOf(sub1) != -1) //Check to see if the phrase can be found
                total++; //Update the total
        }
        return total; //Return the total hits
    }

    /**
     * FileInput takes a file and converts it into a String. All of the text is converted into lowercase and and "." are deleted.
     */

    public class FileInput {

        public String read(File f){
            FileInputStream fis = null;             //Creates a FileInputStream Object
            BufferedInputStream bis = null;         //Creates a BufferedInputStream Object
            DataInputStream dis = null;             //Creates a DataInputStream Object
            String str ="";
            try {
                fis = new FileInputStream(f);               //Instantiates the variables
                bis = new BufferedInputStream(fis);
                dis = new DataInputStream(bis);

                while (dis.available() != 0) //Checks to see if there are more lines
                {
                    str += dis.readLine(); //Adds the current line to the string
                }

                //Dispose all the resources after using them.
                fis.close();
                bis.close();
                dis.close();

            } catch (FileNotFoundException e) { //Catch exceptions
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            str = str.toLowerCase(); //Convert the string to lowercase
            String s = str.replace(".",""); //Remove any "." from the string
            return s; //Return the string
        }
    }
}