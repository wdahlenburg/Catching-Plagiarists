import java.util.Comparator;
public class revComparator implements Comparator <Data>
    {
        public int compare(Data one, Data two){
            if(one.percent > two.percent)
                return -1;
            else if(one.percent < two.percent)
                return 1;
            else
                return 0;
        }
    }