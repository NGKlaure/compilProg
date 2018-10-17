// CS 3520: Complete the token recognition logic.
// Feel free to add new methods
// Author:: Joshua Yue

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import static java.lang.System.in;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class DotLexer implements TokenStream
{
    public static final int ID       = 1;
    public static final int INT      = 2;
    public static final int STRING   = 3;
    public static final int LCURLY   = 4;
    public static final int RCURLY   = 5;
    public static final int SEMI     = 6;
    public static final int LBRACK   = 7;
    public static final int RBRACK   = 8;
    public static final int ARROW    = 9;
    public static final int EQUALS   = 10;
    public static final int DIGRAPH  = 11;
    public static final int SUBGRAPH = 12;
    public static final int COMMA    = 13;
    public static final int WS 		 = 14;

   // private Reader reader = null;
    PushbackReader reader = null;
    
   
    // Lookahead char.
    private char c;

   
    
    // Text of currently matched token.
    private StringBuffer text = new StringBuffer(100);

    // Maps a keyword to its token type.
    static final Map<String, Integer> keywords = new HashMap<>();
    
    static {
        keywords.put("digraph", DIGRAPH);
        keywords.put("subgraph", SUBGRAPH);
    }
        Charset encoding = Charset.defaultCharset();
    public DotLexer (Reader reader) throws IOException {
      // this.reader = new InputStreamReader(in, encoding); 
      this.reader = new PushbackReader(new InputStreamReader(in));
    }
	//read a character from the input stream
    protected void nextChar () throws IOException {
 
    }
	//the public method is called in Prog3.java to return next token recognized 
	//in the input stream
    public Token nextToken () throws IOException {
       do{
            c = (char)this. reader.read();
            if ( c == '$'){
               // System.out.println("Lexical analysis is finished!");
                    return new Token(Token.EOF,text.toString());
            }
            else if (c == '{'){
              return new Token(LCURLY ,"{");}
            else if (c == '}'){
              return new Token(RCURLY ,"}");}
            else  if (c == '['){
              return new Token(LBRACK ,"[");}
            else if (c == ']'){
              return new Token(RBRACK ,"]");}
            else if (c == ';'){
              return new Token(SEMI ,";");}
            else  if (c == '=')
             {return new Token(EQUALS ,"=");}
            else if (c == '-'){
                    int nextC = this.reader.read();
                        if(nextC == '>')
                         return new Token(ARROW,"->");
            }
            else if (c == ','){    
                 return new Token(COMMA ,",");}
            else if (Character.isDigit(c)){
                return this.matchINT();}

            else if ( c == '"'){
                return this.matchSTRING();
                    
            }
            else if (Character.isLetter(c)){
               return this.matchID();
            }
            else if (c == '\n'){
                System.out.print("");
            }else if (c == ' '){
                System.out.print("");
            }
            else
            {System.out.println("illegal char " + c);}
   
       } while (true);         
        
    }
    
	//A method called by nextToken to match a string
   /* protected int matchSTRING () throws IOException {
        text.append(c);
        nextChar();
         //System.out.println(c);
        // store until the end of STRING
        while ((-1 != c) && ('"' != c)) {
            text.append(c);
            nextChar();
        }
        text.append(c);
        nextChar(); // skip final double quote
        return STRING;
    }*/
        //methode to match INT
    protected Token matchINT() throws IOException{
         String val = String.valueOf(c);
                    
                    while (true){
                       int c = reader.read();
                        if (Character.isDigit(c)){
                          val = val + Character.getNumericValue(c);
                        }else{this.reader.unread(c);
                            
                         break;}
                    }
                        //System.out.print("print" + val);
                        return new Token(INT,String.valueOf(val));
    }
        // to match a string 
    protected Token matchSTRING() throws IOException{
        StringBuilder text1 = new StringBuilder();
        text1.append(c);
        while (true){
            char next = (char) this.reader.read();
             if (next != '"'){ 
              text1.append( next);
             }else if ((Character.isDigit(next)| Character.isLetter(next))){
                 text1.append( next);
             }else 
                 break;
            
        }
         text1.append(c);
       return new Token(STRING,text1.toString()); 
    }
    
    // methode to match an Identifier.
    //ID start with a letter follow by letters or digits
     public Token matchID() throws IOException {
         StringBuilder identifier = new StringBuilder();
         identifier.append(c);
        while(true){
             char next = (char)this.reader.read();
                 if (Character.isLetterOrDigit(next)) 
                  {identifier.append( next);}
                
                 else {this.reader.unread(next); //to put back the character in the stack.
                  break;} 
        }
        // manage keywords 
        String idString = identifier.toString();
        if (identifier.toString().equalsIgnoreCase("digraph")){
            return new Token(DIGRAPH,identifier.toString());
        }
       
        if (identifier.toString().equalsIgnoreCase("Subgraph")){
            return new Token(SUBGRAPH,identifier.toString());
        }
       
        return new Token(ID,idString);
    }
}
