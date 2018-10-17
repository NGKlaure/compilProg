// CS7360:: Complete the parsing and tree construction logic.
// Author:: Joshua Yue
 
import java.io.IOException;
import java.io.InputStreamReader;

public class AstDotParser extends DotParser
{
   public static final int CLUSTER = 100;
   public static final int PROPS = 101;

   public AstDotParser(TokenStream input) throws IOException
   {
      super(input);
   }

   //build a subtree for a diagraph
   public Ast digraph() throws Exception
   {

      Ast root = new Ast(tokens[p]); //Digraph
      match(DotLexer.DIGRAPH);

      Ast left = new Ast(tokens[p]);
      match(DotLexer.ID);
      root.addChild(left);

      Ast right = cluster();
      root.addChild(right);

      return root;
   }
   
   //build a subtree for a cluster
   protected Ast cluster() throws Exception
   {
      
       Token tok =new Token( CLUSTER,"CLUSTER");
       Ast root = new Ast(tok);
       
         
        tokens[p].text="";
        Ast left = new Ast(tokens[p]);
        match(DotLexer.LCURLY);
        root.addChild(left);

        if (((tokens[p].type == DotLexer.ID) &&(tokens[p+1].type == DotLexer.EQUALS)))
        {
             
            Ast root1 = property();
            root.addChild(root1);     
        }
        if (tokens[p].type== DotLexer.SEMI)
        {
            p++;
            Ast root1 =option();
            root.addChild(root1);   
        }
        
        if ((tokens[p].type == DotLexer.ID && tokens[p+1].type == DotLexer.ARROW)| 
           (tokens[p].type == DotLexer.INT && tokens[p+1].type ==DotLexer.ARROW)| 
           (tokens[p].type == DotLexer.STRING && tokens[p+1].type ==DotLexer.ARROW))
        {
            
            Ast root2= edgeStatement();
            root.addChild(root2);   
        }
        
        if (tokens[p].type == DotLexer.SUBGRAPH)
        {
             Ast root3= subgraph();
             root.addChild(root3);
        } if(tokens[p].type==DotLexer.ARROW |
                tokens[p].type==DotLexer.LCURLY)
        {
            throw new Exception("expecting property,edge " +
                    "or subgraph; found " + tokens[p].text);
        }  

            Ast right3 = option();
            root.addChild(right3);
            
   
        return root;
        
   }

 
    //build a subtree for a subgraph
   protected Ast subgraph() throws Exception
   {
      Ast root = new Ast(tokens[p]); //Digraph
      match(DotLexer.SUBGRAPH);
      Ast left = new Ast(tokens[p]);
      match(DotLexer.ID);
      root.addChild(left);
      Ast right = cluster();
      root.addChild(right);
  
      return root;
     

   }
       // method  match option inside a cluster
     protected Ast option() throws Exception 
     {
        
        Ast root = new Ast(tokens[p]);
        if (tokens[p].type == DotLexer.SUBGRAPH)
        {
             root= subgraph();
         
        } 
        else if(tokens[p].type== edgeNode() && tokens[p+1].type == DotLexer.ARROW)
        { 

            root = edgeStatement();  
        } 
        else if ((tokens[p].type == DotLexer.ID) &&(tokens[p+1].type == DotLexer.EQUALS))
        {
           root =  property();  
        }else if (tokens[p].type== DotLexer.SEMI)
        {
            p++;
            Ast root1=option();
             root = root1;
          
        }else if (tokens[p].type == DotLexer.RCURLY)
        {
          
            tokens[p].text = "";
            p++;
            Ast root1=option();
            root =root1;
            
        }else if(tokens[p].type== DotLexer.RBRACK) 
        {
            tokens[p].text = "";
            
        }
         
         return root;
    }
        //Subtree for edge statement
    protected Ast edgeStatement() throws Exception 
    {
        //System.out.print(tokens[p]);
        Ast root =new Ast (tokens[p+1]);
        match(edgeNode());
        Ast left = new Ast(tokens[p-1]);
        match (DotLexer.ARROW);
        root.addChild(left);
        Ast right = new Ast (tokens[p]);
        match (edgeNode());
        root.addChild(right);
       if (tokens[p].type == DotLexer.LBRACK)
       {
           
            Ast right2 =  props();
            root.addChild(right2);
            p++;
       }  
       if (tokens[p].type ==DotLexer.ARROW)
       {
           
           Ast root1 = new Ast(tokens[p]);
           match(DotLexer.ARROW);
           root.addChild(root1);
           Ast leftt = new Ast (tokens[p]);
           match(edgeNode());
           root.addChild(leftt);
           p++;
            System.out.print(tokens[p]);
            
          if (tokens[p].type== edgeNode())
          {
              Ast right3=option();
              p++;
           root.addChild(right3);
          }
          Ast ri = option();
          root.addChild(ri);
          
          root.addChild(option());
            
       } 
       

        return root;
    }
        //subtree for a property
    protected Ast property() throws Exception 
    {
        Ast root = new Ast (tokens[p+1]);
      
        match(edgeNode());
        Ast left= new Ast (tokens[p-1]);
        match (DotLexer.EQUALS);
        root.addChild(left);
        Ast right = new Ast (tokens[p]);
        match(edgeNode());
        root.addChild(right);
       if (tokens[p].type== DotLexer.COMMA)
       {
            tokens[p].text= "";
            p++;
            Ast root1= option();
            propsroot.addChild(root1);
            
        }
        
        return root;
        
    }
        //method to define a node
    protected int edgeNode() {
        
        int node=DotLexer.ID ;
            if (tokens[p].type == DotLexer.ID)
                node= DotLexer.ID;
            else if (tokens[p].type == DotLexer.INT)
                node= DotLexer.INT;
            if (tokens[p].type == DotLexer.STRING)
                node=DotLexer.STRING;
       
        return node;
    }
    Ast propsroot= new Ast (tokens[p]);
    
    //method for subtree propeties inside LBracket
    private Ast props() throws Exception 
    {
        Token tok =new Token( PROPS,"PROPS");
      
        propsroot= new Ast (tok);
        if( tokens[p].type == DotLexer.LBRACK)
        {
            Ast left = new Ast(new Token(DotLexer.WS,""));
       
            propsroot.addChild(left);
            p++;
             
            Ast right = property();
            propsroot.addChild(right);
            p++;
        }
         
        return propsroot;
    }

   
}
