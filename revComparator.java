import java.util.Comparator;
/**
 * revComparator is a custom Comparator that takes data and sorts it in reverse order
 * 
 * @Wyatt Dahlenburg
 * @5/27/13
 */
public class revComparator implements Comparator <Data>
    {
        public int compare(Data one, Data two){ //Compares one against two
            if(one.percent > two.percent) //We want the highest information at the bottom
                return -1;
            else if(one.percent < two.percent) //Lowest information to the top
                return 1;
            else
                return 0;
        }
    }