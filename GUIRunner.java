import java.awt.*;        // using AWT containers and components
import java.awt.event.*;  // using AWT events and listener interfaces
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.border.EmptyBorder;
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
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(600,300);
        gui.setTitle("Plagiarism Catcher");
        Container pane = gui.getContentPane();
        gui.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        FileReader read = new FileReader();
        if(e.getSource() == run){
            int a = inputNum();
            File f = dirChooser();
            if(f == null)
                return;
            int tolerance = getTolerance();
            Data[] data = read.reader(a,tolerance,f); 
            Screen scr = new Screen(data);
            scr.setVisible(true);
        }else if(e.getSource() == about){
            Welcome w = new Welcome();
            w.setVisible(true);
        }else if(e.getSource() == help){
            Help helper = new Help();
            helper.setVisible(true);
        }
    }

    public int inputNum(){
        JOptionPane jop = new JOptionPane("Input");
        String check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below");
        boolean numValid = true;
        int count = 0;
        if(isInteger(check)){
            count = Integer.parseInt(check);
            if((count > 26 || count < 0) || check == null || count == 0)
                numValid = false;
        }
        else{
            numValid = false;
        }

        if(!numValid){
            while(true){
                JFrame alert = new JFrame("Alert");
                alert.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jop.showMessageDialog(alert,"Sorry try a number that is between 1 and 25");
                check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below");
                if(check == null){
                    alert.dispose();
                }
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
            if(count < 0 )
                numValid = false;
        }
        else{
            numValid = false;
        }

        if(!numValid){
            while(true){
                JFrame alert = new JFrame("Alert");
                alert.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jop.showMessageDialog(alert,"Sorry try a number that is greater than or equal to 0");
                check = jop.showInputDialog(null,"Please enter the tolerance");
                if(check == null){
                    alert.dispose();
                }
                if(isInteger(check)){
                    count = Integer.parseInt(check);
                    if(count >= 0 )
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
            }
        }

        return jfc.getSelectedFile();
    }

    public class Screen extends JFrame implements ActionListener{
        public Screen(Data[] d){
            setTitle("Comparisons");
            String str = "";
            for(Data temp : d){
                String percent = String.format("%.1f",temp.percent) + "%";
                str += temp.name + "\t" + temp.hits + " ->\t" + percent + "\n";
            }
            JTextArea text = new JTextArea(str,12, 40);
            setSize(500,600);
            text.setLineWrap(true);
            text.setWrapStyleWord(true);
            text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JScrollPane scroller = new JScrollPane(text);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            add(scroller);
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }

    public class Help extends JFrame implements ActionListener{
        JPanel contentPane;
        public Help() {
            setTitle("Help");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 700, 650);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

            JTextPane txtpnToStartEnter = new JTextPane();
            txtpnToStartEnter.setFont(new Font("Times New Roman", Font.BOLD, 18));
            txtpnToStartEnter.setEditable(false);
            txtpnToStartEnter.setText("Steps:\r\n\r\n1. Click \"Start\"\r\n2. Enter a word frequency between 1 and 25*\r\n3." +
            "Select a directory to compare (large, medium, or small)\r\n4. Type a positive tolerance**\r\n5." +
            " Wait***\r\n\r\n* -A word frequecy is described as:\r\n\r\nFrequency: 2\r\n\r\nSentance: " +
            "\r\n       The brown fox can jump over logs.\r\n\r\nCompared words:\r\n\t" +
            "The + brown \r\n\tbrown + fox\r\n\tfox + can\r\n\tcan + jump\r\n  \tjump + over \r\n\t" +
            "over + logs\r\n\r\n** -The tolerance is the minimum amount of hits and above to be displayed" +
            "\r\n\r\n***-Times may vary according to number of files and frequency used");
            contentPane.add(txtpnToStartEnter);
        }
        
        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }
    
    public class Welcome extends JFrame implements ActionListener{        
        JPanel contentPane;
        public Welcome(){
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 636, 378);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(new BorderLayout(0, 0));
            setContentPane(contentPane);

            JLabel lblWelcomeToPlaga = new JLabel("Welcome to Plagiarism Catcher v.2");
            lblWelcomeToPlaga.setFont(new Font("Shonar Bangla", Font.BOLD, 40));
            lblWelcomeToPlaga.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblWelcomeToPlaga, BorderLayout.NORTH);

            JLabel lblNewLabel = new JLabel("The copiers will be found.");
            lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblNewLabel, BorderLayout.CENTER);

            JLabel lblNewLabel_1 = new JLabel("Made by Wyatt Dahlenburg");
            lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
            add(lblNewLabel_1, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }

}
