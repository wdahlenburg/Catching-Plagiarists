import java.awt.*;        // using AWT containers and components
import java.awt.event.*;  // using AWT events and listener interfaces
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.border.EmptyBorder;
/**
 * GUIRunner takes the Catching Plagiarism problem and puts it in a GUI window. 
 * 
 * @Wyatt Dahlenburg
 * @5/29/13
 */
public class GUIRunner extends JFrame implements ActionListener
{
    JButton run,about,help; //Create Buttons
    public GUIRunner(){
        JLabel text = new JLabel("Welcome to Plagiarism Catcher v.2"); //Give text to the label
        text.setPreferredSize(new Dimension(100,50)); //Create a size for the label
        add(text,BorderLayout.PAGE_START); //Add the label
        
        run = new JButton("Start");                     //Set up a start button
        run.setPreferredSize(new Dimension(200,100));   //Adjust the size
        add(run,BorderLayout.CENTER);                   //Add the button
        
        about = new JButton("About");                   //Set up an about button
        about.setPreferredSize(new Dimension(200,100)); //Adjust the size
        add(about,BorderLayout.LINE_START);             //Add the about button
        
        help = new JButton("Help");                     //Set up the help button
        help.setPreferredSize(new Dimension(200,100));  //Adjust the size
        add(help,BorderLayout.LINE_END);                //Add the help button

        //Have all of the buttons do specific actions
        run.addActionListener(this);                
        about.addActionListener(this);
        help.addActionListener(this);
    }

    public static void main(String [] args){
        GUIRunner gui = new GUIRunner(); //Create a GUIRunner object
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Have the program close if the "X" is clicked
        gui.setSize(600,300); //Set the size
        gui.setTitle("Plagiarism Catcher"); //Set the title
        gui.setVisible(true); //Have the program show up
    }

    public void actionPerformed(ActionEvent e){
        FileReader read = new FileReader(); //Make a FileReader object
        if(e.getSource() == run){ //Check if the event was the run button being pressed
            int freq = inputNum(); //Take a number for the frequency
            File f = dirChooser(); //Get a directory
            int tolerance = getTolerance(); //Get the tolerance
            Data[] data = read.reader(freq,tolerance,f); //Get the sorted data
            Screen scr = new Screen(data); //Create a return screen
            scr.setVisible(true); //Have the screen show up
        }else if(e.getSource() == about){ //Check if the event was the about button being pressed
            Welcome w = new Welcome(); //Create a welcome screen
            w.setVisible(true); //Make the welcome screen appear
        }else if(e.getSource() == help){ //Check if the event was the help button being pressed
            Help helper = new Help(); //Create a help screen
            helper.setVisible(true); //Have the help screen appear
        }
    }
    
    /*
     * This method takes a number. This is done in a GUI input pane.
     */
    public int inputNum(){
        JOptionPane jop = new JOptionPane("Input"); //Set the title
        String check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below"); //Create the dialog box
        boolean numValid = true; //Create a boolean - Assume the number is valid
        int count = 0; //Create a integer object for the string to be passed into
        if(isInteger(check)){ //Check if the string is a number
            count = Integer.parseInt(check); //Converts the string. This fails if it is not a string.
            if((count > 26 || count < 0) || check == null || count == 0) //Set boundries
                numValid = false; //The number does not work
        }
        else{
            numValid = false; //If the string is not a number we want to troubleshoot
        }

        if(!numValid){ //Checks to see if there is a number or not
            while(true){ //Loops until a valid number is entered
                jop.showMessageDialog(null,"Sorry try a number that is between 1 and 25"); //Error message
                check = jop.showInputDialog(null,"Please Insert a number between 1 and 25 below"); //Request input again
                if(isInteger(check)){ //Check if the input is a number
                    count = Integer.parseInt(check); //Pass the string as a number
                    if(count >1 && count < 26) //Check if the number is valid
                        break; //The number is acceptable
                }
            }
        }

        return count; //Return the number
    }

    /*
     * The getTolerance method acts similarily to the inputNum method. This method uses a GUI pop up
     * to request a tolerance. 
     */
    public int getTolerance(){
        JOptionPane jop = new JOptionPane("Input"); //Create a pane
        String check = jop.showInputDialog(null,"Please enter the tolerance"); //Ask the question
        boolean numValid = true; //Assume the number is valid
        int count = 0; //Create a variable the string can get passed into
        if(isInteger(check)){ //Check to see if the string is a number
            count = Integer.parseInt(check); //Convert the string into an integer
            if(count < 0 ) //Check to see if the tolerance is negative
                numValid = false; //This is a bad value so try again
        }
        else{
            numValid = false; //This is not a number, so we need to ask again
        }

        if(!numValid){ //Check to see if the number is positive
            while(true){ //Loop until there is a valid number
                jop.showMessageDialog(null,"Sorry try a number that is greater than or equal to 0"); //Create a popup
                check = jop.showInputDialog(null,"Please enter the tolerance"); //Ask for input
                if(isInteger(check)){ //Check if the new string is a number
                    count = Integer.parseInt(check); //Convert the string into a number
                    if(count >= 0 ) //Check to see if the number is valid
                        break; //Exit
                }
            }
        }

        return count; //Return the number
    }

