PROJECT TITLE: Catching Plagiarists
PURPOSE OF PROJECT: Given a folder containing a set of text documents, identify the ones that
                    contain a large amount of similar text-in other words, catch the plagiarists.
VERSION or DATE: 5/27/13
HOW TO START THIS PROJECT: Run the GUIRunner class. Click on "About" to learn about the program.
                           Click on "Start" to run the program. "Help" has instructions and
                           explanations on how the program works.
AUTHOR: Wyatt Dahlenburg
OTHER USER INSTRUCTIONS: This program uses a GUI interface. Three buttons are used at the home
                         screen. They can be clicked on with a mouse. In the start portion of 
                         the program, the frequency and tolerance need to be entered with a 
                         keyboard. The directory can be selected by typing it in or by using a
                         mouse. The output is a text area that is scrollable via mouse.
DESCRIPTION OF YOUR ALGORITHM:

The algorithm is basically set up to go through a folder and search for the text files. Once one file
is found the program makes a list of the words in that file. It then would take another file and
search it for each n-frequency word in the first file. A n-frequency word is "n" amount of words put
together to be a phrase. This phrase is searched for in the file. If it is found then 1 is added to
the total or "hits". This process is done without repeating any of the files. The information is then
sorted in order of priority.  
                               
ESTIMATED RUNNING TIME OF YOUR ALGORTIHM:

Using N number of files and W words per file
Worst Case: O(N^2) + O(W)
Best Case: O(N(N-1)) + O(W)