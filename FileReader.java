import java.util.*;
import java.io.*;
/**
 * This FileReader object . . .
 * 
 * @author  
 * @version 
 */
public class FileReader
{
    FileInput file = new FileInput();
    Scanner in = new Scanner(System.in);
    HashTable files;
    ArrayList<Integer> fileNums;
    int n,t;
    public ArrHash reader(int num, int tolerance, File d){
        String directory = d.getName();
        File dir = new File(directory);
        String[] temp = dir.list();
        files = new HashTable(4357);
        fileNums = new ArrayList<Integer>();
        n = num;
        t = tolerance;
        for(int i = 0; i < temp.length; i++)
        {
            String tempy = "";
            String prev = null;
            if(temp[i].endsWith(".txt")){
                File start = new File(dir + "\\" + temp[i]);
                prev = file.read(start);
                tempy = temp[i];

                for(int b = i +1; b < temp.length;b++){ 
                    if(temp[b].endsWith(".txt")){
                        File curr = new File(dir + "\\" + temp[b]);
                        String s = file.read(curr);
                        int tempNum = compare(prev,s);
                        fileNums.add(tempNum);
                        files.put(tempNum, start + "," + temp[b] + ": ");
                        /*
                        System.out.print("[" + tempy +"],[" + temp[b] +"]\t\t");
                        System.out.println("->\t" +compare(prev,s));
                        */
                    }
                }
            }
        }
        Collections.sort(fileNums);
        return ( new ArrHash(fileNums,files));
    }

    public int getNumWords(String s){
        int i = s.indexOf("Words: ");
        if(i == -1){
            String[] temp = s.split("\\s+");
            int wordCount = temp.length;
            return wordCount;
        }
        else
            try{
                int c = Integer.parseInt(s.substring(i+7));
                return c;
            }
            catch(NumberFormatException ex){
                System.out.println("Error 12038: You appear to have two " +
                    "word counts\nCheck files and try again for maximum results.\nWill be using" +
                    " the latter number for this check");
                return getNumWords(s.substring(i+7));
        }
    }

    public int compare(String s1, String s2){
        double x = 0;
        int total = 0;
        String[] temp = s1.split("\\s+");
        //for(int i = 0; i < temp.length; i++){

        for(int i = 0; i < temp.length-1; i+= (n - (n-1))){
            String sub1 = "";
            for(int count = i; count < (n + i)  && count < temp.length; count++){
                if(count != 1)
                    sub1 += temp[count] + " ";
                else
                    sub1+= temp[count];
            }
            if(s2.indexOf(sub1) != -1)
                total++;
        }
        // }
        /*
        x = (total * 100.0 / getNumWords(s1));
        java.text.DecimalFormat df = new java.text.DecimalFormat("###.##");
        try{
        x = df.parse(df.format(x)).doubleValue();
        }
        catch(Exception ex){
        System.out.println("Error 132231323354444521\nBasically you broke it...");
        }
         */
        return total;// + "%";

    }

    public class FileInput {

        public String read(File f){
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            DataInputStream dis = null;
            String str ="";
            try {
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                dis = new DataInputStream(bis);

                while (dis.available() != 0) //checks to see if there are more lines
                {

                    // this statement reads the line from the file and print it to
                    // the console.

                    str+=dis.readLine();
                    //System.out.println(dis.readLine());
                }

                // dispose all the resources after using them.
                fis.close();
                bis.close();
                dis.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            str = str.toLowerCase();
            String s = str.replace(".","");
            return s;
        }
    }
}