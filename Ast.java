
// CS7360:: You do not need to edit this file.
// Author:: Joshua Yue

import java.util.ArrayList;
import java.util.List;

public class Ast
{
    protected List<Ast> children;

    // The contents of a tree node.
    protected Token token;

    public Ast (Token token) 
    {
        this.token = token;
    }

    // Add a node to the end of the child list for this node
    public void addChild (Ast node) 
    {
        if (null == children) 
        {
            children = new ArrayList<Ast>();
        }
        children.add(node);
    }

    // Get the token text for this node
    public String getText () 
    {
        return token.text;
    }

    // Get the token type for this node
    public int getType () 
    {
        return token.type;
    }

    public String toString () 
    {
        StringBuffer b = new StringBuffer();
        b.append('[');
        b.append(getText());
        b.append(":");
        b.append(getType());
        b.append("]");
        return b.toString();
    }

    // Print out a child-sibling tree in LISP-like notation
    public String toStringTree () 
    {
        if ((null == children) || (0 == children.size())) 
        {
            return this.getText();
        }
        StringBuffer buf = new StringBuffer();
        if (null != children) 
        {
            buf.append("(");
            buf.append(this.getText());
            buf.append(' ');
        }
        for (int i = 0; (null != children) && (i < children.size()); ++i) 
        {
            Ast t = children.get(i);
            if (i > 0) 
            {
                buf.append(' ');
            }
            buf.append(t.toStringTree());
        }
        if (null != children) 
        {
            buf.append(")");
        }
        return buf.toString();
    }
}
