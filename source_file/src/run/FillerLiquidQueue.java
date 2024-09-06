import java.util.LinkedList;
import java.util.Queue;

public class FillerLiquidQueue
{
    private final Queue<LiquidConfig> queue;

    public FillerLiquidQueue()
    {
        this.queue = new LinkedList<LiquidConfig>();
    }

    public void PopOneBottle()
    {
        if (this.queue.isEmpty())
            return;

        this.queue.peek().PopOneBottle();
        if (0 == queue.peek().GetCount())
            queue.poll();
    }

    public boolean PushOneQueue(String str)
    {
        LiquidConfig cfg = new LiquidConfig(str);
        return this.queue.offer(cfg);
    }

    public int GetCurrentIngredient()
    {
        if (this.queue.isEmpty())
            return -1;

        return this.queue.peek().GetVolume();
    }
}
