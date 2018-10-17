
// CS7360:: You do not need to edit this file.
// Author:: Joshua Yue

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class DotParser
{
   // The source of tokens.
   protected TokenStream input = null;

   // Buffer of all tokens including EOF.
   protected Token[] tokens = null;

   // Pointer into tokens buffer; p=-1 means uninitialized.
   protected int p = -1;

   public DotParser (TokenStream input) throws IOException 
   {
      this.input = input;
      initTokenBuffer(input);
   }

   protected void initTokenBuffer (TokenStream input) throws IOException 
   {
      // fill token buffer to make lookahead easy
      List<Token> buf = new ArrayList<Token>();
      Token t = input.nextToken();
      while (Token.EOF != t.type) 
      {
         buf.add(t);
	// System.out.println("Parser: "+t);
         t = input.nextToken();
      }
      buf.add(t);
      tokens = new Token[buf.size()];
      buf.toArray(tokens);
      p = 0; // point at first token
   }

   protected void consume () throws IOException 
   {
      p++;
   }

   protected void match (int type) throws Exception 
   {
      if (tokens[p].type != type) 
      {
         throw new Exception("token mismatch: expecting token type " +
                                type + "; found " + tokens[p].text);
      }
      
      consume();
   }
}
