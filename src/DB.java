import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/6/24.
 */
public class DB {
    String userName="";
    int uid;
    List<FollowU> ful;
    List<FollowD> fdl;
    List<FollowN> fnl;

    private static DB ourInstance = new DB();

    public static DB getInstance() {
        return ourInstance;
    }

    private DB() {
        ful=new ArrayList<>();
        fdl=new ArrayList<>();
        fnl=new ArrayList<>();
    }

    class FollowU{
        int id;
        String name;
    }

    class FollowD{
        int id;
        String title;
        String path;
        int ownerid;
        int dislistid;
    }

    class FollowN{
        int id;
        int docid;
        int ownerid;
        String path;
        int dislistid;
    }
}
