import java.util.*;
import java.io.*;
/**
 * This Runner object . . .
 * 
 * @author  
 * @version 
 */
public class Runner
{
    public Runner(){
    }
    static Scanner in = new Scanner(System.in);
    public static ArrHash run(int a,int t, File f){
        //System.out.println("\nYour word frequency is " + a);

        FileReader read = new FileReader();
        return read.reader(a,t,f);
    }

    public static int getNum(){
        return 0;
    }

    public static String welcomeText(){
        String str ="";
        str += "**************Welcome to Plagiarism Finder v.2**************\n          *******The copiers will be found.*******\n\nTo start enter a " +
        "number below on how many \nwords you would like to compare simularities too. \n \nEx. \nSentance:" +
        "\nThe brown fox can jump over logs. \nNumber: 2 \n \nThe words that will be compared are: \n" +
        "The + brown \nbrown + fox \nfox + can \ncan + jump \njump + over \nover + logs \n \n";
        return str;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }

}