    public static boolean isInteger(String str) { //Check to see if a string is an integer
        try {
            Integer.parseInt(str); //Use the method parseInt
                                   //An Excpetion will be thrown if it is not
            return true;
        } catch (NumberFormatException nfe) {} //Catch the exception
        return false; //The string is not a number
    }
    
    /*
     * dirChooser is used to graphically select a directory. A JFileChooser is used for this process.
     */
    public File dirChooser(){
        JFileChooser jfc = new JFileChooser(); //Create a JFileChooser object
        jfc.setCurrentDirectory(new java.io.File(".")); //Gets the current directory
        jfc.setDialogTitle("Choose a directory"); //Set the title
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //This chooses directories only
        jfc.setAcceptAllFileFilterUsed(false); //Do not accept all file types
    
        JOptionPane jop = new JOptionPane("Error"); //Create an error screen
        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) { //If the file can not be used
            JFrame alert = new JFrame("Alert"); //Create an error screen
            if(jfc.getSelectedFile().equals(null))
            {
                jop.showMessageDialog(null,"Sorry try again with a valid file");
            }
        }

        return jfc.getSelectedFile(); //Return the appropriate directory
    }

    /**
     * Class Screen is an output screen for the data. A Screen object uses an array of Data objects to 
     * output the information in a nice looking box.
     */
    public class Screen extends JFrame implements ActionListener{
        public Screen(Data[] d){
            setTitle("Comparisons"); //Set the title
            String str = ""; //Create a string to add to
            for(Data temp : d){ //Loop through the data
                String percent = String.format("%.1f",temp.percent) + "%"; //Set the precision to 1 decimal
                str += temp.name + "\t" + temp.hits + " ->\t" + percent + "\n"; //Add to the string
            }
            JTextArea text = new JTextArea(str,12, 40); //Make an area to display the text
            setSize(500,600); //Size the window
            text.setLineWrap(true);
            text.setWrapStyleWord(true);
            text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Have the box close when the 'X' is clicked

            JScrollPane scroller = new JScrollPane(text); //Add a scroller to the text
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //Always have a scroller
            add(scroller); //Add the scroller and text
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }

    /**
     * Class Help displays instructions on how to use this program.
     */
    public class Help extends JFrame implements ActionListener{
        JPanel contentPane;
        public Help() {
            setTitle("Help"); //Set the title
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Have the window close when the 'X' is clicked
            setBounds(100, 100, 700, 650); //Set the size
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); //Create a border
            setContentPane(contentPane);
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

            JTextPane txtpnToStartEnter = new JTextPane(); //Create a text pane
            txtpnToStartEnter.setFont(new Font("Times New Roman", Font.BOLD, 18)); //Set the font and size
            txtpnToStartEnter.setEditable(false); //Do not allow the box to be editable
            
            //Add text to the box
            txtpnToStartEnter.setText("Steps:\r\n\r\n1. Click \"Start\"\r\n2. Enter a word frequency between 1 and 25*\r\n3." +
                "Select a directory to compare (large, medium, or small)\r\n4. Type a positive tolerance**\r\n5." +
                " Wait***\r\n\r\n* -A word frequecy is described as:\r\n\r\nFrequency: 2\r\n\r\nSentance: " +
                "\r\n       The brown fox can jump over logs.\r\n\r\nCompared words:\r\n\t" +
                "The + brown \r\n\tbrown + fox\r\n\tfox + can\r\n\tcan + jump\r\n  \tjump + over \r\n\t" +
                "over + logs\r\n\r\n** -The tolerance is the minimum amount of hits and above to be displayed" +
                "\r\n\r\n***-Times may vary according to number of files and frequency used");
            contentPane.add(txtpnToStartEnter); //Add the text box
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }

    /**
     * Welcome is a very simple box that displays the name of the project and a slogan. 
     */
    public class Welcome extends JFrame implements ActionListener{        
        JPanel contentPane;
        public Welcome(){
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Have the window close on "X" without closing the program
            setBounds(100, 100, 650, 400); //Set up the size
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); //Create a border
            contentPane.setLayout(new BorderLayout(0, 0));
            setContentPane(contentPane);

            JLabel lblWelcomeToPlaga = new JLabel("Welcome to Plagiarism Catcher v.2"); //Create a label
            lblWelcomeToPlaga.setFont(new Font("Shonar Bangla", Font.BOLD, 40)); //Use a special font
            lblWelcomeToPlaga.setHorizontalAlignment(SwingConstants.CENTER); //Place the label in the center
            add(lblWelcomeToPlaga, BorderLayout.NORTH); //Add the label at the top

            JLabel lblNewLabel = new JLabel("The copiers will be found."); //Create a slogan label
            lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 30)); //Use the Monospaced font
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); //Alight the text in the center
            add(lblNewLabel, BorderLayout.CENTER); //Place the text in the center of the screen

            JLabel lblNewLabel_1 = new JLabel("Made by Wyatt Dahlenburg"); //Make a new label
            lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING); //Place the text in the right corner
            add(lblNewLabel_1, BorderLayout.SOUTH); //Add the label
        }

        public void actionPerformed(ActionEvent e){
            setVisible(true);
        }
    }
}
