public class LiquidConfig
{
    private String name;
    private int volume;
    private int count;

    public LiquidConfig(int volume, int count)
    {
        this.volume = volume;;
        this.count = count;
    }

    public LiquidConfig(String str)
    {
        String [] parts = str.split(",");
        this.volume = Integer.parseInt(parts[0]);
        this.count = Integer.parseInt(parts[1]);
    }

    public String toString()
    {
        return volume + "," + count;
    }

    public int GetCount()
    {
        return this.count;
    }

    public void PopOneBottle()
    {
        --this.count;
    }

    public int GetVolume()
    {
        return this.volume;
    }
}
