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
    ArrayList<Integer> percents;
    int n;
    ArrayList<String> fileString;
    public Data[] reader(int num, int tol, File d){
        String directory = d.getName();
        File dir = new File(directory);
        String[] temp = dir.list();
        files = new HashTable(dir.list().length * dir.list().length);
        fileString = new ArrayList<String>();
        percents = new ArrayList<Integer>();
        n = num;
        for(int i = 0; i < temp.length; i++)
        {
            String[] prev = null;
            if(temp[i].endsWith(".txt")){
                File start = new File(dir + "\\" + temp[i]);
                File startCopy = new File(temp[i]);
                prev = file.read(start).split("\\s+");

                for(int b = i +1; b < temp.length;b++){ 
                    if(temp[b].endsWith(".txt")){
                        File curr = new File(dir + "\\" + temp[b]);
                        String s = file.read(curr);
                        int tempNum = compare(prev,s);

                        if(tempNum > tol){
                            percents.add(getNumWords(prev));
                            fileString.add("[" + startCopy + "]" + ", " + "[" + temp[b] + "]" + ": ");
                            files.put("[" + startCopy + "]" + ", " + "[" + temp[b] + "]" + ": ",tempNum);
                        }
                    }
                }
            }
        }
        Data[] data = new Data[fileString.size()];
        for(int b = 0; b < fileString.size(); b++){
            String s = fileString.get(b);
            double p = ((int)files.get(s) * 1.00 / percents.get(b)) * 100;
            data[b] = new Data(s,(int)files.get(s),p);
        }
        Arrays.sort(data,new revComparator());
        return data;
    }

    public int getNumWords(String[] s){
        int index = -1;
        for(int i = 0; i < s.length; i++)
            if(s[i].equals("words:"))
                index = i;
        if(index == -1){
            int wordCount = s.length;
            return wordCount;
        }
        else
            try{
                int c = Integer.parseInt(s[index+7]);
                return c;
            }
            catch(NumberFormatException ex){
                System.out.println("Error 12038: You appear to have two " +
                    "word counts\nCheck files and try again for maximum results.\nWill be using" +
                    " the latter number for this check");
                return getNumWords(s);
        }
    }

    public int compare(String[] s1, String s2){
        double x = 0;
        int total = 0;

        for(int i = 0; i < s1.length-1; i+= (n - (n-1))){
            String sub1 = "";
            for(int count = i; count < (n + i)  && count < s1.length; count++){
                if(count != 1)
                    sub1 += s1[count] + " ";
                else
                    sub1+= s1[count];
            }
            if(s2.indexOf(sub1) != -1)
                total++;
        }
        return total;

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