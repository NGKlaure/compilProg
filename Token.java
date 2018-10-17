
// CS7360: You do not need to edit this file.

public class Token
{
    public static final int INVALID = 0;
    public static final int EOF     = -1;

    public String   text;
    public int      type;

    public Token (int type, String text) 
    {
        this.type = type;
        this.text = text;
    }

    public String toString () 
    {
        return "[" + text + ":" + type + "]";
    }
}
