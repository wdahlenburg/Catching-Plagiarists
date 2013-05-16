import java.io.*;
import java.util.*;
/**
 * Write a description of class temp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class temp
{
    public static void main (String [] args) throws Exception{

        FileInput f = new FileInput();
        FileReader yolo = new FileReader();
        String first = "jrf1109.shtml.txt";
        String second = "sra31.shtml.txt";
        File frr = new File("small\\" + first);
        HashTable tbl = f.read1(frr);

        String s = f.read2(new File("small\\" + second));
        System.out.println(compareAll());
    }

    public static int compareAll() throws Exception{
        FileInput f = new FileInput();
        File dir = new File("small");
        String[] temp = dir.list();
        int total = 0;
        for(int i = 0; i < temp.length; i++)
        {
            String tempy = "";
            HashTable prev = null;
            if(temp[i].endsWith(".txt")){
                File start = new File(dir + "\\" + temp[i]);
                prev = f.read1(start);
                tempy = temp[i];

                for(int b = i + 1; b < temp.length;b++){ 
                    if(temp[b].endsWith(".txt")){
                        String s ="";
                        while((s=f.read2(new File(dir + "\\" + temp[b]))) != null ){
                            if(s!= null)
                                total += compareSingle(prev,s);
                        }
                    }
                }
            }
            System.out.println(total);
        }
        return total;
    }

    public static  int compareSingle(HashTable s1, String s2){
        if(s1 != null && s2 != null && s1.get(s2) != null)
            return 1;
        else return 0;
    }

    public static class FileInput {

        public static HashTable read1(File f) throws Exception{
            Scanner file = new Scanner(f);
            HashTable hash = new HashTable(4357);
            while(file.hasNext())
            {
                String phrase = "";
                // read 'numWords' words from file
                for(int j = 0; j < 4; j++)
                {
                    if(file.hasNext())
                    // strip away all punctuation, and set to lowercase
                        phrase += file.next().replaceAll("[^A-z]","").toLowerCase();
                    else
                        phrase = null; // not enough words at end of file
                }
                if(phrase != null)
                hash.put(phrase,phrase);
            }
            return hash;
        }

        public static String read2(File f) throws Exception{
            Scanner file = new Scanner(f);
            while(file.hasNext())
            {
                String phrase = "";
                // read 'numWords' words from file
                for(int j = 0; j < 4; j++)
                {
                    if(file.hasNext())
                    // strip away all punctuation, and set to lowercase
                        phrase += file.next().replace(".","").toLowerCase();
                    else
                        phrase = null; // not enough words at end of file
                }

                return phrase;
            }
            return null;
        }
    }   

}
