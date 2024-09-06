
public class SysjMessage {
    public String src;
    public String msg;

    public SysjMessage(String str)
    {
        String [] parts = str.split("\\.");
        this.src = parts[0];
        this.msg = parts[1];
    }

    SysjMessage(String src, String msg)
    {
        this.src = src;
        this.msg = msg;
    }

    public String MsgToString()
    {
        return this.src + "." + this.msg;
    }

    public void assign(String src, String dst, String msg)
    {
        this.src = src;
        this.msg = msg;
    }
}
