/**
 * Data stores the comparisons of two files.
 * 
 * @Wyatt Dahlenburg
 * @5/27/13
 */
public class Data
{
    String name; //A name of the files being compared
    int hits; //The number of hits found between file one and file two
    double percent; //The percent of hits over total words multiplied by 100
    
    public Data(String names, int h ,double percent){
        //Assigns values to proper instance variables
        name = names;
        hits = h;
        this.percent = percent;
    }
}
