import org.json.*;
public class BottleQueue
{
    private final Bottles[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public BottleQueue(int capacity)
    {
        //0 - capacity-1 is available, capacity is not available
        this.capacity = capacity + 1;
        this.queue = new Bottles[this.capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public boolean enqueue(Bottles element)
    {
        if (isFull())
        {
            System.out.println("Queue is full. Cannot enqueue " + element);
            return false;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = element;
        size++;
        return true;
    }

    public Bottles dequeue()
    {
        if (isEmpty())
        {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }

        Bottles ret = queue[front];
        front = (front + 1) % capacity;
        size--;
        return ret;
    }

    public boolean isFull()
    {
        return size == capacity;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }

    public void Display()
    {
        if (isEmpty())
        {
            return;
        }

        System.out.print("Queue elements: ");
        for (int i = 0; i < size; i++)
        {
            System.out.print(queue[(front + i) % capacity] + " ");
        }
        System.out.println();
    }

    public int GetLastPopId()
    {
        if (this.size == 0)
            return -1;

        return queue[(front + size) % capacity].id;
    }

    public String GetLastPopStringJson()
    {
        if (this.size == 0)
            return null;

        Bottles tmp = queue[(front + size) % capacity];

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("bottleID", tmp.id);
        jsonObj.put("orderID", tmp.current_order_id);
        jsonObj.put("bottleIndex", tmp.current_bottle_index);
        jsonObj.put("orderAmount", tmp.current_order_amount);

        return jsonObj.toString();
    }

}
