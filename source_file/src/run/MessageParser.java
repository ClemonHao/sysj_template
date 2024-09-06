public class MessageParser
{
    public static String EncodeMessage(String source, String msg)
    {
        return source + "." + msg;
    }

    public static String GetMessageState(String source, String msg)
    {
        return source + "."  + msg;
    }

    public static SysjMessage DecodeMessage(String message)
    {
        String [] parts = message.split("\\.");
        return new SysjMessage(parts[0], parts[1]);
    }
}
