import org.json.*;

public class LiquidIngredient
{
    private final LiquidConfig[] ingredient;

    public String[] IngredientNameList;

    {
        IngredientNameList = new String[]{
                "LiquidA",
                "LiquidB",
                "LiquidC",
                "LiquidD"
        };
    }

    public LiquidIngredient(String json_string)
    {
        this.ingredient = new LiquidConfig[IngredientNameList.length];

        JSONObject jsonObj = new JSONObject(json_string);
        int count = jsonObj.getInt("count");
        JSONObject ingre =  jsonObj.getJSONObject("recipe");
        for (int i = 0; i < ingre.length(); i++)
        {
            String liqType = jsonObj.getString("liqType");
            int capacity = jsonObj.getInt("capacity");
            for (int k = 0; k < IngredientNameList.length; k++)
            {
                if (liqType.equals(IngredientNameList[k]))
                {
                    this.ingredient[k] = new LiquidConfig(capacity, count);
                }
                else
                {
                    System.out.println("Invalid liquid type: " + liqType);
                }
            }
        }
    }

    public LiquidConfig Get(int index)
    {
        return ingredient[index];
    }

    public boolean UpdateIngredient()
    {
        return true;
    }

    public boolean IsFull()
    {
        return IngredientNameList.length == ingredient.length;
    }

    public int GetCapacity()
    {
        return IngredientNameList.length;
    }
}
