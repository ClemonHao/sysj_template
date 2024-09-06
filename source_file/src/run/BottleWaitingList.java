import java.util.ArrayList;
import org.json.*;

public class BottleWaitingList
{
    private final ArrayList<Bottles> list;

    public BottleWaitingList()
    {
        this.list = new ArrayList<>();
    }

    //TODO: change the rule of ID generator
    private int BottleIDGenerater(int orderID, int current_count)
    {
        return orderID + current_count + 1;
    }

    public boolean AppendListWithJson(String json_string)
    {
        JSONObject jsonObj = new JSONObject(json_string);
        int count = jsonObj.getInt("count");
        int orderID = jsonObj.getInt("orderID");
        String name = jsonObj.getString("name");

        for (int i = 0; i < count; i++)
        {
            this.list.add(
                    new Bottles(BottleIDGenerater(orderID, i),
                    name, orderID, i, count));
        }

        return true;
    }

    public Bottles PopBottle()
    {
        if (this.list.isEmpty())
            return null;

        return this.list.remove(0);
    }

    public int GetListLength()
    {
        return this.list.size();
    }
}
