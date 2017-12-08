import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CrossRef
{
    public class Node
    {
        private String name;
        private List values;
        private Node left;
        private Node right;


        Node(String name, List values, Node left, Node right)
        {
            Node head = new Node("Head",values,null,null);
            this.name = name;
            this.left = left;
            this.right = right;
        }

    }
    private class List
    {

        private List next;
        private int value;

        List(int value, List next)
        {
            this.value = value;
            this.next = next;
        }

    }

    Node root;

    CrossRef()
    {
        root = new Node(null,null,null,null);
    }


    public void addBST(String name, int line)
    {
        Node temp = root;
        List tracker;

        if(root.name == null)
        {
            List linked = new List(line,null);
            root.values = linked;
            root.name = name;
        }
        else
        {
            if(root.name.equals(name))
            {
                tracker = root.values;
                while(tracker != null)
                {
                    if(tracker.next == null)
                    {
                        tracker.next = new List(line,null);
                    }
                    else
                    {
                        tracker = tracker.next;
                    }
                }
            }
            else if(0 < root.name.compareTo(name))
            {
                temp = root.left;
                List linked = new List(line, null);
                temp = new Node(name, linked,null,null);

            else
            {
                temp = root.right;
                List linked = new List(temp,line,null);
                temp = new Node(name,linked,null,null);
            }
        }
    }
}


//
//  NOMENCLATOR. Read names from a Java source file.
//
//    James Moen
//    04 Dec 17
//
//  The version of 01 Dec 17 could not handle /*...*/ comments that extend over
//  multiple lines, but this one can.
//


//  NOMENCLATOR. Read names from a Java source file.  It acts like an ITERATOR,
//  but it has two NEXT methods: one for names, and one for the line numbers of
//  those names.

 class Nomenclator
 {
     private char              ch;                 //  Current CHAR from READER.
     private static final char eof = (char) 0x00;  //  End of file sentinel.
     private static final char eol = (char) 0x0A;  //  End of line sentinel.
     private int               index;              //  Index into LINE.
     private String            line;               //  Current LINE from READER.
     private boolean           listing;            //  Are we listing the file?
     private String            name;               //  Current name.
     private int               number;             //  Current line number.
     private String            path;               //  Pathname to READER's file.
     private BufferedReader    reader;             //  Read CHARs from here.

//  Constructor. Initialize a new NOMENCLATOR that reads from a text file whose
//  pathname is PATH. If we can't open it then throw an exception. LISTING says
//  whether we should copy the file to standard output as we read it.

     public Nomenclator(String path, boolean listing)
     {
         try
         {
             index = 0;
             line = "";
             this.listing = listing;
             number = 0;
             this.path = path;
             reader = new BufferedReader(new FileReader(path));
             skipChar();
         }
         catch (IOException ignore)
         {
             throw new IllegalArgumentException("Can't open '" + path + "'.");
         }
     }

//  HAS NEXT. Test if there's another name waiting to be read. If so, then read
//  it, so NEXT NAME and NEXT NUMBER can return it and its line number later.

     public boolean hasNext()
     {
         while (true)
         {
             if (Character.isJavaIdentifierStart(ch))
             {
                 skipName();
                 return true;
             }
             else if (Character.isDigit(ch))
             {
                 skipNumber();
             }
             else
             {
                 switch (ch)
                 {
                     case eof:
                     {
                         return false;
                     }
                     case '"':
                     case '\'':
                     {
                         skipDelimited();
                         break;
                     }
                     case '/':
                     {
                         skipComment();
                         break;
                     }
                     default:
                     {
                         skipChar();
                         break;
                     }
                 }
             }
         }
     }

//  NEXT NAME. If HAS NEXT was true, then return the next name. If HAS NEXT was
//  false, then return an undefined string.

     public String nextName()
     {
         return name;
     }

//  NEXT NUMBER. If HAS NEXT was true, then return the line number on which the
//  next name appears. If HAS NEXT was false, then return an undefined INT.

     public int nextNumber()
     {
         return number;
     }

//  SKIP CHAR. If no more CHARs remain unread in LINE, then read the next line,
//  adding an EOL at the end. If no lines can be read, then read a line with an
//  EOF char in it. Otherwise just read the next char from LINE and return it.

     private void skipChar()
     {
         if (index >= line.length())
         {
             index = 0;
             number += 1;
             try
             {
                 line = reader.readLine();
                 if (line == null)
                 {
                     line = "" + eof;
                 }
                 else
                 {
                     if (listing)
                     {
                         System.out.format("%05d ", number);
                         System.out.println(line);
                     }
                     line += eol;
                 }
             }
             catch (IOException ignore)
             {
                 line = "" + eof;
             }
         }
         ch = line.charAt(index);
         index += 1;
     }

//  SKIP COMMENT. We end up here if we read a '/'. If it is followed by another
//  '/', or by a '*', then we skip a comment. We must skip comments so that any
//  names that appear in them will be ignored.

     private void skipComment()
     {
         skipChar();
         if (ch == '*')
         {
             skipChar();
             while (true)
             {
                 if (ch == '*')
                 {
                     skipChar();
                     if (ch == '/')
                     {
                         skipChar();
                         return;
                     }
                 }
                 else if (ch == eof)
                 {
                     return;
                 }
                 else
                 {
                     skipChar();
                 }
             }
         }
         else if (ch == '/')
         {
             skipChar();
             while (true)
             {
                 if (ch == eof)
                 {
                     return;
                 }
                 else if (ch == eol)
                 {
                     skipChar();
                     return;
                 }
                 else
                 {
                     skipChar();
                 }
             }
         }
     }

//  SKIP DELIMITED. Skip a string constant or a character constant, so that any
//  names that appear inside them will be ignored.  Throw an exception if there
//  is a missing delimiter at the end.

     private void skipDelimited()
     {
         char delimiter = ch;
         skipChar();
         while (true)
         {
             if (ch == delimiter)
             {
                 skipChar();
                 return;
             }
             else
             {
                 switch (ch)
                 {
                     case eof:
                     case eol:
                     {
                         throw new IllegalStateException("Bad string in '" + path + "'.");
                     }
                     case '\\':
                     {
                         skipChar();
                         if (ch == eol || ch == eof)
                         {
                             throw new IllegalStateException("Bad string in '" + path + "'.");
                         }
                         else
                         {
                             skipChar();
                         }
                         break;
                     }
                     default:
                     {
                         skipChar();
                         break;
                     }
                 }
             }
         }
     }

//  SKIP NAME. Skip a name, but convert it to a STRING, stored in NAME.

     private void skipName()
     {
         StringBuilder builder = new StringBuilder();
         while (Character.isJavaIdentifierPart(ch))
         {
             builder.append(ch);
             skipChar();
         }
         name = builder.toString();
     }

//  SKIP NUMBER. Skip something that might be a number. It starts with a digit,
//  followed by zero or more letters and digits. We must do this so the letters
//  aren't treated as names.

     private void skipNumber()
     {
         skipChar();
         while (Character.isJavaIdentifierPart(ch))
         {
             skipChar();
         }
     }

//  MAIN. Get a file pathname from the command line. Read a series of names and
//  their line numbers from the file, and write them one per line. For example,
//  the command "java Nomenclator Nomenclator.java" reads names from the source
//  file you are now looking at. This method is only for debugging!

     public static void main(String [] args)
     {
         Nomenclator reader = new Nomenclator(args[0], false);
         while (reader.hasNext())
         {
             System.out.println(reader.nextNumber() + " " + reader.nextName());
         }
     }
 }