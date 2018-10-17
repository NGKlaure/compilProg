
// CS7360:: You do not need to edit this file.
// Author:: Joshua Yue
import java.io.InputStreamReader;
import java.io.Reader;

public class Prog3
{

   public static void main(String[] args)
   {
      System.out.println("Please copy and paste dot code that ends with "
            + "a $ sign for the basic parser:");
      //Basic parser
      try
      {
         // Create lexer for stdin.
         DotLexer lexer = new DotLexer(new InputStreamReader(System.in));
         BasicDotParser parser = new BasicDotParser(lexer);
         parser.digraph();
         System.out.println("OK");  // prints OK if no exception
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }

      System.out.println("\nEnter Q to stop or any other character "
            + "to continue with AST parser:");

      Reader reader = new InputStreamReader(System.in);

      try
      {
         if ((char) reader.read() != 'Q')
         {
            System.out.println("\nPlease copy and paste dot code that ends with "
                  + "a $ sign for the AST parser:");
            //AST parser     
            DotLexer lexer = new DotLexer(new InputStreamReader(System.in));
            AstDotParser astParser = new AstDotParser(lexer);
            Ast root = astParser.digraph();
            System.out.println(root.toStringTree());

         }
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }

   }
}
