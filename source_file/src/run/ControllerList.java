import java.util.HashMap;

public class ControllerList
{
    private HashMap<String, Controller> map;

    ControllerList()
    {
        this.map = new HashMap<String, Controller>();
    }

    public boolean AddController(String str)
    {
        Controller ctler = new Controller(str);
        if (this.map.containsKey(ctler.GetName()))
            return false;

        this.map.put(ctler.GetName(), ctler);
        return true;
    }

    public boolean AddController(Controller controller)
    {
        if (this.map.containsKey(controller.GetName()))
            return false;

        //create a new object
        Controller c = new Controller(controller.GetName());
        this.map.put(c.GetName(), c);
        return true;
    }

    public boolean CheckAllIdleState()
    {
        for (Controller v : this.map.values())
        {
            if (!v.IsIdle())
                return false;
        }

        return true;
    }

    public void ResetAllIdleState()
    {
        for (Controller v : this.map.values())
            v.ResetIdle();
    }

    public boolean SetSpecificState(Controller controller) {
        if (this.map.containsKey(controller.GetName()))
            return false;

        this.map.get(controller.GetName()).SetIdle();
        return true;
    }

    public boolean SetSpecificState(String name)
    {
        if (this.map.containsKey(name))
            return false;

        this.map.get(name).SetIdle();
        return true;
    }

    public int GetListSize()
    {
        return this.map.size();
    }

}
