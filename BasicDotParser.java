// CS7360:: Complete the parsing logic.
// Author:: Joshua Yue

import java.io.IOException;
import java.io.InputStreamReader;

public class BasicDotParser extends DotParser
{

   public BasicDotParser(TokenStream input) throws IOException
   {

       super(input);
   }

   public void digraph() throws Exception
   {
      System.out.println("Start recognizing a digraph");
      
      match(DotLexer.DIGRAPH);

      match(DotLexer.ID);

      cluster();

      match(Token.EOF);
      
      System.out.println("Finish recognizing a digraph");
   }

    //metthode to match the cluster of a digraph
   protected void cluster() throws Exception
   {
      
    // You will need to look at tokens[p] and tokens[p+1] for this rule/method.
       System.out.println("Start recognizing a cluster");
      
       match(DotLexer.LCURLY);
       
       clusterOption();

       match(DotLexer.RCURLY);
       
       System.out.println("Finish recognizing a cluster");
    
   }
   
   //methode to match a propety if condition is satisfied in clusterOption()
    protected void property() throws IOException, Exception {
        
        System.out.println("Start recognizing a property");
            
             match(DotLexer.ID);
            
             match(DotLexer.EQUALS);
            
             match(edgeNode());
             System.out.println("Finish recognizing a property");
             
             clusterOption();

    }
    
        
        //methode to match an edge if condition is satified in clustercontion()
    protected void edgeStatement() throws IOException, Exception {

             System.out.println("Start recognizing an edge statement");
             match(edgeNode());
             match(DotLexer.ARROW);
             match(edgeNode());
             if (tokens[p].type == DotLexer.LBRACK){
                 match(DotLexer.LBRACK);
                 property();
                 if (tokens[p].type == DotLexer.COMMA){
                    p++;  
                    property();
                  }
                     match(DotLexer.RBRACK);
             }
            System.out.println("Finish recognizing an edge statement");
                clusterOption();
                if (tokens[p].type== DotLexer.ARROW){
                    multiEdgeStatement();
                    System.out.println("Finish recognizing and edge statement");
                    clusterOption();
                }
    }
    
    // methode to match only nodes and edge
    private void multiEdgeStatement() throws Exception {
        
             match(DotLexer.ARROW);
             match(edgeNode());

            System.out.println("Finish recognizing an edge statement");
                clusterOption();

    }

    // methode to call a subgraph if the condition is satisfied in clusterOption()
    protected void subgraph() throws Exception {
        System.out.println("Start recognizing a subgraph");
        
         match(DotLexer.SUBGRAPH);

         match(DotLexer.ID);
        
        clusterSubgraph();
         
        System.out.println("Finish recognizing a subgraph");
        clusterOption();
    }
    
    //methode for the cluster in a subgraph
    protected void clusterSubgraph() throws Exception {
        System.out.println("Start recognizing a cluster");
      
        match(DotLexer.LCURLY);
       
       clusterOption();

       match(DotLexer.RCURLY);
       
       System.out.println("Finish recognizing a cluster");
       
       
    }

        // node of and edge statement
    protected int edgeNode() {
        while(true){  
            if (tokens[p].type == DotLexer.ID)
                return DotLexer.ID;
            else if (tokens[p].type == DotLexer.INT)
                return DotLexer.INT;
            if (tokens[p].type == DotLexer.STRING)
                return DotLexer.STRING;
        }
    }
    
    //methode to check the option of the cluster 
    protected void clusterOption() throws Exception {
       
        if (tokens[p].type == DotLexer.SUBGRAPH){
            subgraph(); 
        } 
        else if ((tokens[p].type == DotLexer.ID && tokens[p+1].type == DotLexer.ARROW)| 
              (tokens[p].type == DotLexer.INT && tokens[p+1].type ==DotLexer.ARROW)| 
               (tokens[p].type == DotLexer.STRING && tokens[p+1].type ==DotLexer.ARROW)){
            edgeStatement();  
       } 
        else if ((tokens[p].type == DotLexer.ID) &&(tokens[p+1].type == DotLexer.EQUALS)){
            property();  
        }else if (tokens[p].type== DotLexer.SEMI){
            p++;
            clusterOption();  
        }     
        
    }

    


}
