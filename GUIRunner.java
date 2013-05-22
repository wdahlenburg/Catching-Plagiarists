import java.awt.*;        // using AWT containers and components
import java.awt.event.*;  // using AWT events and listener interfaces
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 * Write a description of class GUIRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIRunner extends JFrame implements ActionListener
{
    JButton run,about,help;
    public GUIRunner(){
        JLabel text = new JLabel("Welcome to Plagiarism Catcher v.2");
        text.setPreferredSize(new Dimension(100,50));
        add(text,BorderLayout.PAGE_START);
        //setLayout(new FlowLayout());
        run = new JButton("Start");
        run.setPreferredSize(new Dimension(200,100));
        add(run,BorderLayout.CENTER);
        about = new JButton("About");
        about.setPreferredSize(new Dimension(200,100));
        add(about,BorderLayout.LINE_START);
        help = new JButton("Help");
        help.setPreferredSize(new Dimension(200,100));
        add(help,BorderLayout.LINE_END);

        run.addActionListener(this);
        about.addActionListener(this);
        help.addActionListener(this);
    }

    public static void main(String [] args){
        GUIRunner gui = new GUIRunner();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setSize(600,300);
        gui.setTitle("Plagiarism Catcher");
        Container pane = gui.getContentPane();
        //pane.setLayout(new GridLayout(1,4));
        gui.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        Runner runner = new Runner();
        if(e.getSource() == run){
            int a = inputNum();
            File f = dirChooser();            
            int tolerance = getTolerance();
            ArrHash arrhash = runner.run(a,f); 
            data d = new data(arrhash,tolerance);
            //d.displayData(arrhash);
        }else if(e.getSource() == about){
            System.out.println(runner.welcomeText());
        }else if(e.getSource() == help){
            System.out.println("We need some help up in here");
        }
    }

    public int inputNum(){
        JOptionPane jop = new JOptionPane("Input");
        String check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below");
        boolean numValid = true;
        int count = 0;
        if(isInteger(check)){
            count = Integer.parseInt(check);
            if(count > 26 || count < 0)
                numValid = false;
        }
        else{
            numValid = false;
        }

        if(!numValid){
            while(true){
                JFrame alert = new JFrame("Alert");
                jop.showMessageDialog(alert,"Sorry try a number that is between 1 and 25");
                check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below");
                if(isInteger(check)){
                    count = Integer.parseInt(check);
                    if(count >1 && count < 26)
                        break;
                }
            }
        }

        return count;
    }

    public int getTolerance(){
        JOptionPane jop = new JOptionPane("Input");
        String check = jop.showInputDialog(null,"Please enter the tolerance");
        boolean numValid = true;
        int count = 0;
        if(isInteger(check)){
            count = Integer.parseInt(check);
            if(count <= 0 )
                numValid = false;
        }
        else{
            numValid = false;
        }

        if(!numValid){
            while(true){
                JFrame alert = new JFrame("Alert");
                jop.showMessageDialog(alert,"Sorry try a number that is greater than or equal to 0");
                check = jop.showInputDialog(null,"Please enter the tolerance");
                if(isInteger(check)){
                    count = Integer.parseInt(check);
                    if(count <= 0 )
                        break;
                }
            }
        }

        return count;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }

    public File dirChooser(){
        JFileChooser jfc = new JFileChooser();

        jfc = new JFileChooser(); 
        jfc.setCurrentDirectory(new java.io.File(".")); //Gets the current directory
        jfc.setDialogTitle("Choose a directory"); 
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //This chooses file only
        jfc.setAcceptAllFileFilterUsed(false);
        //    
        JOptionPane jop = new JOptionPane("Error");
        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) { 
            JFrame alert = new JFrame("Alert");
            if(jfc.getSelectedFile().equals(null))
            {
                jop.showMessageDialog(null,"Sorry try again with a valid file");
                dispose();
            }
        }

        return jfc.getSelectedFile();
    }

    public class data extends JFrame implements ActionListener{
        public data(ArrHash arr,int tolerance){
            String str = "";
            ArrayList<Integer> alist = arr.getArrayList();
            HashTable table = arr.getHashTable();
            for(int num :alist){
                if(num > tolerance)
                    str += table.get(num) + " " + num +  "->\t" + "\n";
            }
            JLabel label = new JLabel("The comparisons:   ");
            setLayout(new FlowLayout());
            add(label);
            JTextArea text = new JTextArea(str);
            text.setRows(40);
            text.setColumns(20);
            text.setPreferredSize(new Dimension(500,600));
            JScrollPane jsp = new JScrollPane(text);
            setBounds(10,100,text.getRows() * 400,text.getColumns() * 400);
            jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            getContentPane().add(jsp);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //add(text,BorderLayout.PAGE_START);
            //add(myScrollPane,BorderLayout.EAST);
            setLayout(new FlowLayout());
            setVisible(true);
        }

        public void displayData(ArrHash arr){
            String str = "";
            ArrayList<Integer> alist = arr.getArrayList();
            HashTable table = arr.getHashTable();
            for(int num : alist){
                str += table.get(num) + " " + num + "\n";
            }
            JTextArea myText = new JTextArea(str);
            myText.setPreferredSize(new Dimension(400,800));
            JScrollPane myScrollPane = new JScrollPane(myText);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myText.setText(str);
            myText.setEditable(false);
            myText.setVisible(true);
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }

}
